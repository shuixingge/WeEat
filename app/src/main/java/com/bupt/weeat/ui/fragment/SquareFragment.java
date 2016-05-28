package com.bupt.weeat.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.Toast;

import com.bupt.weeat.R;
import com.bupt.weeat.adapter.PostListAdapter;
import com.bupt.weeat.model.entity.Post;
import com.bupt.weeat.model.entity.User;
import com.bupt.weeat.presenter.SquarePresenter;
import com.bupt.weeat.ui.activity.CommentActivity;
import com.bupt.weeat.ui.activity.CreatePostActivity;
import com.bupt.weeat.ui.activity.LoginActivity;
import com.bupt.weeat.ui.activity.UserActivity;
import com.bupt.weeat.utils.LogUtils;
import com.bupt.weeat.utils.ToastUtils;
import com.bupt.weeat.utils.UserUtils;
import com.bupt.weeat.view.customView.RecyclerItemClickListener;
import com.bupt.weeat.view.customView.SwipeLoadRefreshLayout;
import com.bupt.weeat.view.mvpView.SquarePostListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;

public class SquareFragment extends BaseFragment implements PostListAdapter.OnFeedItemClickListener, SquarePostListView {
    private static final String TAG = SquareFragment.class.getSimpleName();
    @Bind( R.id.square_refresh_load)
    SwipeLoadRefreshLayout swipeRefreshLayout;
    @Bind(R.id.square_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.square_coordinator)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.square_fab)
    FloatingActionButton fab;
    private List<Post> postList=new ArrayList<>();;
    private PostListAdapter adapter;
    private int postPage = 1;
    private static final int ANIM_DURATION_FAB = 500;
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    private SquarePresenter presenter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square;
    }

    @Override
    protected void initData() {
        super.initData();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary,
                R.color.colorAccent);
        adapter = new PostListAdapter(context, postList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        presenter=new SquarePresenter(context);
        presenter.attachView(this);
        if (!UserUtils.isLogin(context)) {
            ToastUtils.showToast(context, R.string.please_login_first, Toast.LENGTH_SHORT);
            Intent intent = new Intent(context, LoginActivity.class);
            getActivity().startActivity(intent);
            getActivity().overridePendingTransition(0, 0);
        }
        if (postList.size() <= 0)
            presenter.loadList(postPage);
        //startFabAnim();
        dynamicAddSkinView(fab,"backgroundTint", R.color.colorAccent);
    }

    private void startFabAnim() {
        fab.setTranslationY(240);
        fab.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.0f))
                .setDuration(ANIM_DURATION_FAB)
                .setStartDelay(400);
    }

    @Override
    protected void setListener() {
        super.setListener();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postPage = 1;
                presenter.loadList(postPage);
            }
        });
        swipeRefreshLayout.setOnLoadListener(new SwipeLoadRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                postPage++;
            }
        });
        adapter.setOnFeedItemClickListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreatePostActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void refresh(List<Post> posts) {
        postList.clear();
        postList.addAll(posts);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMore(List<Post> posts) {
        postList.addAll(posts);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setLoading(false);
    }

    @Override
    public void showError(String msg) {
        LogUtils.i(TAG, "query fail");
        postPage--;
        if (postPage <= 1)
            postPage = 1;
        ToastUtils.showToast(context, msg, Toast.LENGTH_SHORT);
    }

    public void showSnackbar() {
        Snackbar.make(coordinatorLayout, "loved", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCommentsClick(View view, int position) {
        LogUtils.i(TAG, "onCommentsClick");
        Intent intent = new Intent(context, CommentActivity.class);
        Post post = postList.get(position);
        intent.putExtra("data", post);
        startActivity(intent);
    }

    @Override
    public void onShareClick(View v, int position) {

    }

    @Override
    public void onPraiseClick(View view, int position) {
        showSnackbar();
        PostListAdapter.ViewHolder holder = (PostListAdapter.ViewHolder) view.getTag(R.id.tag_holder);
        ImageView praiseImg = holder.praiseImg;
        TextSwitcher textSwitcher = holder.likeCounterSwitcher;
        final User user = BmobUser.getCurrentUser(context, User.class);
        final BmobRelation relation = new BmobRelation();
        final Post post = postList.get(position);
        if (!post.isMyPraise()) {
            LogUtils.i(TAG, post.isMyPraise() + "");
            setupPraiseAnim(praiseImg);
            post.setMyPraise(true);
            relation.add(post);
            user.setLoveRelation(relation);
            post.setPraiseNum(post.getPraiseNum() + 1);
            setupLikesCounter(textSwitcher, position);
            user.update(context, new UpdateListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(context, R.string.collect_success, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(context, R.string.collect_fail, Toast.LENGTH_LONG).show();
                    post.setMyPraise(false);
                    relation.remove(post);
                    user.setLoveRelation(relation);
                }
            });
            post.update(context, new UpdateListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int i, String s) {
                    post.setMyPraise(false);
                }
            });
        } else {
            LogUtils.i(TAG, post.isMyPraise() + "else");
            praiseImg.setImageResource(R.drawable.ic_heart_outline_grey);
            post.setMyPraise(false);
            post.setPraiseNum(post.getPraiseNum() - 1);
            setupLikesCounter(textSwitcher, position);
            LogUtils.i(TAG, post.isMyPraise() + "after undo");
            relation.remove(post);
            user.setLoveRelation(relation);
            user.update(context, new UpdateListener() {
                @Override
                public void onSuccess() {

                    Toast.makeText(context, R.string.cancel_collect_success, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    relation.add(post);
                    user.setLoveRelation(relation);
                    Toast.makeText(context, R.string.cancel_collect_fail, Toast.LENGTH_LONG).show();
                }
            });
            post.update(context, new UpdateListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int i, String s) {
                    post.setMyPraise(true);
                }
            });
        }

    }

    public void setupLikesCounter(TextSwitcher switcher, int position) {
        Post post = postList.get(position);
        int currentLikesCount = post.getPraiseNum();
        switcher.setText(currentLikesCount + " " + "赞");

    }

    public void setupCommentsCounter(TextSwitcher switcher, int position) {
        Post post = postList.get(position);
        int currentLikesCount = post.getPraiseNum();
        switcher.setText(currentLikesCount + " " + "评论");

    }

    public void setupSharesCounter(TextSwitcher switcher, int position) {
        Post post = postList.get(position);
        int currentLikesCount = post.getPraiseNum();
        switcher.setText(currentLikesCount + " " + "分享");

    }

    @Override
    public void onUserAvatarClick(View view, int position) {
        Intent intent = new Intent(getActivity(), UserActivity.class);
        startActivity(intent);
    }

    private void setupPraiseAnim(final ImageView userPraise) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator rotation = ObjectAnimator.ofFloat(userPraise, "rotation", 0f, 360f);
        rotation.setDuration(300);
        rotation.setInterpolator(ACCELERATE_INTERPOLATOR);
        ObjectAnimator bounceX = ObjectAnimator.ofFloat(userPraise, "scaleX", 0.2f, 1f);
        bounceX.setDuration(300);
        bounceX.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator bounceY = ObjectAnimator.ofFloat(userPraise, "scaleY", 0.2f, 1f);
        bounceY.setDuration(300);
        bounceY.setInterpolator(OVERSHOOT_INTERPOLATOR);
        bounceX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                userPraise.setImageResource(R.drawable.ic_heart_red);
            }
        });
        set.play(rotation);
        set.play(bounceX).with(bounceY).after(rotation);
        set.start();
    }

}
package com.bupt.weeat.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.weeat.Constant;
import com.bupt.weeat.R;
import com.bupt.weeat.adapter.DishCommentAdapter;
import com.bupt.weeat.model.entity.Comment;
import com.bupt.weeat.model.entity.DishBean;
import com.bupt.weeat.model.entity.User;
import com.bupt.weeat.utils.BaseUtils;
import com.bupt.weeat.utils.LogUtils;
import com.bupt.weeat.utils.ToastUtils;
import com.bupt.weeat.view.customView.SendCommentButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class DishDetailActivity extends BaseActivity {
    private static final String TAG = DishDetailActivity.class.getSimpleName();
    private static final int NEW_DISH_CODE = 0;
    private static final int WEEK_DISH_CODE = 1;
    @Bind(R.id.dish_detail_image)
    ImageView dishImage;
    @Bind(R.id.dish_detail_name)
    TextView dishName;
    @Bind(R.id.dish_detail_price)
    TextView dishPrice;
    @Bind(R.id.dish_detail_tastes)
    TextView dishTaste;
    @Bind(R.id.dish_detail_window)
    TextView dishWindow;
    @Bind(R.id.dish_detail_praise)
    TextView dishPraise;
    @Bind(R.id.dish_detail_load_more_comments)
    TextView loadMoreComment;
    @Bind(R.id.dish_detail_comment_bn)
    SendCommentButton dishCommentBn;
    @Bind(R.id.dish_detail_list)
    ListView commentLv;
    @Bind(R.id.dish_detail_comment_edit)
    EditText dishCommentEt;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    int DISH_CODE;

    private ArrayList<Comment> list = new ArrayList<>();
    ;
    private DishCommentAdapter adapter;
    private DishBean dish;

    @Override
    public int getLayoutId() {
        return R.layout.dish_detail_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        initToolbar();
        DISH_CODE = getIntent().getIntExtra("DISH_CODE", 2);
        LogUtils.i(TAG, "DISH_CODE :" + DISH_CODE);
        switch (DISH_CODE) {
            case NEW_DISH_CODE:
                dish = (DishBean) getIntent().getSerializableExtra("new_dish_data");
                LogUtils.i(TAG, dish + "");
                break;
            case WEEK_DISH_CODE:
                dish = (DishBean) getIntent().getSerializableExtra("week_dish_data");
                LogUtils.i(TAG, dish + "");
                break;
            default:
                break;
        }
        setDishDetailUI();
        uploadDish();
        initListView();
        dynamicAddSkinEnableView(toolbar, "background", R.color.colorPrimary);
        dynamicAddSkinEnableView(dishCommentBn,"background",R.color.colorPrimary);
    }

    @Override
    protected void setListener() {
        dishCommentBn.setOnSendClickListener(new SendCommentButton.onSendClickListener() {
            @Override
            public void onSendClick(View view) {
                if (validateComment()) {
                    dishCommentEt.setText(null);
                    dishCommentBn.setCurrentState(SendCommentButton.STATE_DONE);

                }
            }
        });
    }

    public boolean validateComment() {
        if (isLogin()) {
            String commentContent = dishCommentEt.getText().toString().trim();
            if (commentContent.isEmpty()) {
                ToastUtils.showToast(context, R.string.no_empty_comment, Toast.LENGTH_SHORT);
                dishCommentBn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_empty));
                return false;
            } else {
                LogUtils.i(TAG, commentContent);
                try {
                    publishComment(commentContent);
                    hideSoftKeyBoard();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        } else {
            ToastUtils.showToast(context, R.string.please_login_first, Toast.LENGTH_SHORT);
            Intent intent = new Intent(DishDetailActivity.this, LoginActivity.class);
            startActivity(intent);
            return false;
        }

    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("菜品");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setDishDetailUI() {
        try {
            if (dish.getDish_Cover().getPath() != null) {
                Picasso.with(this)
                        .load(dish.getDish_Cover().getPath())
                        .resize(180, 150)
                        .centerInside()
                        .into(dishImage);
                dishName.setText(dish.getName());
                dishPrice.setText(dish.getPrice() + "元");
                dishTaste.setText(dish.getTastes());
                try {
                    dishWindow.setText(dish.getDish_Window().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }


                dishPraise.setText(dish.getPraise());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadDish() {
        dish.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });
    }

    private void initListView() {
        adapter = new DishCommentAdapter(context, list);
        commentLv.setAdapter(adapter);
        if (isLogin()) {
            queryComment();
        }
        BaseUtils.setListViewHeightBasedOnChildren(commentLv);
        commentLv.setCacheColorHint(0);
        commentLv.setScrollingCacheEnabled(false);
        commentLv.setScrollContainer(false);
        commentLv.setFastScrollEnabled(true);
        commentLv.setSmoothScrollbarEnabled(true);
        commentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showToast(DishDetailActivity.this, "" + (position + 1), Toast.LENGTH_SHORT);
            }
        });
    }

    @OnClick({R.id.dish_detail_load_more_comments, R.id.dish_detail_image})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dish_detail_load_more_comments:
                if (!isLogin()) {
                    ToastUtils.showToast(context, R.string.please_login_first, Toast.LENGTH_SHORT);
                    Intent intent = new Intent(DishDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    queryComment();
                }
                break;
            case R.id.dish_detail_image:
                Intent intent = new Intent(this, ImageDetailActivity.class);
                intent.putExtra("image_url", dish.getDish_Cover().getPath());
                startActivity(intent);
            default:
                break;
        }
    }

    private void publishComment(String commentContent) {
        User user = BmobUser.getCurrentUser(context, User.class);
        LogUtils.i(TAG, user.getUsername());
        final Comment comment = new Comment();
        LogUtils.i(TAG, comment.toString());
        comment.setUser(user);
        comment.setCommentContent(commentContent);
        comment.setDishObject(dish);
        comment.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showToast(DishDetailActivity.this, R.string.comment_success, Toast.LENGTH_SHORT);
                list.add(comment);
                adapter.notifyDataSetChanged();
                BaseUtils.setListViewHeightBasedOnChildren(commentLv);
                dishCommentEt.setText("");
                dish.update(DishDetailActivity.this, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showToast(DishDetailActivity.this, R.string.upload_post_fail, Toast.LENGTH_SHORT);
            }
        });
    }

    private void queryComment() {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.order("-createdAt");
        query.addWhereEqualTo("dishObject", new BmobPointer(dish));
        query.setLimit(Constant.NUM_PER_PAGE);
        BmobDate CurrentTime = new BmobDate(new Date(System.currentTimeMillis()));
        query.include("user");
        query.findObjects(this, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> lists) {
                if (lists.size() != 0 && lists.get(lists.size() - 1) != null) {
                    if (lists.size() < Constant.NUM_PER_PAGE)
                        loadMoreComment.setText("暂无更多评论");
                    ToastUtils.showToast(DishDetailActivity.this, R.string.load_comment_success, Toast.LENGTH_SHORT);
                    list.addAll(lists);
                    LogUtils.i(TAG, lists.size() + "");
                    adapter.notifyDataSetChanged();
                    BaseUtils.setListViewHeightBasedOnChildren(commentLv);
                } else {
                    loadMoreComment.setText("暂无更多评论");
                    ToastUtils.showToast(DishDetailActivity.this, R.string.no_more_comments, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.showToast(DishDetailActivity.this, R.string.query_fail, Toast.LENGTH_SHORT);

            }
        });

    }

    public boolean isLogin() {
        BmobUser user = BmobUser.getCurrentUser(this, User.class);
        if (user != null) {
            return true;
        }
        return false;
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(dishCommentEt.getWindowToken(), 0);

    }


}

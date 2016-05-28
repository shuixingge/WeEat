package com.bupt.weeat.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bupt.weeat.R;
import com.bupt.weeat.adapter.RecipeAdapter;
import com.bupt.weeat.model.entity.RecipeBean;
import com.bupt.weeat.presenter.RecipePresenter;
import com.bupt.weeat.ui.activity.RecipeDetailActivity;
import com.bupt.weeat.utils.BaseUtils;
import com.bupt.weeat.utils.LogUtils;
import com.bupt.weeat.utils.ToastUtils;
import com.bupt.weeat.view.customView.RecyclerItemClickListener;
import com.bupt.weeat.view.customView.SwipeLoadRefreshLayout;
import com.bupt.weeat.view.mvpView.RecipeBeanListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;

/**
 * Created by zhaoruolei1992 on 2016/5/23.
 */
public class RecipeFragment extends BaseFragment implements RecipeBeanListView {
    @Bind(R.id.recipe_activity_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.recipe_refresh_load)
    SwipeLoadRefreshLayout swipeRefreshLayout;
    @Bind(R.id.fab_search)
    FloatingActionButton fab;
    private ArrayList<RecipeBean> recipeLists = new ArrayList<>();
    RecipePresenter presenter;
    private int page = 1;
    private RecipeAdapter adapter;
    private static final String TAG = RecipeFragment.class.getSimpleName();
    private String keyWord;
    private EditText mETInput;
    private AlertDialog mInputDialog;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recipe;
    }

    @Override
    protected void initData() {
        super.initData();
        LogUtils.i(TAG, "initData()");
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary,
                R.color.colorAccent);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecipeAdapter(context, recipeLists);
        recyclerView.setAdapter(adapter);
        presenter = new RecipePresenter(context);
        presenter.attachView(this);
        getRandomList();
        initInputDialog();
        dynamicAddSkinView(fab, "backgroundTint", R.color.colorAccent);

    }

    private void getRandomList() {
        LogUtils.i(TAG, "getRandomList()");
        String[] keyWords = {"红烧肉", "辣子鸡", "东坡肉"};
        Random random = new Random();
        int n = random.nextInt(keyWords.length);
        keyWord = keyWords[n];
        presenter.loadList(page, keyWord);
    }

    @Override
    protected void setListener() {
        super.setListener();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("recipe", recipeLists.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                presenter.loadList(page, keyWord);
            }
        });
        swipeRefreshLayout.setOnLoadListener(new SwipeLoadRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                page++;
                presenter.loadList(page, keyWord);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputDialog.show();

            }
        });
    }

    @Override
    public void loadMore(List<RecipeBean> list) {
        if (BaseUtils.isEmpty(list)) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setLoading(false);
        } else {
            recipeLists.addAll(list);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setLoading(false);
        }

    }

    @Override
    public void refresh(List<RecipeBean> list) {
        recipeLists.clear();
        recipeLists.addAll(list);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        LogUtils.i(TAG, list + "");
    }

    private void initInputDialog() {
        mETInput = new EditText(context);
        mETInput.setTextColor(Color.parseColor("#292929"));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请输入关键字");


        builder.setView(mETInput);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                keyWord = mETInput.getText().toString();
                if ("".equals(keyWord)) {//如果用户输入的关键字为空，我们就按照最开始的数据加载方式加载
                    getRandomList();
                } else {
                    presenter.loadList(page, keyWord);
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mETInput.setText("");
            }
        });
        mInputDialog = builder.create();

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {
        page--;
        if (page <= 1)
            page = 1;
        ToastUtils.showToast(context, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener, int imageId) {

    }

    @Override
    public void showNetError(View.OnClickListener onClickListener) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
package com.bupt.weeat.ui.fragment;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bupt.weeat.R;
import com.bupt.weeat.adapter.NewDishAdapter;
import com.bupt.weeat.model.entity.DishBean;
import com.bupt.weeat.presenter.NewDishPresenter;
import com.bupt.weeat.ui.activity.DishDetailActivity;
import com.bupt.weeat.utils.LogUtils;
import com.bupt.weeat.view.customView.RecyclerItemClickListener;
import com.bupt.weeat.view.mvpView.DishBeanListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class NewDishFragment extends BaseFragment implements DishBeanListView {
    @Bind(R.id.new_dish_recycler)
    RecyclerView recyclerView;
    private int page = 1;
    private static final int NEW_DISH_CODE = 0;
    private ArrayList<DishBean> list=new ArrayList<>();;
    private static final String TAG = NewDishFragment.class.getSimpleName();
    private NewDishAdapter adapter;
    private NewDishPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.new_dish_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter = new NewDishAdapter(list, context);
        recyclerView.setAdapter(adapter);
        presenter=new NewDishPresenter(context);
        presenter.attachView(this);
        presenter.loadList(page);

    }

    @Override
    protected void setListener() {
        super.setListener();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogUtils.i(TAG, "onItemClick");
                try {
                    Intent intent = new Intent(getActivity(), DishDetailActivity.class);
                    intent.putExtra("new_dish_data", list.get(position));
                    intent.putExtra("DISH_CODE", NEW_DISH_CODE);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        }
            ));
        }

    public static NewDishFragment newInstance() {
        return new NewDishFragment();

    }

    @Override
    public void refresh(List<DishBean> beanList) {
        list.clear();
        list.addAll(beanList);
        adapter.notifyDataSetChanged();
        LogUtils.i(TAG, list + "");
    }

    @Override
    public void loadMore(List<DishBean> list) {

    }


    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener, int imageId) {
        super.showEmpty(msg, onClickListener, imageId);
    }

    @Override
    public void showNetError(View.OnClickListener onClickListener) {
        super.showNetError(onClickListener);
    }
}

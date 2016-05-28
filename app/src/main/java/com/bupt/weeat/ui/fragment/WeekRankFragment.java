package com.bupt.weeat.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bupt.weeat.R;
import com.bupt.weeat.adapter.WeekRankAdapter;
import com.bupt.weeat.model.entity.DishBean;
import com.bupt.weeat.presenter.WeekRankPresenter;
import com.bupt.weeat.ui.activity.DishDetailActivity;
import com.bupt.weeat.utils.LogUtils;
import com.bupt.weeat.view.customView.RecyclerItemClickListener;
import com.bupt.weeat.view.mvpView.DishBeanListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class WeekRankFragment extends BaseFragment implements DishBeanListView {
    @Bind(R.id.week_rank_recycler)
    RecyclerView recyclerView;
    private ArrayList<DishBean> list = new ArrayList<>();
    private static final int WEEK_DISH_CODE = 1;
    private WeekRankAdapter adapter;
    private static final String TAG = WeekRankFragment.class.getSimpleName();
    private WeekRankPresenter presenter;
    private int page = 1;
    @Override
    protected int getLayoutId() {
        return R.layout.weekly_rank_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter = new WeekRankAdapter(context, list);
        recyclerView.setAdapter(adapter);
        presenter=new WeekRankPresenter(context);
        presenter.attachView(this);
        presenter.loadList(page);

    }


    @Override
    protected void setListener() {
        super.setListener();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    Log.i(TAG, "onItemClick");
                    Intent intent = new Intent(getActivity(), DishDetailActivity.class);
                    intent.putExtra("week_dish_data", list.get(position));
                    intent.putExtra("DISH_CODE", WEEK_DISH_CODE);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

    }

    @Override
    public void refresh(List<DishBean> beanList) {
        list.clear();
        list.addAll(beanList);
        adapter.notifyDataSetChanged();
        LogUtils.i(TAG, list + "");
    }

    @Override
    public void loadMore(List<DishBean> beanList) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public static WeekRankFragment newInstance() {

        return new WeekRankFragment();


    }

}

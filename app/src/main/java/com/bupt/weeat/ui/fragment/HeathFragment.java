package com.bupt.weeat.ui.fragment;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bupt.weeat.R;
import com.bupt.weeat.adapter.HealthAdapter;
import com.bupt.weeat.ui.activity.HeatStatisticsActivity;
import com.bupt.weeat.ui.activity.RecipeRecommendActivity;
import com.bupt.weeat.utils.LogUtils;
import com.bupt.weeat.view.customView.RecyclerItemClickListener;

import butterknife.Bind;

public class HeathFragment extends BaseFragment {
    private static final int HEAT_STATISTICS = 0;
    private static final int RECIPE_RECOMMENDED = 1;
    @Bind(R.id.health_recycler)
    RecyclerView recyclerView;
    private static final String TAG = HeathFragment.class.getSimpleName();

    @Override
    protected int getLayoutId() {
        return R.layout.heath_fragment_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new HealthAdapter(context));
    }

    @Override
    protected void setListener() {
        super.setListener();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == HEAT_STATISTICS) {
                    LogUtils.i(TAG, "onClick");
                    Intent intent = new Intent(context, HeatStatisticsActivity.class);
                    startActivity(intent);
                } else if (position == RECIPE_RECOMMENDED) {
                    Intent intent = new Intent(context, RecipeRecommendActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }


}

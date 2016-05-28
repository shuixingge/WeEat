package com.bupt.weeat.ui.activity;


import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bupt.weeat.R;

import butterknife.Bind;

public class HeatStatisticsActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.add_today_recipe)
    FloatingActionButton fab;

    @Override
    public int getLayoutId() {
        return R.layout.activity_heat_statistic;
    }


    @Override
    protected void initData() {
        super.initData();
        setSupportActionBar(toolbar);
        setTitle("健康饮食");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        dynamicAddSkinEnableView(toolbar, "background", R.color.colorPrimary);
    }

    @Override
    protected void setListener() {
        super.setListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /**/
            }
        });
    }
}

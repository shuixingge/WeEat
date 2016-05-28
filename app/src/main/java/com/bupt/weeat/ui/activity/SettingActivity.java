package com.bupt.weeat.ui.activity;


import android.content.Intent;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.bupt.weeat.R;

import butterknife.Bind;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.linear_about)
    LinearLayout linearAbout;

    AppCompatCheckBox wifiCheckBox, themeCheckBox, netCheckBox;
    LinearLayout linearWifi, linearNetWork, linearTheme, linearWhat, linearAuthor;
    int screenWidth, screenHeight;

    @Override
    protected void initData() {
        super.initData();
        initToolbar();


    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle("设置");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
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
     linearAbout.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_about:
                Intent intent = new Intent(SettingActivity.this, AboutAuthorActivity.class);
                startActivity(intent);
                break;

        }
    }
}

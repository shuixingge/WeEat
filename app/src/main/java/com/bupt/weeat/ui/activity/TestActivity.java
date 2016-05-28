package com.bupt.weeat.ui.activity;

import android.support.design.widget.NavigationView;

import com.bupt.weeat.R;

import butterknife.Bind;

public class TestActivity extends BaseActivity {
    @Bind(R.id.nav)
    NavigationView nav;
   /* @Bind(R.id.toolbar)
    Toolbar toolbar;*/


    @Override
    public int getLayoutId() {
        return R.layout.main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

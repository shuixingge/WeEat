package com.bupt.weeat.ui.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.bupt.weeat.R;
import com.bupt.weeat.adapter.FindPagerAdapter;

import butterknife.Bind;


public class FindFragment extends BaseFragment {
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.find_fragment_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        viewpager.setAdapter(new FindPagerAdapter(getChildFragmentManager()));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewpager);
        dynamicAddSkinView(tabLayout, "tabIndicatorColor", R.color.colorAccent);
    }
}


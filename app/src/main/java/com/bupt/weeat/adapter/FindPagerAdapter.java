package com.bupt.weeat.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bupt.weeat.ui.fragment.RecommendationFragment;
import com.bupt.weeat.ui.fragment.NewDishFragment;
import com.bupt.weeat.ui.fragment.WeekRankFragment;



public class FindPagerAdapter extends FragmentPagerAdapter {
    private static final int RECOMMENDATION_FRAGMENT = 0;
    private static final int NEW_PRODUCT_FRAGMENT = 1;
    private static final int WEEK_RANK_FRAGMENT = 2;
    private String[] FindTabArray = { "热门推荐", "新品速递", "每周榜单" };

    public FindPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int arg0) {
        switch (arg0) {
            case RECOMMENDATION_FRAGMENT:
                return RecommendationFragment.newInstance();
            case NEW_PRODUCT_FRAGMENT:
                return NewDishFragment.newInstance();
            case WEEK_RANK_FRAGMENT:
                return WeekRankFragment.newInstance();
            default:
                return WeekRankFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return FindTabArray.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FindTabArray[position];
    }
}

package com.bupt.weeat.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bupt.weeat.skinloader.base.SkinBaseFragment;
import com.bupt.weeat.view.mvpView.MvpView;

import butterknife.ButterKnife;

public abstract class BaseFragment extends SkinBaseFragment implements MvpView {
    protected Context context;
    protected View rootView;
    protected boolean isVisible;

    protected boolean isFirst=true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        initData();
        setListener();
        return rootView;
    }

    protected abstract int getLayoutId();

    protected void initData() {

    }

    protected void setListener()
    {
    }


    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {

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
}

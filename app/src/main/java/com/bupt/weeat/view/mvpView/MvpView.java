package com.bupt.weeat.view.mvpView;

import android.view.View;

/**
 * Created by zhaoruolei1992 on 2016/5/21.
 */
public interface MvpView {

    void showLoading(String msg);

    void hideLoading();

    void showError(String msg, View.OnClickListener onClickListener);

    void showEmpty(String msg, View.OnClickListener onClickListener);

    void showEmpty(String msg, View.OnClickListener onClickListener,int imageId);

    void showNetError(View.OnClickListener onClickListener);

}

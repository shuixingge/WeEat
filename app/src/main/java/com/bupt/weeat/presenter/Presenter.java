package com.bupt.weeat.presenter;

import com.bupt.weeat.view.mvpView.MvpView;

/**
 * Created by zhaoruolei1992 on 2016/5/21.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}

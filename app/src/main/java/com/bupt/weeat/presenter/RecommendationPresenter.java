package com.bupt.weeat.presenter;

import android.content.Context;

import com.bupt.weeat.apis.RxService.RecommendationDishService;
import com.bupt.weeat.model.entity.DishBean;
import com.bupt.weeat.model.response.RecommendationResponse;
import com.bupt.weeat.apis.DishApi;
import com.bupt.weeat.view.mvpView.DishBeanListView;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoruolei1992 on 2016/5/22.
 */
public class RecommendationPresenter extends BasePresenter<DishBeanListView> {
    private Subscription mSubscription;
    private  RecommendationDishService service;

    public RecommendationPresenter(Context context) {
        service=DishApi.createApi(context, RecommendationDishService.class);
    }

    @Override
    public void attachView(DishBeanListView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null)
            mSubscription.unsubscribe();
    }
    public void loadList() {
       mSubscription= service.getRecommendationList()
                .subscribeOn(Schedulers.io())
                .map(new Func1<RecommendationResponse, List<DishBean>>() {
                    @Override
                    public List<DishBean> call(RecommendationResponse response) {
                        return  response.getData().getDish_hotest();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DishBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {


                    }

                    @Override
                    public void onNext(List<DishBean> beanList) {
                        getMvpView().refresh(beanList);

                    }
                });

    }
    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    @Override
    public DishBeanListView getMvpView() {
        return super.getMvpView();
    }

    @Override
    public void checkViewAttached() {
        super.checkViewAttached();
    }
}

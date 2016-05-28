package com.bupt.weeat.presenter;

import android.content.Context;

import com.bupt.weeat.apis.DishApi;
import com.bupt.weeat.apis.RxService.WeekRankDishService;
import com.bupt.weeat.model.entity.DishBean;
import com.bupt.weeat.model.entity.Window;
import com.bupt.weeat.model.response.WeekRankDishResponse;
import com.bupt.weeat.utils.LogUtils;
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
public class WeekRankPresenter extends BasePresenter<DishBeanListView> {
    private Subscription mSubscription;
    private WeekRankDishService service;

    public WeekRankPresenter(Context context) {
        service = DishApi.createApi(context, WeekRankDishService.class);
    }

    public void loadList(final int page) {
        checkViewAttached();
        mSubscription = service.getWeekRankList()
                .subscribeOn(Schedulers.io())
                .map(new Func1<WeekRankDishResponse, List<DishBean>>() {
                    @Override
                    public List<DishBean> call(WeekRankDishResponse response) {
                        List<Window> windows = response.getData().getWindows();
                        List<DishBean> beans = response.getData().getDishes();
                        for (int i = 0; i < beans.size(); i++) {
                            DishBean bean = beans.get(i);
                            DishBean.DishWindow window = new DishBean.DishWindow();
                            window.setName(windows.get(i).getWindow_Cover().getName());
                            bean.setDish_Window(window);
                        }
                        return beans;
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
                        if (page == 1) {
                            getMvpView().refresh(beanList);
                            LogUtils.i("tag", beanList.toString());
                        } else {
                            getMvpView().loadMore(beanList);
                        }

                    }
                });

    }

    @Override
    public void attachView(DishBeanListView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
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

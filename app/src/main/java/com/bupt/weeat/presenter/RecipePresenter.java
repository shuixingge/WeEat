package com.bupt.weeat.presenter;

import android.content.Context;

import com.bupt.weeat.Constant;
import com.bupt.weeat.apis.RxService.RecipeService;
import com.bupt.weeat.model.entity.RecipeBean;
import com.bupt.weeat.model.response.RecipeResponse;
import com.bupt.weeat.utils.LogUtils;
import com.bupt.weeat.view.mvpView.RecipeBeanListView;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoruolei1992 on 2016/5/23.
 */
public class RecipePresenter extends BasePresenter<RecipeBeanListView> {
    private Subscription mSubscription;
    private RecipeService service;
    Context context;

    public RecipePresenter(Context context) {
        getRecipeService();
        this.context = context;
    }

    public RecipeService getRecipeService() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.RECIPE_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(RecipeService.class);
        }
        return service;
    }

    public void loadList(final int page, String name) {
        checkViewAttached();
        LogUtils.i("tag", "loadList");
        mSubscription = service.getRecipeList(name,Constant.JUHE_KEY)
                .subscribeOn(Schedulers.io())
                .map(new Func1<RecipeResponse, List<RecipeBean>>() {
                    @Override
                    public List<RecipeBean> call(RecipeResponse recipeResponse) {
                        LogUtils.i("tag", "call");
                        return recipeResponse.getResult().getData();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<RecipeBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<RecipeBean> list) {
                        LogUtils.i("tag", list.toString());
                        if (page == 1) {
                            getMvpView().refresh(list);
                        } else {
                            getMvpView().loadMore(list);
                        }
                    }
                });


    }

    @Override
    public void attachView(RecipeBeanListView mvpView) {
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
    public RecipeBeanListView getMvpView() {
        return super.getMvpView();
    }

    @Override
    public void checkViewAttached() {
        super.checkViewAttached();
    }
}

package com.bupt.weeat.presenter;

import android.content.Context;

import com.bupt.weeat.Constant;
import com.bupt.weeat.model.entity.Post;
import com.bupt.weeat.utils.LogUtils;
import com.bupt.weeat.view.mvpView.SquarePostListView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhaoruolei1992 on 2016/5/23.
 */
public class SquarePresenter extends BasePresenter<SquarePostListView> {
    private static final String TAG = SquarePresenter.class.getSimpleName();
    private Context context;

    public SquarePresenter(Context context) {
        this.context = context;
    }

    public void loadList(final int page) {
        checkViewAttached();
        final BmobQuery<Post> query = new BmobQuery<>();
        query.order("-createdAt");
        query.include("author");
        query.setLimit(Constant.NUM_PER_PAGE);
        query.findObjects(context, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                LogUtils.i(TAG, "query success " + list.size());
                if (page == 1) {
                    getMvpView().refresh(list);
                } else {
                    getMvpView().loadMore(list);
                }
            }

            @Override
            public void onError(int i, String s) {
                LogUtils.i(TAG, "query fail");
                getMvpView().showError(s);
            }
        });
    }

    @Override
    public void attachView(SquarePostListView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    @Override
    public SquarePostListView getMvpView() {
        return super.getMvpView();
    }

    @Override
    public void checkViewAttached() {
        super.checkViewAttached();
    }
}

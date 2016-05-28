package com.bupt.weeat.view.mvpView;

import com.bupt.weeat.model.entity.RecipeBean;

import java.util.List;

/**
 * Created by zhaoruolei1992 on 2016/5/23.
 */
public interface RecipeBeanListView extends MvpView {
    void refresh(List<RecipeBean> data);

    void loadMore(List<RecipeBean> data);
}

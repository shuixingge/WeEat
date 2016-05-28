package com.bupt.weeat.view.mvpView;


import com.bupt.weeat.model.entity.DishBean;

import java.util.List;

/**
 * Created by zhaoruolei1992 on 2016/5/21.
 */
public interface DishBeanListView extends MvpView {
    void refresh(List<DishBean> data);

    void loadMore(List<DishBean> data);
}

package com.bupt.weeat.apis.RxService;

import com.bupt.weeat.model.response.NewDishResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zhaoruolei1992 on 2016/5/21.
 */
public interface NewDishService {
    @GET("/WeEat/back/WeEat/index.php?r=home/new")
    Observable<NewDishResponse> getNewList();
}

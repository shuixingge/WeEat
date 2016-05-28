package com.bupt.weeat.apis.RxService;

import com.bupt.weeat.model.response.WeekRankDishResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zhaoruolei1992 on 2016/5/21.
 */
public interface WeekRankDishService {
    @GET("/WeEat/back/WeEat/index.php?r=home/hotest")
    Observable<WeekRankDishResponse> getWeekRankList();
}

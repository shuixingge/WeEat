package com.bupt.weeat.apis.RxService;

import com.bupt.weeat.model.response.RecipeResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhaoruolei1992 on 2016/5/23.
 */
public interface RecipeService {
    @GET("/cook/query")
    Observable<RecipeResponse> getRecipeList(@Query("menu") String menu, @Query("key") String key);
}

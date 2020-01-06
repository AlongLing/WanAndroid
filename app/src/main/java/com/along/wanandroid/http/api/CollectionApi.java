package com.along.wanandroid.http.api;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.home.model.bean.ArticleList;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CollectionApi {

    @GET("/lg/collect/list/{pageNo}/json")
    Observable<BaseBean<ArticleList>> getCollections(@Path("pageNo") int pageNo);

    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<BaseBean<Object>> cancelCollectArticle(@Path("id") int id, @Field("originId") int originid);

}

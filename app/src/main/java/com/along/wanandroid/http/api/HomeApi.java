package com.along.wanandroid.http.api;



import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.home.model.bean.BannerBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HomeApi {

    /***
     * 获取首页banner数据
     * @return
     */
    @GET("/banner/json")
    Observable<BaseBean<List<BannerBean>>> getHomeBannerData();

    /***
     * 获取首页文章列表数据
     * @return
     */
    @GET("article/list/{pageNo}/json")
    Observable<BaseBean<ArticleList>> getArticlesData(@Path("pageNo") int num);

    /***
     * 收藏文章
     * @param id
     * @return
     */
    @POST("lg/collect/{id}/json")
    Observable<BaseBean<Object>> collectArticle(@Path("id") int id);

    /***
     * 文章取消收藏
     * @param id
     * @return
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseBean<Object>> cancleCollectArticle(@Path("id") int id);
}

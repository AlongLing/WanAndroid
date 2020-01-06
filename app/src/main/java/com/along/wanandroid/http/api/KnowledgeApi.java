package com.along.wanandroid.http.api;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.knowledge.model.bean.KnowledgeBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KnowledgeApi {

    // 获取知识体系数据。
    @GET("/tree/json")
    Observable<BaseBean<List<KnowledgeBean>>> getKnowledgeData();

    // 获取某一个知识体系下面的文章数据。
    @GET("/article/list/{pageNo}/json")
    Observable<BaseBean<ArticleList>> getKnowledgeArticles(@Path("pageNo") int pageNo, @Query("cid") int cid);

}

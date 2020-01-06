package com.along.wanandroid.http.api;



import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.project.model.bean.ProjectCategory;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProjectApi {

    // 获取项目分类数据。
    @GET("/project/tree/json")
    Observable<BaseBean<List<ProjectCategory>>> getProjectCategory();

    // 获取某一个项目分类下面的列表数据。
    @GET("/project/list/{pageNo}/json")
    Observable<BaseBean<ArticleList>> getProjectArticles(@Path("pageNo") int pageNo, @Query("cid") int cid);
}

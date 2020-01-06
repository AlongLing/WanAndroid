package com.along.wanandroid.http.api;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.navigation.model.bean.NavigationBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NavigationApi {

    @GET("/navi/json")
    Observable<BaseBean<List<NavigationBean>>> getNavigationData();
}

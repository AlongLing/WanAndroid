package com.along.wanandroid.http.api;

import com.along.wanandroid.basemvp.bean.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MainApi {

    @GET("/user/logout/json")
    Observable<BaseBean<Object>> logout();

}

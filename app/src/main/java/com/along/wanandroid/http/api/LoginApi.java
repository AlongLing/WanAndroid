package com.along.wanandroid.http.api;



import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.login.model.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {

    @POST("/user/login")
    @FormUrlEncoded
    Observable<BaseBean<LoginBean>> login(@Field("username") String userName, @Field("password") String password);

    @POST("/user/register")
    @FormUrlEncoded
    Observable<BaseBean<LoginBean>> register(@Field("username") String userName, @Field("password") String password, @Field("repassword") String repssword);
}

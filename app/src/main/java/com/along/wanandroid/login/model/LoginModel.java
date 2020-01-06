package com.along.wanandroid.login.model;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BaseModel;
import com.along.wanandroid.contract.LoginContract;
import com.along.wanandroid.http.HttpHelper;
import com.along.wanandroid.http.api.LoginApi;
import com.along.wanandroid.login.model.bean.LoginBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginModel extends BaseModel implements LoginContract.Model {


    @Override
    public Observable<BaseBean<LoginBean>> login(Context context, String userName, String password) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(LoginApi.class)
                .login(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseBean<LoginBean>> register(Context context, String userName, String password, String repassword) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(LoginApi.class)
                .register(userName, password, repassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

package com.along.wanandroid.main.model;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BaseModel;
import com.along.wanandroid.contract.MainContract;
import com.along.wanandroid.http.HttpHelper;
import com.along.wanandroid.http.api.MainApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainModel extends BaseModel implements MainContract.Model {


    @Override
    public Observable<BaseBean<Object>> logout(Context context) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(MainApi.class)
                .logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

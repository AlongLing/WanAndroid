package com.along.wanandroid.navigation.model;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BaseModel;
import com.along.wanandroid.contract.NavigationContract;
import com.along.wanandroid.http.HttpHelper;
import com.along.wanandroid.http.api.NavigationApi;
import com.along.wanandroid.navigation.model.bean.NavigationBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NavigationModel extends BaseModel implements NavigationContract.Model {


    @Override
    public Observable<BaseBean<List<NavigationBean>>> getNavigationData(Context context) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(NavigationApi.class)
                .getNavigationData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

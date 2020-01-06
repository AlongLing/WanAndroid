package com.along.wanandroid.home.model;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BaseModel;
import com.along.wanandroid.contract.HomeContract;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.home.model.bean.BannerBean;
import com.along.wanandroid.http.HttpHelper;
import com.along.wanandroid.http.api.HomeApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeModel extends BaseModel implements HomeContract.Model {


    @Override
    public Observable<BaseBean<List<BannerBean>>> getBannerData(Context context) {
        return HttpHelper.getInstance(context)
                .getRetrofitClient()
                .builder(HomeApi.class)
                .getHomeBannerData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseBean<ArticleList>> getArticlesData(Context context, int pageNo) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(HomeApi.class)
                .getArticlesData(pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseBean<Object>> collectArticle(Context context, int id) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(HomeApi.class)
                .collectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseBean<Object>> cancelCollectArticle(Context context, int id) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(HomeApi.class)
                .cancleCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

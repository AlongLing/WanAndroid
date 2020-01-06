package com.along.wanandroid.collection.model;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BaseModel;
import com.along.wanandroid.contract.CollectionContract;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.http.HttpHelper;
import com.along.wanandroid.http.api.CollectionApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CollectionModel extends BaseModel implements CollectionContract.Model {


    @Override
    public Observable<BaseBean<ArticleList>> getCollections(Context context, int pageNo) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(CollectionApi.class)
                .getCollections(pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseBean<Object>> cancelCollectArticle(Context context, int id, int originId) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(CollectionApi.class)
                .cancelCollectArticle(id, originId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

package com.along.wanandroid.knowledge.model;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BaseModel;
import com.along.wanandroid.contract.KnowledgeListContract;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.http.HttpHelper;
import com.along.wanandroid.http.api.KnowledgeApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KnowledgeListModel extends BaseModel implements KnowledgeListContract.Model {


    @Override
    public Observable<BaseBean<ArticleList>> getKnowledgeArticles(Context context, int pageNo, int cid) {

        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(KnowledgeApi.class)
                .getKnowledgeArticles(pageNo, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}

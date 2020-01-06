package com.along.wanandroid.knowledge.model;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BaseModel;
import com.along.wanandroid.contract.KnowledgeContract;
import com.along.wanandroid.http.HttpHelper;
import com.along.wanandroid.http.api.KnowledgeApi;
import com.along.wanandroid.knowledge.model.bean.KnowledgeBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KnowledgeModel extends BaseModel implements KnowledgeContract.Model {


    @Override
    public Observable<BaseBean<List<KnowledgeBean>>> getKnowledgeData(Context context) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(KnowledgeApi.class)
                .getKnowledgeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

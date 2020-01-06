package com.along.wanandroid.project.model;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BaseModel;
import com.along.wanandroid.contract.ProjectListContract;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.http.HttpHelper;
import com.along.wanandroid.http.api.ProjectApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectListModel extends BaseModel implements ProjectListContract.Model {


    @Override
    public Observable<BaseBean<ArticleList>> getProjectArticles(Context context, int pageNo, int cid) {
        return HttpHelper.getInstance(context)
                .getRetrofitClient()
                .builder(ProjectApi.class)
                .getProjectArticles(pageNo, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

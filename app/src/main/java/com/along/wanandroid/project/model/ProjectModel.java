package com.along.wanandroid.project.model;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BaseModel;
import com.along.wanandroid.contract.ProjectContract;
import com.along.wanandroid.http.HttpHelper;
import com.along.wanandroid.http.api.ProjectApi;
import com.along.wanandroid.project.model.bean.ProjectCategory;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectModel extends BaseModel implements ProjectContract.Model {


    @Override
    public Observable<BaseBean<List<ProjectCategory>>> getProjectCategory(Context context) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(ProjectApi.class)
                .getProjectCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

package com.along.wanandroid.project.presenter;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BasePresenter;
import com.along.wanandroid.contract.ProjectContract;
import com.along.wanandroid.home.model.HomeModel;
import com.along.wanandroid.project.model.ProjectModel;
import com.along.wanandroid.project.model.bean.ProjectCategory;
import com.along.wanandroid.utils.AppLog;

import java.util.List;

import io.reactivex.functions.Consumer;

public class ProjectPresenter extends BasePresenter<ProjectContract.View, ProjectModel> implements ProjectContract.Presenter {

    private static final String TAG = "ProjectPresenter";

    @Override
    public void initData(Context context) {
        /***
         * 初始化项目分类数据。
         */
        getModel().getProjectCategory(context).subscribe(new Consumer<BaseBean<List<ProjectCategory>>>() {
            @Override
            public void accept(BaseBean<List<ProjectCategory>> listBaseBean) throws Exception {
                getView().showTabs(listBaseBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                AppLog.debug(TAG, "initData: throwable = " + throwable);
            }
        });
    }
}

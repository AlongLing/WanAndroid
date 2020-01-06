package com.along.wanandroid.project.presenter;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BasePresenter;
import com.along.wanandroid.contract.ProjectListContract;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.project.model.ProjectListModel;
import com.along.wanandroid.utils.AppLog;

import io.reactivex.functions.Consumer;

public class ProjectListPresenter extends BasePresenter<ProjectListContract.View, ProjectListModel> implements ProjectListContract.Presenter {

    private static final String TAG = "ProjectListPresenter";

    @Override
    public void getProjectArticles(Context context, int pageNo, int cid) {
        getModel().getProjectArticles(context, pageNo, cid).subscribe(new Consumer<BaseBean<ArticleList>>() {
            @Override
            public void accept(BaseBean<ArticleList> articleListBaseBean) throws Exception {
                getView().showArticleList(articleListBaseBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                AppLog.debug(TAG, "getProjectArticles: throwable = " + throwable);
            }
        });
    }
}

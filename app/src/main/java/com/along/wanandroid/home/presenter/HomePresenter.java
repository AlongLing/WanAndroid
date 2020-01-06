package com.along.wanandroid.home.presenter;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BasePresenter;
import com.along.wanandroid.contract.HomeContract;
import com.along.wanandroid.home.model.HomeModel;
import com.along.wanandroid.home.model.bean.ArticleDetailBean;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.utils.AppLog;

import io.reactivex.functions.Consumer;

public class HomePresenter extends BasePresenter<HomeContract.View, HomeModel> implements HomeContract.Presenter {

    private static final String TAG = "HomePresenter";

    @Override
    public void initData(Context context) {
        /***
         * 初始化banner数据
         */
        getModel().getBannerData(context).subscribe(listBaseBean ->
                getView().showBanner(listBaseBean.getData()), throwable ->
                AppLog.debug(TAG, "get banner data error: " + throwable.getMessage()));

        getModel().getArticlesData(context, 0).subscribe(articleListBaseBean ->
                getView().showArticleList(articleListBaseBean.getData().getDatas()), throwable ->
                AppLog.debug(TAG, "get articles data error: " + throwable.getMessage()));
    }

    @Override
    public void loadMoreArticles(Context context, int page) {
        getModel().getArticlesData(context, page).subscribe(new Consumer<BaseBean<ArticleList>>() {
            @Override
            public void accept(BaseBean<ArticleList> articleListBaseBean) throws Exception {
                getView().loadMoreArticles(articleListBaseBean.getData().getDatas());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                AppLog.debug(TAG, "load more articles error: " + throwable.getMessage());
            }
        });
    }

    @Override
    public void collectArticle(Context context, ArticleDetailBean data) {
        getModel().collectArticle(context, data.getId()).subscribe(objectBaseBean -> {
            if (objectBaseBean.getErrorCode() == 0) {
                AppLog.debug(TAG, "收藏成功");
            } else {
                AppLog.debug(TAG, "收藏失败");
            }
        }, throwable -> AppLog.debug(TAG, "collect article error: " + throwable.getMessage()));
    }

    @Override
    public void cancelCollectArticle(Context context, ArticleDetailBean data) {
        getModel().cancelCollectArticle(context, data.getId()).subscribe(objectBaseBean -> {

            if (objectBaseBean.getErrorCode() == 0) {
                AppLog.debug(TAG, "取消收藏成功");
            } else {
                AppLog.debug(TAG, "取消收藏失败");
            }
        }, throwable -> AppLog.debug(TAG, "cancle collect article error:" + throwable.getMessage()));
    }
}

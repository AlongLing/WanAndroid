package com.along.wanandroid.collection.presenter;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BasePresenter;
import com.along.wanandroid.collection.model.CollectionModel;
import com.along.wanandroid.contract.CollectionContract;
import com.along.wanandroid.home.model.bean.ArticleDetailBean;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.utils.AppLog;

import io.reactivex.functions.Consumer;

public class CollectionPresenter extends BasePresenter<CollectionContract.View, CollectionModel> implements CollectionContract.Presenter {

    private static final String TAG = "CollectionPresenter";

    @Override
    public void getCollections(Context context, int pageNo) {
        getModel().getCollections(context, pageNo).subscribe(new Consumer<BaseBean<ArticleList>>() {
            @Override
            public void accept(BaseBean<ArticleList> articleListBaseBean) throws Exception {
                AppLog.debug(TAG, "getCollections: accept articleListBaseBean = " + articleListBaseBean.getErrorMsg());
                getView().showArticleList(articleListBaseBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                AppLog.debug(TAG, "getCollections: throwable = " + throwable);
            }
        });
    }

    @Override
    public void cancelCollectArticle(Context context, ArticleDetailBean articleDetailBean, int position) {
        getModel().cancelCollectArticle(context, articleDetailBean.getId(), articleDetailBean.getOriginId())
                .subscribe(new Consumer<BaseBean<Object>>() {
                    @Override
                    public void accept(BaseBean<Object> objectBaseBean) throws Exception {
                        if (objectBaseBean.getErrorCode() == 0) {
                            getView().deleteArticle(position);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        AppLog.debug(TAG, "cancelCollectArticle: throwable = " + throwable);
                    }
                });
    }
}

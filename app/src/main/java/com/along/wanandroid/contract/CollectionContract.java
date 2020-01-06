package com.along.wanandroid.contract;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.interfaces.IBasePresenter;
import com.along.wanandroid.basemvp.interfaces.IBaseView;
import com.along.wanandroid.home.model.bean.ArticleDetailBean;
import com.along.wanandroid.home.model.bean.ArticleList;

import io.reactivex.Observable;

public interface CollectionContract {

    interface View extends IBaseView {

        void showArticleList(ArticleList articleList);

        void deleteArticle(int position);

    }

    interface Model {

        Observable<BaseBean<ArticleList>> getCollections(Context context, int pageNo);

        Observable<BaseBean<Object>> cancelCollectArticle(Context context, int id, int originId);

    }

    interface Presenter extends IBasePresenter {

        void getCollections(Context context, int pageNo);

        void cancelCollectArticle(Context context, ArticleDetailBean articleDetailBean, int position);

    }

}

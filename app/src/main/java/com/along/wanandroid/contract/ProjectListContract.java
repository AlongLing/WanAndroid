package com.along.wanandroid.contract;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.interfaces.IBasePresenter;
import com.along.wanandroid.basemvp.interfaces.IBaseView;
import com.along.wanandroid.home.model.bean.ArticleList;

import io.reactivex.Observable;

public interface ProjectListContract {

    interface View extends IBaseView {

        void showArticleList(ArticleList articleList);

    }

    interface Model {

        Observable<BaseBean<ArticleList>> getProjectArticles(Context context, int pageNo, int cid);

    }

    interface Presenter extends IBasePresenter {

        void getProjectArticles(Context context, int pageNo, int cid);

    }

}

package com.along.wanandroid.contract;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.interfaces.IBasePresenter;
import com.along.wanandroid.basemvp.interfaces.IBaseView;
import com.along.wanandroid.home.model.bean.ArticleList;

import io.reactivex.Observable;

public interface KnowledgeListContract {

    interface View extends IBaseView {

        void showKnowledgeArticles(ArticleList data);

    }

    interface Model {

        Observable<BaseBean<ArticleList>> getKnowledgeArticles(Context context, int pageNo, int cid);

    }

    interface Presenter extends IBasePresenter {

        void getKnowledgeArticles(Context context, int pageNo, int cid);

    }

}

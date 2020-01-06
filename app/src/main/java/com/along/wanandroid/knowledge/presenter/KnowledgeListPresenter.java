package com.along.wanandroid.knowledge.presenter;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BasePresenter;
import com.along.wanandroid.contract.KnowledgeListContract;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.knowledge.model.KnowledgeListModel;
import com.along.wanandroid.utils.AppLog;

import io.reactivex.functions.Consumer;

public class KnowledgeListPresenter extends BasePresenter<KnowledgeListContract.View, KnowledgeListModel> implements KnowledgeListContract.Presenter {

    private static final String TAG = "KnowledgeListPresenter";

    @Override
    public void getKnowledgeArticles(Context context, int pageNo, int cid) {
        getModel().getKnowledgeArticles(context, pageNo, cid).subscribe(new Consumer<BaseBean<ArticleList>>() {
            @Override
            public void accept(BaseBean<ArticleList> articleListBaseBean) throws Exception {
                getView().showKnowledgeArticles(articleListBaseBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                AppLog.debug(TAG, "getKnowledgeArticles: throwable = " + throwable);
            }
        });
    }
}

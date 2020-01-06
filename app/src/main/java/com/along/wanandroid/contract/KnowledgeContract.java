package com.along.wanandroid.contract;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.interfaces.IBasePresenter;
import com.along.wanandroid.basemvp.interfaces.IBaseView;
import com.along.wanandroid.knowledge.model.bean.KnowledgeBean;

import java.util.List;

import io.reactivex.Observable;

public interface KnowledgeContract {

    interface View extends IBaseView {

        void showKnowLedge(List<KnowledgeBean> knowledgeBeans);

    }

    interface Model {

        Observable<BaseBean<List<KnowledgeBean>>> getKnowledgeData(Context context);

    }

    interface Presenter extends IBasePresenter {

        /***
         * 初始化知识体系的数据。
         * @param context
         */
        void initData(Context context);

    }
 }

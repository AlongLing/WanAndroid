package com.along.wanandroid.knowledge.presenter;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BasePresenter;
import com.along.wanandroid.contract.KnowledgeContract;
import com.along.wanandroid.knowledge.model.KnowledgeModel;
import com.along.wanandroid.knowledge.model.bean.KnowledgeBean;
import com.along.wanandroid.utils.AppLog;

import java.util.List;

import io.reactivex.functions.Consumer;

public class KnowledgePresenter extends BasePresenter<KnowledgeContract.View, KnowledgeModel> implements KnowledgeContract.Presenter {

    private static final String TAG = "KnowledgePresenter";

    @Override
    public void initData(Context context) {
        getModel().getKnowledgeData(context).subscribe(new Consumer<BaseBean<List<KnowledgeBean>>>() {
            @Override
            public void accept(BaseBean<List<KnowledgeBean>> listBaseBean) throws Exception {
                getView().showKnowLedge(listBaseBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                AppLog.debug(TAG, "initData: throwable = " + throwable);
            }
        });
    }
}

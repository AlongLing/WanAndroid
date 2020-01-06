package com.along.wanandroid.main.presenter;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BasePresenter;
import com.along.wanandroid.contract.MainContract;
import com.along.wanandroid.main.model.MainModel;
import com.along.wanandroid.utils.AppLog;

import io.reactivex.functions.Consumer;

public class MainPresenter extends BasePresenter<MainContract.View, MainModel> implements MainContract.Presenter {

    private static final String TAG = "MainPresenter";

    @Override
    public void logout(Context context) {
        getModel().logout(context).subscribe(new Consumer<BaseBean<Object>>() {
            @Override
            public void accept(BaseBean<Object> objectBaseBean) throws Exception {
                AppLog.debug(TAG, "logout getErrorMsg = " + objectBaseBean.getErrorMsg());
                AppLog.debug(TAG, "logout getErrorCode = " + objectBaseBean.getErrorCode());
                getView().logoutSuccess();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                AppLog.debug(TAG, "logout: throwable = " + throwable);
            }
        });
    }
}

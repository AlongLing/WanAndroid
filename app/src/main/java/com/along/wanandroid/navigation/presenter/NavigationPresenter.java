package com.along.wanandroid.navigation.presenter;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BasePresenter;
import com.along.wanandroid.contract.NavigationContract;
import com.along.wanandroid.navigation.model.NavigationModel;
import com.along.wanandroid.navigation.model.bean.NavigationBean;
import com.along.wanandroid.utils.AppLog;

import java.util.List;

import io.reactivex.functions.Consumer;

public class NavigationPresenter extends BasePresenter<NavigationContract.View, NavigationModel> implements NavigationContract.Presenter {

    private static final String TAG = "NavigationPresenter";

    @Override
    public void requestNavigationData(Context context) {
        getModel().getNavigationData(context).subscribe(new Consumer<BaseBean<List<NavigationBean>>>() {
            @Override
            public void accept(BaseBean<List<NavigationBean>> listBaseBean) throws Exception {
                getView().showData(listBaseBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                AppLog.debug(TAG, "requestNavigationData: throwable = " + throwable);
            }
        });
    }
}

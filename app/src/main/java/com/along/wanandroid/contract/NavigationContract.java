package com.along.wanandroid.contract;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.interfaces.IBasePresenter;
import com.along.wanandroid.basemvp.interfaces.IBaseView;
import com.along.wanandroid.navigation.model.bean.NavigationBean;

import java.util.List;

import io.reactivex.Observable;

public interface NavigationContract {

    interface View extends IBaseView {

        void showData(List<NavigationBean> data);

    }

    interface Model {

        Observable<BaseBean<List<NavigationBean>>> getNavigationData(Context context);

    }

    interface Presenter extends IBasePresenter {

        void requestNavigationData(Context context);

    }

}

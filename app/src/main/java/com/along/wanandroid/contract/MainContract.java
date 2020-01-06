package com.along.wanandroid.contract;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.interfaces.IBasePresenter;
import com.along.wanandroid.basemvp.interfaces.IBaseView;

import io.reactivex.Observable;

public interface MainContract {

    interface View extends IBaseView {

        void logoutSuccess();

    }

    interface Model {

        Observable<BaseBean<Object>> logout(Context context);

    }

    interface Presenter extends IBasePresenter {

        void logout(Context context);

    }
}

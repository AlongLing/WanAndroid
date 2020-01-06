package com.along.wanandroid.contract;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.interfaces.IBasePresenter;
import com.along.wanandroid.basemvp.interfaces.IBaseView;
import com.along.wanandroid.login.model.bean.LoginBean;

import io.reactivex.Observable;

public interface LoginContract {

    interface View extends IBaseView {

        void loginSuccess(String userName);

        void loginFail(String errorMsg);

        void registerSuccess(LoginBean loginBean);

        void registerFail(String errorMsg);

    }

    interface Model {

        Observable<BaseBean<LoginBean>> login(Context context, String userName, String password);

        Observable<BaseBean<LoginBean>> register(Context context, String userName, String password, String repassword);

    }

    interface Presenter extends IBasePresenter {

        void requestLogin(Context context, String userName, String password);

        void requestRegister(Context context, String userName, String password, String repassword);

    }

}

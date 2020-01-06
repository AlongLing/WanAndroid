package com.along.wanandroid.login.presenter;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.impl.BasePresenter;
import com.along.wanandroid.contract.LoginContract;
import com.along.wanandroid.login.model.LoginModel;
import com.along.wanandroid.login.model.bean.LoginBean;
import com.along.wanandroid.utils.AppLog;

import io.reactivex.functions.Consumer;

public class LoginPresenter extends BasePresenter<LoginContract.View, LoginModel> implements LoginContract.Presenter {

    private static final String TAG = "LoginPresenter";

    @Override
    public void requestLogin(Context context, String userName, String password) {
        getModel().login(context, userName, password).subscribe(new Consumer<BaseBean<LoginBean>>() {
            @Override
            public void accept(BaseBean<LoginBean> loginBeanBaseBean) throws Exception {
                AppLog.debug(TAG, "requestLogin: loginBeanBaseBean.getErrorMsg() = " + loginBeanBaseBean.getErrorMsg());
                AppLog.debug(TAG, "requestLogin: loginBeanBaseBean.getErrorCode() = " + loginBeanBaseBean.getErrorCode());
                AppLog.debug(TAG, "requestLogin: loginBeanBaseBean.getData() = " + loginBeanBaseBean.getData());

                if (loginBeanBaseBean.getErrorCode() == 0) {
                    // 登录成功
                    getView().loginSuccess(loginBeanBaseBean.getData().getUsername());
                } else {
                    // 登录失败
                    getView().loginFail(loginBeanBaseBean.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                AppLog.debug(TAG, "requestLogin: throwable = " + throwable);
            }
        });
    }

    @Override
    public void requestRegister(Context context, String userName, String password, String repassword) {
        getModel().register(context, userName, password, repassword).subscribe(new Consumer<BaseBean<LoginBean>>() {
            @Override
            public void accept(BaseBean<LoginBean> loginBeanBaseBean) throws Exception {
                if (loginBeanBaseBean.getErrorCode() == 0) {
                    // 注册成功
                    getView().registerSuccess(loginBeanBaseBean.getData());
                } else {
                    // 注册失败
                    getView().registerFail(loginBeanBaseBean.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                AppLog.debug(TAG, "requestRegister: throwable = " + throwable);
            }
        });
    }
}

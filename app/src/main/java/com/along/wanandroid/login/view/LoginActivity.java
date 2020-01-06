package com.along.wanandroid.login.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.along.wanandroid.R;
import com.along.wanandroid.basemvp.InjectPresenter;
import com.along.wanandroid.basemvp.impl.BaseActivity;
import com.along.wanandroid.contract.LoginContract;
import com.along.wanandroid.login.LoginEvent;
import com.along.wanandroid.login.model.bean.LoginBean;
import com.along.wanandroid.login.presenter.LoginPresenter;
import com.along.wanandroid.utils.AppLog;
import com.along.wanandroid.utils.MyApplication;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.login_username)
    public EditText mUserName;

    @BindView(R.id.login_password)
    public EditText mPassword;

    @BindView(R.id.login_repassword)
    public EditText mRePassword;

    @BindView(R.id.register_text)
    public TextView mRegisterText;

    @BindView(R.id.forget_password_text)
    public TextView mForgetPasswordText;

    @BindView(R.id.login_back)
    public TextView mLoginBack;

    @BindView(R.id.login_btn)
    public Button mLoginBtn;

    @BindView(R.id.register_btn)
    public Button mRegisterBtn;

    @InjectPresenter
    private LoginPresenter mLoginPresenter;

    @Override
    protected int getXMLId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @OnClick({R.id.login_btn, R.id.register_btn, R.id.register_text, R.id.forget_password_text, R.id.login_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                String userName = mUserName.getText().toString();
                String password = mPassword.getText().toString();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    showToast("用户名和密码不能为空");
                } else {
                    mLoginPresenter.requestLogin(MyApplication.getContext(), userName, password);
                }
                break;
            case R.id.register_btn:
                String userName1 = mUserName.getText().toString();
                String password1 = mPassword.getText().toString();
                String repassword1 = mRePassword.getText().toString();
                if (TextUtils.isEmpty(userName1) || TextUtils.isEmpty(password1) || TextUtils.isEmpty(repassword1)) {
                    showToast("用户名和密码不能为空");
                } else if (!password1.equals(repassword1)) {
                    showToast("两次密码不匹配");
                } else {
                    mLoginPresenter.requestRegister(MyApplication.getContext(), userName1, password1, repassword1);
                }
                break;
            case R.id.register_text:
                mLoginBtn.setVisibility(View.GONE);
                mRegisterBtn.setVisibility(View.VISIBLE);
                mRePassword.setVisibility(View.VISIBLE);
                mLoginBack.setVisibility(View.VISIBLE);
                mRegisterText.setVisibility(View.GONE);
                mForgetPasswordText.setVisibility(View.GONE);
                break;
            case R.id.forget_password_text:
                showToast("功能敬请期待");
                break;
            case R.id.login_back:
                mLoginBtn.setVisibility(View.VISIBLE);
                mRegisterBtn.setVisibility(View.GONE);
                mRePassword.setVisibility(View.GONE);
                mLoginBack.setVisibility(View.GONE);
                mRegisterText.setVisibility(View.VISIBLE);
                mForgetPasswordText.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void loginSuccess(String userName) {
        EventBus.getDefault().post(new LoginEvent(true, userName));
        showToast("登录成功");
        finish();
    }

    @Override
    public void loginFail(String errorMsg) {
        AppLog.debug(TAG, "loginFail: errorMsg = " + errorMsg);
        showToast(errorMsg);
    }

    @Override
    public void registerSuccess(LoginBean loginBean) {
        AppLog.debug(TAG, "registerSuccess: username = " + loginBean.getUsername() + " password = " + loginBean.getPassword());
        mLoginBtn.setVisibility(View.VISIBLE);
        mRegisterBtn.setVisibility(View.GONE);
        mRePassword.setVisibility(View.GONE);
        mLoginBack.setVisibility(View.GONE);
        mRegisterText.setVisibility(View.VISIBLE);
        mForgetPasswordText.setVisibility(View.VISIBLE);
        showToast("注册成功");
    }

    @Override
    public void registerFail(String errorMsg) {
        AppLog.debug(TAG, "registerFail: errorMsg = " + errorMsg);
        showToast(errorMsg);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

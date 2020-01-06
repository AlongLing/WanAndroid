package com.along.wanandroid.basemvp.impl;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.along.wanandroid.basemvp.InjectPresenter;
import com.along.wanandroid.basemvp.interfaces.IBaseView;
import com.along.wanandroid.database.AppDatabase;
import com.along.wanandroid.database.UserDao;
import com.along.wanandroid.utils.MyApplication;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    /**
     * 保存使用注解的 Presenter ，用于解绑
     */
    private List<BasePresenter> mInjectPresenters;

    /**
     * 获取当前Activity xml的id
     *
     * @return
     */
    protected abstract int getXMLId();

    /***
     * 当前Activity初始化地方
     */
    protected abstract void init(@Nullable Bundle savedInstanceState);

    /**
     * 有些Activty不适用EventBus
     * @return
     */
    protected abstract boolean useEventBus();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getXMLId());
        ButterKnife.bind(this);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }

        mInjectPresenters = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();   // 解释1。
        for (Field field : fields) {
            //获取变量上面的注解类型
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if (injectPresenter != null) {
                try {
                    Class<? extends BasePresenter> type = (Class<? extends BasePresenter>) field.getType();
                    BasePresenter mInjectPresenter = type.newInstance();
                    mInjectPresenter.attach(this);                  // P 绑定 V 层。
                    field.setAccessible(true);
                    field.set(this, mInjectPresenter);
                    mInjectPresenters.add(mInjectPresenter);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }catch (ClassCastException e){
                    e.printStackTrace();
                    throw new RuntimeException("SubClass must extends Class:BasePresenter");
                }
            }
        }

        init(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 解绑，避免内存泄漏
         */
        for (BasePresenter presenter : mInjectPresenters) {
            presenter.detach();
        }
        mInjectPresenters.clear();
        mInjectPresenters = null;
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}

package com.along.wanandroid.basemvp.interfaces;

public interface IBasePresenter {

    void attach(IBaseView view);

    void detach();

}

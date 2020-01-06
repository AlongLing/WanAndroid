package com.along.wanandroid.contract;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.interfaces.IBasePresenter;
import com.along.wanandroid.basemvp.interfaces.IBaseView;
import com.along.wanandroid.project.model.bean.ProjectCategory;

import java.util.List;

import io.reactivex.Observable;

public interface ProjectContract {

    interface View extends IBaseView {

        void showTabs(List<ProjectCategory> categoryList);

    }

    interface Model {

        Observable<BaseBean<List<ProjectCategory>>> getProjectCategory(Context context);

    }

    interface Presenter extends IBasePresenter {

        /***
         * 初始化项目分类数据。
         * @param context
         */
        void initData(Context context);

    }

}

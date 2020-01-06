package com.along.wanandroid.contract;

import android.content.Context;

import com.along.wanandroid.basemvp.bean.BaseBean;
import com.along.wanandroid.basemvp.interfaces.IBasePresenter;
import com.along.wanandroid.basemvp.interfaces.IBaseView;
import com.along.wanandroid.home.model.bean.ArticleDetailBean;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.home.model.bean.BannerBean;

import java.util.List;

import io.reactivex.Observable;

public interface HomeContract {

    interface View extends IBaseView {

        void showBanner(List<BannerBean> banners);

        void showArticleList(List<ArticleDetailBean> articles);

        void loadMoreArticles(List<ArticleDetailBean> articles);

    }

    interface Model {

        Observable<BaseBean<List<BannerBean>>> getBannerData(Context context);

        Observable<BaseBean<ArticleList>> getArticlesData(Context context, int pageNo);

        Observable<BaseBean<Object>> collectArticle(Context context, int id);

        Observable<BaseBean<Object>> cancelCollectArticle(Context context, int id);

    }

    interface Presenter extends IBasePresenter {

        /***
         * 初始化首页数据
         * @param context
         */
        void initData(Context context);

        /***
         * 加载更多数据
         * @param context
         */
        void loadMoreArticles(Context context, int page);

        /**
         * 收藏文章
         *
         * @param data
         */
        void collectArticle(Context context, ArticleDetailBean data);

        /***
         * 取消收藏
         * @param data
         */
        void cancelCollectArticle(Context context, ArticleDetailBean data);

    }

}

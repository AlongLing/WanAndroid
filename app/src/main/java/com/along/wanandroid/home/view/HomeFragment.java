package com.along.wanandroid.home.view;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.along.wanandroid.R;
import com.along.wanandroid.basemvp.InjectPresenter;
import com.along.wanandroid.basemvp.impl.BaseFragment;
import com.along.wanandroid.contract.HomeContract;
import com.along.wanandroid.home.adapter.ArticlesAdapter;
import com.along.wanandroid.home.adapter.GlideImageLoader;
import com.along.wanandroid.home.model.bean.ArticleDetailBean;
import com.along.wanandroid.home.model.bean.BannerBean;
import com.along.wanandroid.home.presenter.HomePresenter;
import com.along.wanandroid.login.view.LoginActivity;
import com.along.wanandroid.main.view.ContentActivity;
import com.along.wanandroid.utils.AppLog;
import com.along.wanandroid.utils.MyDatabaseUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.recycleViewContent)
    public RecyclerView mRecyclerView;

    @BindView(R.id.multipleStatusView)
    public MultipleStatusView multipleStatusView;

    @BindView(R.id.swiperefresh_layout)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    private ArticlesAdapter articlesAdapter;

    private List<ArticleDetailBean> articleDetailData;           // 文章列表。

    private Banner bannerView;

    @InjectPresenter
    private HomePresenter mHomePresenter;

    private MyDatabaseUtil mMyDatabaseUtil;

    @Override
    protected int getXMLId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        articleDetailData = new ArrayList<>();
        mHomePresenter.initData(getActivity());
        View bannerLayout = getLayoutInflater().inflate(R.layout.home_banner, null);
        bannerView = bannerLayout.findViewById(R.id.banner);
        bannerView.setImageLoader(new GlideImageLoader());
        mSwipeRefreshLayout.setOnRefreshListener(() -> mHomePresenter.initData(getActivity()));
        articlesAdapter = new ArticlesAdapter(R.layout.home_recyclerview_item, articleDetailData);
        articlesAdapter.setOnItemClickListener(onItemClickListener);
        articlesAdapter.setOnItemChildClickListener(onItemChildClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        articlesAdapter.addHeaderView(bannerLayout);
        articlesAdapter.bindToRecyclerView(mRecyclerView);
        articlesAdapter.setOnLoadMoreListener(() -> mHomePresenter.loadMoreArticles(getActivity(), articleDetailData.size() / 20), mRecyclerView);
        mMyDatabaseUtil = MyDatabaseUtil.getInstance();
    }

    @Override
    public void showBanner(List<BannerBean> banners) {
        AppLog.debug(TAG, "showBanner: listBannerBean = " + banners.toString());
        final List<String> imagePaths = new ArrayList<>();
        final List<String> titles = new ArrayList<>();
        Observable.fromIterable(banners).subscribe(new Consumer<BannerBean>() {
            @Override
            public void accept(BannerBean bannerDataItem) throws Exception {
                imagePaths.add(bannerDataItem.getImagePath());
                titles.add(bannerDataItem.getTitle());
            }
        });

        bannerView.setImages(banners);
        bannerView.setBannerTitles(titles);
        bannerView.setDelayTime(2500);
        bannerView.setOnBannerListener(position -> {
            toContentActivity(banners.get(position).getId(), banners.get(position).getUrl(), banners.get(position).getTitle());
        });
        bannerView.start();
    }

    @Override
    public void showArticleList(List<ArticleDetailBean> articles) {
        AppLog.debug(TAG, "showArticleList");
        multipleStatusView.showContent();
        mSwipeRefreshLayout.setRefreshing(false);
        articlesAdapter.setNewData(articles);
        articleDetailData.clear();
        articleDetailData.addAll(articles);
        if (articles.size() < 20) {
            articlesAdapter.loadMoreEnd(false);
        }
    }

    @Override
    public void loadMoreArticles(List<ArticleDetailBean> articles) {
        articlesAdapter.addData(articles);
        articleDetailData.addAll(articles);
        if (articles.size() < 20) {
            articlesAdapter.loadMoreEnd(false);

        } else {
            articlesAdapter.loadMoreComplete();
        }
    }

    private void toContentActivity(int id, String url, String title) {
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        Objects.requireNonNull(getActivity()).startActivity(intent);
        getActivity().overridePendingTransition(R.anim.tranlate_in, R.anim.tranlate_out);
    }

    private ArticlesAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            ArticleDetailBean article = articleDetailData.get(position);
            if (article != null) {
                toContentActivity(article.getId(), article.getLink(), article.getTitle());
            }
        }
    };

    private ArticlesAdapter.OnItemChildClickListener onItemChildClickListener = new BaseQuickAdapter.OnItemChildClickListener() {
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            switch (view.getId()) {
                case R.id.articleListLikeImg:
                    if (mMyDatabaseUtil.isLogin()) {
                        ArticleDetailBean data = articleDetailData.get(position);
                        boolean isCollect = data.isCollect();
                        data.setCollect(!isCollect);
                        articlesAdapter.setData(position, data);
                        if (!isCollect) {
                            mHomePresenter.collectArticle(getActivity(), data);         // 收藏文章。
                        } else {
                            mHomePresenter.cancelCollectArticle(getActivity(), data);    // 取消收藏文章。
                        }
                    } else {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

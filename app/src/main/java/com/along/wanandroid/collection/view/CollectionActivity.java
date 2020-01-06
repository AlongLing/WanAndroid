package com.along.wanandroid.collection.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.along.wanandroid.R;
import com.along.wanandroid.basemvp.InjectPresenter;
import com.along.wanandroid.basemvp.impl.BaseActivity;
import com.along.wanandroid.collection.presenter.CollectionPresenter;
import com.along.wanandroid.contract.CollectionContract;
import com.along.wanandroid.home.adapter.ArticlesAdapter;
import com.along.wanandroid.home.model.bean.ArticleDetailBean;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.main.view.ContentActivity;
import com.along.wanandroid.utils.AppLog;
import com.along.wanandroid.utils.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class CollectionActivity extends BaseActivity implements CollectionContract.View {

    private static final String TAG = "CollectionActivity";

    @BindView(R.id.collection_msv)
    public MultipleStatusView mCollectionMsv;

    @BindView(R.id.collection_srl)
    public SwipeRefreshLayout mCollectionSrl;

    @BindView(R.id.collection_rv)
    public RecyclerView mCollectionRv;

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    private List<ArticleDetailBean> mCollectionList;

    private ArticlesAdapter mCollectionAdapter;

    @InjectPresenter
    private CollectionPresenter mCollectionPresenter;

    private boolean isCompleteRefresh = true;

    private int currentPage;

    @Override
    protected int getXMLId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mCollectionMsv.showLoading();
        mCollectionList = new ArrayList<>();
        mCollectionAdapter = new ArticlesAdapter(R.layout.home_recyclerview_item, mCollectionList);
        mCollectionAdapter.setOnItemClickListener(onItemClickListener);
        mCollectionAdapter.setOnItemChildClickListener(onItemChildClickListener);
        mCollectionAdapter.setOnLoadMoreListener(requestLoadMoreListener, mCollectionRv);
        mCollectionSrl.setOnRefreshListener(onRefreshListener);
        mCollectionRv.setLayoutManager(new LinearLayoutManager(this));
        mCollectionRv.setItemAnimator(new DefaultItemAnimator());
        mCollectionRv.setAdapter(mCollectionAdapter);
        mCollectionPresenter.getCollections(MyApplication.getContext(), 0);
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @Override
    public void showArticleList(ArticleList articleList) {
        for (ArticleDetailBean articleDetailBean : articleList.getDatas()) {
            articleDetailBean.setCollect(true);
        }
        mCollectionMsv.showContent();
        if (isCompleteRefresh) {
            mCollectionSrl.setRefreshing(false);
            currentPage = articleList.getCurPage();
            mCollectionList.clear();
            mCollectionList.addAll(articleList.getDatas());
            mCollectionAdapter.setNewData(articleList.getDatas());
            if (articleList.getTotal() < articleList.getSize()) {
                mCollectionAdapter.loadMoreEnd(true);
            }
        } else {
            if (!(currentPage == articleList.getCurPage())) {
                currentPage =articleList.getCurPage();
                mCollectionList.addAll(articleList.getDatas());
                mCollectionAdapter.addData(articleList.getDatas());
                mCollectionAdapter.loadMoreComplete();
            } else {
                mCollectionAdapter.loadMoreEnd();
            }
        }
    }

    @Override
    public void deleteArticle(int position) {
        mCollectionList.remove(position);
        mCollectionAdapter.remove(position);
    }

    private BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            ArticleDetailBean articleDetailBean = mCollectionList.get(position);
            toContentActivity(articleDetailBean.getId(), articleDetailBean.getLink(), articleDetailBean.getTitle());
        }
    };

    private void toContentActivity(int id, String url, String title) {
        Intent intent = new Intent(CollectionActivity.this, ContentActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        startActivity(intent);
        overridePendingTransition(R.anim.tranlate_in, R.anim.tranlate_out);
    }

    private BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener = new BaseQuickAdapter.OnItemChildClickListener() {
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            switch (view.getId()) {
                case R.id.articleListLikeImg:
                    try {
                        mCollectionPresenter.cancelCollectArticle(CollectionActivity.this, mCollectionList.get(position), position);
                    } catch (Exception e) {
                        AppLog.debug(TAG, "收藏点击错误信息:" + e.getMessage());
                    }
                    break;
                default:
                    //nothing
                    break;
            }
        }
    };

    private BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener = new BaseQuickAdapter.RequestLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            isCompleteRefresh = false;
            mCollectionPresenter.getCollections(CollectionActivity.this, currentPage);
        }
    };

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            isCompleteRefresh = true;
            mCollectionPresenter.getCollections(CollectionActivity.this, 0);
        }
    };
}

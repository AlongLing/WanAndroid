package com.along.wanandroid.knowledge.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.along.wanandroid.R;
import com.along.wanandroid.basemvp.InjectPresenter;
import com.along.wanandroid.basemvp.impl.BaseFragment;
import com.along.wanandroid.contract.KnowledgeListContract;
import com.along.wanandroid.home.model.bean.ArticleDetailBean;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.knowledge.adapter.KnowledgeListAdapter;
import com.along.wanandroid.knowledge.presenter.KnowledgeListPresenter;
import com.along.wanandroid.main.view.ContentActivity;
import com.along.wanandroid.utils.AppLog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class KnowledgeListFragment extends BaseFragment implements KnowledgeListContract.View {

    private static final String TAG = "KnowledgeListFragment";

    @BindView(R.id.knowledge_list_msv)
    public MultipleStatusView mMultipleStatusView;

    @BindView(R.id.knowledge_list_srl)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.knowledge_list_rv)
    public RecyclerView mRecyclerView;

    @InjectPresenter
    private KnowledgeListPresenter mKnowledgeListPresenter;

    private List<ArticleDetailBean> mArticleDetailBeanList;

    private KnowledgeListAdapter mKnowledgeListAdapter;

    private boolean isCompleteRefresh = true;

    private int currentPage = 0;

    public static KnowledgeListFragment getInstance(int categoryId) {
        KnowledgeListFragment knowledgeListFragment = new KnowledgeListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", categoryId);
        knowledgeListFragment.setArguments(bundle);
        return knowledgeListFragment;
    }


    @Override
    protected int getXMLId() {
        return R.layout.fragment_knowledge_list;
    }

    @Override
    protected void init() {
        AppLog.debug(TAG, "init");
        mArticleDetailBeanList = new ArrayList<>();
        mMultipleStatusView.showLoading();
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mKnowledgeListAdapter = new KnowledgeListAdapter(R.layout.fragment_knowledge_list_item, mArticleDetailBeanList);
        mKnowledgeListAdapter.setOnItemClickListener(onItemClickListener);
        mKnowledgeListAdapter.setOnLoadMoreListener(requestLoadMoreListener, mRecyclerView);
        mRecyclerView.setAdapter(mKnowledgeListAdapter);
        mKnowledgeListPresenter.getKnowledgeArticles(getActivity(), 0, getArguments().getInt("categoryId"));
    }

    @Override
    public void showKnowledgeArticles(ArticleList data) {
        mMultipleStatusView.showContent();
        mSwipeRefreshLayout.setRefreshing(false);
        if (isCompleteRefresh) {
            AppLog.debug(TAG, "showKnowledgeArticles: data size = " + data.getDatas().size());
            mSwipeRefreshLayout.setRefreshing(false);
            mKnowledgeListAdapter.setNewData(data.getDatas());
            mArticleDetailBeanList.clear();
            mArticleDetailBeanList.addAll(data.getDatas());
            if (data.getDatas().size() < 20) {
                mKnowledgeListAdapter.loadMoreEnd(false);
            }
        } else {
            mKnowledgeListAdapter.addData(data.getDatas());
            mArticleDetailBeanList.addAll(data.getDatas());
            currentPage = data.getCurPage();
            if (data.getDatas().size() < 20) {
                mKnowledgeListAdapter.loadMoreEnd(false);
            } else {
                mKnowledgeListAdapter.loadMoreComplete();
            }
        }
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mKnowledgeListPresenter.getKnowledgeArticles(getActivity(), 0, getArguments().getInt("categoryId"));
        }
    };

    private BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            isCompleteRefresh = true;
            ArticleDetailBean article = mArticleDetailBeanList.get(position);
            if (article != null) {
                toContentActivity(article.getId(), article.getLink(), article.getTitle());
            }
        }
    };

    private BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener = new BaseQuickAdapter.RequestLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            isCompleteRefresh = false;
            mKnowledgeListPresenter.getKnowledgeArticles(getActivity(), currentPage + 1, getArguments().getInt("categoryId"));
        }
    };

    private void toContentActivity(int id, String url, String title) {
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        Objects.requireNonNull(getActivity()).startActivity(intent);
        getActivity().overridePendingTransition(R.anim.tranlate_in, R.anim.tranlate_out);
    }
}

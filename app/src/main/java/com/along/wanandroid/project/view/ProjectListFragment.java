package com.along.wanandroid.project.view;

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
import com.along.wanandroid.contract.ProjectListContract;
import com.along.wanandroid.home.model.bean.ArticleDetailBean;
import com.along.wanandroid.home.model.bean.ArticleList;
import com.along.wanandroid.main.view.ContentActivity;
import com.along.wanandroid.project.adapter.ProjectListAdapter;
import com.along.wanandroid.project.presenter.ProjectListPresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class ProjectListFragment extends BaseFragment implements ProjectListContract.View {

    @BindView(R.id.project_list_msv)
    public MultipleStatusView mMultipleStatusView;

    @BindView(R.id.project_list_srl)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.project_list_rv)
    public RecyclerView mRecyclerView;

    private ProjectListAdapter mProjectListAdapter;

    private List<ArticleDetailBean> mArticleDetailBeanList;

    @InjectPresenter
    private ProjectListPresenter mProjectListPresenter;

    private boolean isCompleteRefresh = true;

    private int mCurrentPage = 1;

    public static ProjectListFragment getInstance(int categoryId) {
        ProjectListFragment projectListFragment = new ProjectListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", categoryId);
        projectListFragment.setArguments(bundle);
        return projectListFragment;
    }


    @Override
    protected int getXMLId() {
        return R.layout.fragment_project_list;
    }

    @Override
    protected void init() {
        mArticleDetailBeanList = new ArrayList<>();
        mMultipleStatusView.showLoading();
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProjectListAdapter = new ProjectListAdapter(R.layout.fragment_project_list_item, mArticleDetailBeanList);
        mProjectListAdapter.setOnItemClickListener(onItemClickListener);
        mProjectListAdapter.setOnLoadMoreListener(requestLoadMoreListener, mRecyclerView);
        mRecyclerView.setAdapter(mProjectListAdapter);
        mProjectListPresenter.getProjectArticles(getActivity(), 1, getArguments().getInt("categoryId"));
    }

    @Override
    public void showArticleList(ArticleList articleList) {
        mMultipleStatusView.showContent();
        mSwipeRefreshLayout.setRefreshing(false);
        mCurrentPage = articleList.getCurPage();
        if (isCompleteRefresh) {
            mProjectListAdapter.setNewData(articleList.getDatas());
            mArticleDetailBeanList.clear();
            mArticleDetailBeanList.addAll(articleList.getDatas());
            if (articleList.getDatas().size() < 20) {
                mProjectListAdapter.loadMoreEnd(false);
            }
        } else {
            mProjectListAdapter.addData(articleList.getDatas());
            mArticleDetailBeanList.addAll(articleList.getDatas());
            if (articleList.getDatas().size() < 20) {
                mProjectListAdapter.loadMoreEnd(false);
            } else {
                mProjectListAdapter.loadMoreComplete();
            }
        }
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            isCompleteRefresh = true;
            mProjectListPresenter.getProjectArticles(getActivity(), 1, getArguments().getInt("categoryId"));
        }
    };

    private BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            ArticleDetailBean articleDetailData = mArticleDetailBeanList.get(position);
            toContentActivity(articleDetailData.getId(), articleDetailData.getLink(), articleDetailData.getTitle());
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

    private BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener = new BaseQuickAdapter.RequestLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            isCompleteRefresh = false;
            mProjectListPresenter.getProjectArticles(getActivity(), mCurrentPage + 1, getArguments().getInt("categoryId"));
        }
    };
}

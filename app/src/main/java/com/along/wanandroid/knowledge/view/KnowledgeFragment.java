package com.along.wanandroid.knowledge.view;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.along.wanandroid.R;
import com.along.wanandroid.basemvp.InjectPresenter;
import com.along.wanandroid.basemvp.impl.BaseFragment;
import com.along.wanandroid.contract.KnowledgeContract;
import com.along.wanandroid.knowledge.adapter.KnowledgeAdapter;
import com.along.wanandroid.knowledge.model.bean.KnowledgeBean;
import com.along.wanandroid.knowledge.presenter.KnowledgePresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class KnowledgeFragment extends BaseFragment implements KnowledgeContract.View {

    @BindView(R.id.knowledge_msv)
    public MultipleStatusView mMultipleStatusView;

    @BindView(R.id.knowledge_srl)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.knowledge_rv)
    public RecyclerView mRecyclerView;

    @InjectPresenter
    private KnowledgePresenter mKnowledgePresenter;

    private List<KnowledgeBean> mKnowledgeBeanList;

    private KnowledgeAdapter mKnowledgeAdapter;


    @Override
    protected int getXMLId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void init() {
        mMultipleStatusView.showLoading();
        mKnowledgeBeanList = new ArrayList<>();
        mKnowledgePresenter.initData(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mKnowledgeAdapter = new KnowledgeAdapter(R.layout.knowledge_recyclerview_item, mKnowledgeBeanList);
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        mKnowledgeAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(mKnowledgeAdapter);
    }

    @Override
    public void showKnowLedge(List<KnowledgeBean> knowledgeBeans) {
        mMultipleStatusView.showContent();
        mSwipeRefreshLayout.setRefreshing(false);
        mKnowledgeBeanList.clear();
        mKnowledgeBeanList.addAll(knowledgeBeans);
        mKnowledgeAdapter.setNewData(knowledgeBeans);
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mKnowledgePresenter.initData(getActivity());
        }
    };

    protected BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            toContentActivity(mKnowledgeBeanList.get(position));
        }
    };

    private void toContentActivity(KnowledgeBean knowledgeBean) {
        Intent intent = new Intent(getActivity(), KnowledgeDetailActivity.class);
        intent.putExtra("knowledge", knowledgeBean);
        Objects.requireNonNull(getActivity()).startActivity(intent);
        getActivity().overridePendingTransition(R.anim.tranlate_in, R.anim.tranlate_out);
    }

}

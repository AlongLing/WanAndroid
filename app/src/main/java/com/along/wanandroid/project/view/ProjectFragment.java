package com.along.wanandroid.project.view;

import androidx.viewpager.widget.ViewPager;

import com.along.wanandroid.R;
import com.along.wanandroid.basemvp.InjectPresenter;
import com.along.wanandroid.basemvp.impl.BaseFragment;
import com.along.wanandroid.contract.ProjectContract;
import com.along.wanandroid.project.adapter.ProjectPagerAdapter;
import com.along.wanandroid.project.model.bean.ProjectCategory;
import com.along.wanandroid.project.presenter.ProjectPresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends BaseFragment implements ProjectContract.View {

    @BindView(R.id.tab_layout)
    public TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    public ViewPager mViewPager;

    @InjectPresenter
    private ProjectPresenter mProjectPresenter;

    private ProjectPagerAdapter mAdapter;

    @Override
    protected int getXMLId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void init() {
        mProjectPresenter.initData(getActivity());
    }

    @Override
    public void showTabs(List<ProjectCategory> categoryList) {
        mAdapter = new ProjectPagerAdapter(getActivity().getSupportFragmentManager(), categoryList);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}

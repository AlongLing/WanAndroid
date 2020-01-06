package com.along.wanandroid.knowledge.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.along.wanandroid.knowledge.model.bean.KnowledgeBean;
import com.along.wanandroid.knowledge.view.KnowledgeListFragment;

import java.util.ArrayList;
import java.util.List;

public class KnowledgePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    private List<String> mTitleList;

    public KnowledgePagerAdapter(FragmentManager fm, List<KnowledgeBean.Child> children) {
        super(fm);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        for (KnowledgeBean.Child child : children) {
            mTitleList.add(child.getName());
            Fragment fragment = KnowledgeListFragment.getInstance(child.getId());
            mFragmentList.add(fragment);
        }
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}

package com.along.wanandroid.project.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.along.wanandroid.project.model.bean.ProjectCategory;
import com.along.wanandroid.project.view.ProjectListFragment;

import java.util.ArrayList;
import java.util.List;

public class ProjectPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitles;

    private List<Fragment> mFragmentList;

    public ProjectPagerAdapter(FragmentManager fm, List<ProjectCategory> categoryList) {
        super(fm);
        if (mFragmentList != null) {
            mFragmentList.clear();
        }
        mTitles = new ArrayList<>();
        mFragmentList = new ArrayList<>();
        for (ProjectCategory projectCategory : categoryList) {
            mTitles.add(projectCategory.getName());
            // 根据项目的分类 id 创建不同的 fragment，让每一个 ViewPager 的页面都显示各自的 fragment。
            Fragment fragment = ProjectListFragment.getInstance(projectCategory.getId());
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

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    // 给 TabLayout 设置相应的标题。
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}

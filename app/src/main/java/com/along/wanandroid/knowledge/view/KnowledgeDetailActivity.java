package com.along.wanandroid.knowledge.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.along.wanandroid.R;
import com.along.wanandroid.basemvp.impl.BaseActivity;
import com.along.wanandroid.knowledge.adapter.KnowledgePagerAdapter;
import com.along.wanandroid.knowledge.model.bean.KnowledgeBean;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class KnowledgeDetailActivity extends BaseActivity {

    @BindView(R.id.knowledge_detail_tl)
    public TabLayout mTabLayout;

    @BindView(R.id.knowledge_detail_vp)
    public ViewPager mViewPager;

    private KnowledgePagerAdapter mKnowledgePagerAdapter;

    @Override
    protected int getXMLId() {
        return R.layout.activity_knowledge_detail;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        KnowledgeBean knowledgeBean = (KnowledgeBean) getIntent().getSerializableExtra("knowledge");
        if (knowledgeBean != null) {
            mKnowledgePagerAdapter = new KnowledgePagerAdapter(getSupportFragmentManager(), knowledgeBean.getChildren());
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            mViewPager.setAdapter(mKnowledgePagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }
}

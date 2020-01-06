package com.along.wanandroid.knowledge.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.along.wanandroid.R;
import com.along.wanandroid.home.model.bean.ArticleDetailBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class KnowledgeListAdapter extends BaseQuickAdapter<ArticleDetailBean, BaseViewHolder> {


    public KnowledgeListAdapter(int layoutResId, @Nullable List<ArticleDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleDetailBean item) {
        helper.setText(R.id.knowledge_list_project_title, item.getTitle())
                .setText(R.id.knowledge_list_project_desc, item.getDesc())
                .setText(R.id.knowledge_list_project_time, item.getNiceDate())
                .setText(R.id.knowledge_list_author_name, item.getAuthor());

        if (!item.getEnvelopePic().equals("")) {
            ImageView img = helper.getView(R.id.knowledge_list_project_image);
            Glide.with(mContext).load(item.getEnvelopePic()).into(img);
            img.setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.knowledge_list_project_image).setVisibility(View.GONE);
        }
    }
}

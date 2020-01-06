package com.along.wanandroid.project.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.along.wanandroid.R;
import com.along.wanandroid.home.model.bean.ArticleDetailBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.joda.time.DateTime;

import java.util.List;

public class ProjectListAdapter extends BaseQuickAdapter<ArticleDetailBean, BaseViewHolder> {


    public ProjectListAdapter(int layoutResId, @Nullable List<ArticleDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleDetailBean item) {
        DateTime dateTime = new DateTime(item.getPublishTime());
        String publishTime = dateTime.toString("yyyy-MM-dd");
        helper.setText(R.id.projectTitle, item.getTitle())
                .setText(R.id.projectDescription, item.getDesc())
                .setText(R.id.projectTime, publishTime)
                .setText(R.id.projectAuthorName, item.getAuthor());

        if (!item.getEnvelopePic().equals("")) {
            ImageView img = helper.getView(R.id.projectImage);
            Glide.with(mContext).load(item.getEnvelopePic()).into(img);
            img.setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.projectImage).setVisibility(View.GONE);
        }
    }
}

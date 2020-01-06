package com.along.wanandroid.knowledge.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.along.wanandroid.R;
import com.along.wanandroid.knowledge.model.bean.KnowledgeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.reactivex.Observable;

public class KnowledgeAdapter extends BaseQuickAdapter<KnowledgeBean, BaseViewHolder> {


    public KnowledgeAdapter(int layoutResId, @Nullable List<KnowledgeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, KnowledgeBean item) {
        StringBuilder stringBuilder = new StringBuilder();
        Observable.fromIterable(item.getChildren()).subscribe(child -> {
            stringBuilder.append(child.getName());
            stringBuilder.append("  ");
        });
        helper.setText(R.id.knowledge_Title, item.getName())
                .setText(R.id.knowledge_desc, stringBuilder.toString());
    }
}

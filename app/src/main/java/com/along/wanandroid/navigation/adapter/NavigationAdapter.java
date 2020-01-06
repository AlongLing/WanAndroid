package com.along.wanandroid.navigation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.along.wanandroid.R;
import com.along.wanandroid.navigation.model.bean.NavigationBean;
import com.along.wanandroid.navigation.view.NavigationFragment;
import com.along.wanandroid.utils.AppLog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class NavigationAdapter extends BaseQuickAdapter<NavigationBean, BaseViewHolder> {

    private static final String TAG = "NavigationAdapter";

    private NavigationFragment mNavigationFragment;

    public NavigationAdapter(int layoutResId, @Nullable List<NavigationBean> data) {
        super(layoutResId, data);
    }

    public void setNavigationFragmentInstance(NavigationFragment navigationFragment) {
        mNavigationFragment = navigationFragment;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NavigationBean item) {
        helper.setText(R.id.item_navigation_title, item.getName());
        FlexboxLayout flexboxLayout = helper.getView(R.id.item_navigation_fbl);
        for (int i = 0; i < item.getArticles().size(); i++) {
            final NavigationBean.NavigationItem navigationItem = item.getArticles().get(i);
            TextView textView = createFlexItemTextView(flexboxLayout);
            textView.setText(navigationItem.getTitle());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mNavigationFragment != null) {
                        mNavigationFragment.showNavigationDetail(navigationItem);
                    }
                }
            });
            flexboxLayout.addView(textView);
        }
    }

    private TextView createFlexItemTextView(FlexboxLayout flexboxLayout) {
        return (TextView) LayoutInflater.from(flexboxLayout.getContext()).inflate(R.layout.navigation_recyclerview_child_item, flexboxLayout, false);
    }
}

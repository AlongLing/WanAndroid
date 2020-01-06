package com.along.wanandroid.navigation.view;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.along.wanandroid.R;
import com.along.wanandroid.basemvp.InjectPresenter;
import com.along.wanandroid.basemvp.impl.BaseFragment;
import com.along.wanandroid.contract.NavigationContract;
import com.along.wanandroid.main.view.ContentActivity;
import com.along.wanandroid.navigation.adapter.NavigationAdapter;
import com.along.wanandroid.navigation.model.bean.NavigationBean;
import com.along.wanandroid.navigation.presenter.NavigationPresenter;
import com.along.wanandroid.utils.AppLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class NavigationFragment extends BaseFragment implements NavigationContract.View {

    private static final String TAG = "NavigationFragment";

    @BindView(R.id.navigation_rv)
    public RecyclerView mRecyclerView;

    @InjectPresenter
    private NavigationPresenter mNavigationPresenter;

    private List<NavigationBean> mNavigationBeanList;

    private NavigationAdapter mNavigationAdapter;


    @Override
    protected int getXMLId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void init() {
        mNavigationBeanList = new ArrayList<>();
        mNavigationPresenter.requestNavigationData(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNavigationAdapter = new NavigationAdapter(R.layout.navigation_recyclerview_item, mNavigationBeanList);
        mNavigationAdapter.setNavigationFragmentInstance(this);
        mRecyclerView.setAdapter(mNavigationAdapter);
    }

    @Override
    public void showData(List<NavigationBean> data) {
        mNavigationAdapter.setNewData(data);
    }

    public void showNavigationDetail(NavigationBean.NavigationItem navigationItem) {
        // 这里做了一下转换，有些 url 是 http开头会导致 url 失效，需要转换成 https。
        String strUrl = navigationItem.getLink();
        if (strUrl == null) {
        } else {
            if (!strUrl.substring(0, 5).equals("https")) {
                strUrl = "https" + strUrl.substring(4, strUrl.length());
            }
        }
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("id", navigationItem.getId());
        intent.putExtra("url", strUrl);
        intent.putExtra("title", navigationItem.getTitle());
        AppLog.debug(TAG, "title = " + navigationItem.getTitle() +" url = " + strUrl);
        Objects.requireNonNull(getActivity()).startActivity(intent);
        getActivity().overridePendingTransition(R.anim.tranlate_in, R.anim.tranlate_out);
    }
}

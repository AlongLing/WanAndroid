package com.along.wanandroid.main.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.along.wanandroid.R;
import com.along.wanandroid.basemvp.impl.BaseActivity;
import com.along.wanandroid.utils.AppLog;
import com.just.agentweb.AgentWeb;

import java.util.Objects;

import butterknife.BindView;

/**
 * 文章详情页面。
 */
public class ContentActivity extends BaseActivity {

    private static final String TAG = "ContentActivity";

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.article_details)
    public ConstraintLayout mConstraintLayout;

    private AgentWeb mAgentWeb;

    @Override
    protected int getXMLId() {
        return R.layout.activity_content;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle(getIntent().getStringExtra("title"));
        initWebView(getIntent().getStringExtra("url"));
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    private void initWebView(String url) {
        mAgentWeb = AgentWeb.with(this).setAgentWebParent(mConstraintLayout, new ConstraintLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backWebView();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /***
     * WebView浏览后退，若没有后退则关闭浏览界面
     */
    private void backWebView() {
        boolean isBack = mAgentWeb.back();
        AppLog.debug(TAG, "后退判断->" + isBack);
        if (!isBack) {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}

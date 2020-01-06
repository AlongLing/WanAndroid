package com.along.wanandroid.main.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.along.wanandroid.R;
import com.along.wanandroid.basemvp.InjectPresenter;
import com.along.wanandroid.basemvp.impl.BaseActivity;
import com.along.wanandroid.collection.view.CollectionActivity;
import com.along.wanandroid.contract.MainContract;
import com.along.wanandroid.database.User;
import com.along.wanandroid.home.view.HomeFragment;
import com.along.wanandroid.knowledge.view.KnowledgeFragment;
import com.along.wanandroid.login.LoginEvent;
import com.along.wanandroid.login.view.LoginActivity;
import com.along.wanandroid.main.presenter.MainPresenter;
import com.along.wanandroid.navigation.view.NavigationFragment;
import com.along.wanandroid.project.view.ProjectFragment;
import com.along.wanandroid.utils.MyApplication;
import com.along.wanandroid.utils.MyDatabaseUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainContract.View {

    private static final String TAG = "MainActivity";

    @BindView(R.id.bottomnavigationview)
    public BottomNavigationView mBottomNavigationView;

    @BindView(R.id.navigationview)
    public NavigationView mNavigationView;

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.drawerlayout)
    public DrawerLayout mDrawerLayout;

    private Fragment mHomeFragment;

    private Fragment mProjectFragment;

    private Fragment mKnowledgeFragment;

    private Fragment mNavigationFragment;

    private TextView mUserName;              // 菜单栏头布局中的用户名。

    private MenuItem mLoginInOut;            //  菜单栏菜单布局中的登录退出。

    @InjectPresenter
    private MainPresenter mMainPresenter;

    private MyDatabaseUtil mMyDatabaseUtil;     // 数据库的工具类，因为 Room 的增删改查都要求在子线程中处理，所以封了一个类，其他地方可以直接调用该类的方法就好。

    @Override
    protected int getXMLId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mBottomNavigationView.setItemIconTintList(null);             //显示图标的默认颜色
        mNavigationView.setItemIconTintList(null);
        initToolbar();
        initListener();
        initFragment();
        mUserName = mNavigationView.getHeaderView(0).findViewById(R.id.user_name);    // 获取头布局。
        mLoginInOut = mNavigationView.getMenu().findItem(R.id.loginInOut);    // 获取菜单栏。
        initDatabase();
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);         // 显示返回图标。
            actionBar.setHomeAsUpIndicator(R.drawable.ic_nav_menu_black_24dp);
        }
    }

    private void initListener() {
        mBottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED); // 0 是显示默认动画，1 是清除默认动画。
        //底部导航栏点击事件。
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.homgpage:
                    mToolbar.setTitle("首页");
                    mToolbar.setVisibility(View.VISIBLE);
                    switchFragment(mHomeFragment);
                    return true;
                case R.id.projets:
                    mToolbar.setVisibility(View.GONE);
                    switchFragment(mProjectFragment);
                    return true;
                case R.id.knowledge:
                    mToolbar.setTitle("体系");
                    mToolbar.setVisibility(View.VISIBLE);
                    switchFragment(mKnowledgeFragment);
                    return true;
                case R.id.navigation:
                    mToolbar.setTitle("导航");
                    mToolbar.setVisibility(View.VISIBLE);
                    switchFragment(mNavigationFragment);
                    return true;
                default:
                    break;
            }
            return false;
        });

        //滑动菜单栏点击事件。
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.collection:
                        if (!mMyDatabaseUtil.isLogin()) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        } else {
                            startActivity(new Intent(MainActivity.this, CollectionActivity.class));
                        }
                        return true;
                    case R.id.night:
                        return true;
                    case R.id.setting:
                        return true;
                    case R.id.aboutUs:
                        return true;
                    case R.id.loginInOut:
                        if (!mMyDatabaseUtil.isLogin()) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        } else {
                            logout();
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initFragment() {
        mHomeFragment = new HomeFragment();
        mProjectFragment = new ProjectFragment();
        mKnowledgeFragment = new KnowledgeFragment();
        mNavigationFragment = new NavigationFragment();
        switchFragment(mHomeFragment);
    }

    // 这里检查数据库中是否有用户数据，有就说明用户已经登录。使用 handler 是在子线程中获取用户数据后方便更新ui。
    private void initDatabase() {
        mMyDatabaseUtil = MyDatabaseUtil.getInstance();
        if (mMyDatabaseUtil.isLogin()) {
            User user = mMyDatabaseUtil.findUser();
            Message message = Message.obtain();
            message.what = 1;
            message.obj = user.getName();
            handler.sendMessage(message);
        }
    }

    // 这里我主要是想用一下 Handler。
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mUserName.setText((String) msg.obj);
                mLoginInOut.setTitle("退出");
            }
        }
    };

    /***
     * 切换fragment方法
     * @param f 切换Fragment
     */
    public void switchFragment(Fragment f) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, f);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    mDrawerLayout.openDrawer(GravityCompat.START);     // 点击 toolbar 上的返回按钮显示滑动菜单栏。
                    break;
                default:
                    break;
            }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent loginEvent) {
        if (loginEvent.isLoginSuccess()) {
            mLoginInOut.setTitle("退出");
            mUserName.setText(loginEvent.getUserName());
            if (mMyDatabaseUtil.findUser() != null) {
                mMyDatabaseUtil.deleteUser();
            }
            User user = new User();
            user.setName(loginEvent.getUserName());
            mMyDatabaseUtil.insertUser(user);
        }
    }

    // 退出登录成功。
    @Override
    public void logoutSuccess() {
        mLoginInOut.setTitle("登录");
        mUserName.setText("user name");
        // 清除数据库中的用户数据。
        mMyDatabaseUtil.deleteUser();
    }

    private void logout() {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("确定要退出吗？")
                .setPositiveButton("确认", (dialog, which) -> {
                    mMainPresenter.logout(MyApplication.getContext());
                })
                .setNegativeButton("取消", (dialog, which) -> {

                }).create().show();
    }
}

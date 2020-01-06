package com.along.wanandroid.utils;

import com.along.wanandroid.database.AppDatabase;
import com.along.wanandroid.database.User;
import com.along.wanandroid.database.UserDao;

public class MyDatabaseUtil {

    private static volatile MyDatabaseUtil sInstance;

    private boolean isLogin;

    private UserDao mUserDao;

    private User mUser = null;

    public static MyDatabaseUtil getInstance() {
        if (sInstance == null) {
            synchronized (MyDatabaseUtil.class) {
                if (sInstance == null) {
                    sInstance = new MyDatabaseUtil();
                }
            }
        }
        return sInstance;
    }

    private MyDatabaseUtil() {
        AppDatabase appDatabase = AppDatabase.getInstance(MyApplication.getContext());
        mUserDao = appDatabase.userDao();
    }

    // 判断用户是否登录。
    public boolean isLogin() {
        Thread thread = new Thread(() -> {
            User user = mUserDao.findUser();
            if (user == null) {
                isLogin = false;
            } else {
                isLogin = true;
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isLogin;
    }

    public void deleteUser() {
        new Thread(() -> mUserDao.deleteUser()).start();
    }

    public void insertUser(User user) {
        new Thread(() -> mUserDao.insertUser(user)).start();
    }

    public User findUser() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mUser = mUserDao.findUser();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mUser;
    }

}

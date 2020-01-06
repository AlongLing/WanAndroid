package com.along.wanandroid.http;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class HttpHelper {

    private static volatile HttpHelper sInstance;

    private Context mContext;

    private OkHttpClient mOkHttpClient;

    private HttpHelper(Context context) {
        mContext = context;
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS).build();
    }

    public RetrofitClient getRetrofitClient() {

        return new RetrofitClient(mOkHttpClient);
    }

    public static HttpHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (HttpHelper.class) {
                if (sInstance == null) {
                    sInstance = new HttpHelper(context);
                }
            }
        }
        return sInstance;
    }

}

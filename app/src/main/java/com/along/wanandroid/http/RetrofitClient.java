package com.along.wanandroid.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private OkHttpClient okHttpClient;

    private static Retrofit retrofit;

    public RetrofitClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    /**
     * 创建 Retrofit的builder
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T builder(Class<T> clazz) {
        if (clazz == null) {
            throw new RuntimeException("Retrofit Client clazz is null");
        }
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://www.wanandroid.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return (T) retrofit.create(clazz);
    }
}

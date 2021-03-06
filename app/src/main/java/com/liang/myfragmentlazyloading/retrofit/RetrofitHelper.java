package com.liang.myfragmentlazyloading.retrofit;

import android.util.Log;

import com.liang.myfragmentlazyloading.app.MyApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建日期：2018/12/24 on 11:14
 * 描述: Retrofit网络请求框架
 * 作者: liangyang
 */
public class RetrofitHelper {

    private volatile static RetrofitHelper sInstance;
    private Retrofit mRetrofit;
    private MyService mMyService;

    private RetrofitHelper(String url) {

        //cache url
        File httpCacheDirectory = new File(MyApplication.mContext.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("okhttp", "message=" + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);
        client.cache(cache).build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client.build())
                .build();
        mMyService = mRetrofit.create(MyService.class);
    }

    public static RetrofitHelper getInstance(String url) {
        if (sInstance == null) {
            synchronized (RetrofitHelper.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitHelper(url);
                }
            }
        }
        return sInstance;
    }

    /**
     * Android Retrofit 在开发中进行更改多个BaseUrl
     */
    public static RetrofitHelper getInstanceChangeBaseUrl(String newApiBaseUrl) {
        return new RetrofitHelper(newApiBaseUrl);
    }

    public MyService getMyService() {
        return mMyService;
    }

    public static RetrofitHelper deleteInstance() {
        if (sInstance != null) {
            sInstance = null;
        }
        return sInstance;
    }
}

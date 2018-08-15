package com.example.framwork;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.util.SparseLongArray;

import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by lenovo on 2017/1/17.
 */

public class BaseApplictaion extends Application {
    private static Application _instance;
    // 用于存放倒计时时间
    public static SparseLongArray timeMap;
    private final int TIME_OUT = 20 * 1000;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        initNohttp();
        //分包
        MultiDex.install(this);
        JodaTimeAndroid.init(this);

    }

    public static Application getInstance() {
        return _instance;
    }


    //初始化nohttp
    private void initNohttp() {
        InitializationConfig config = InitializationConfig.newBuilder(this)
                // 全局连接服务器超时时间，单位毫秒，默认10s。
                .connectionTimeout(TIME_OUT)
                // 全局等待服务器响应超时时间，单位毫秒，默认10s。
                .readTimeout(TIME_OUT)
                .networkExecutor(new OkHttpNetworkExecutor())
                .build();
        NoHttp.initialize(config);
    }
}

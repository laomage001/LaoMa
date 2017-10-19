package com.example.thirdparty;

import android.app.Application;

/**
 * @author Dash
 * @date 2017/10/18
 * @description:
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化
        ImageLoaderUtil.init(this);
    }
}
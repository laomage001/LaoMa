package com.example.mrefresh;

import android.app.Application;


/**
 * @author Dash
 * @date 2017/10/10
 * @description:
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //在应用创建的时候,,,对imageLoader进行全局配置
        ImageLoaderUtil.init(this);//写一个工具类对imageLoader进行初始化...应用也可以作为上下文
    }
}
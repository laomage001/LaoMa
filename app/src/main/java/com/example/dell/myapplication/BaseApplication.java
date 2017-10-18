package com.example.dell.myapplication;

import android.app.Application;



public class BaseApplication extends Application {
    public void onCreate() {
        super.onCreate();


        ImageLoaderUtil.init(this);
    }
}

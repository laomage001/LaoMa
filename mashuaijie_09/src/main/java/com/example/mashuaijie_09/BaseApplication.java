package com.example.mashuaijie_09;

import android.app.Application;


public class BaseApplication extends Application {
    public void onCreate() {
        super.onCreate();


        ImageLoaderUtil.init(this);
    }
}

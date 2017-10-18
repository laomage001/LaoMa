package com.example.mashuaijie11;

import android.app.Application;

/**
 * Created by dell on 2017/10/12.
 */

public class Appuri extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLodelUtil.info(this);
    }
}

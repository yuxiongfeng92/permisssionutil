package com.yxf.permissionutils;

import android.app.Application;

/**
 * Created by yuxiongfeng.
 * Date: 2019/5/16
 */
public class App extends Application {
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static App getInstance(){
        return instance;
    }
}

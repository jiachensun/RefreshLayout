package com.sjc.mytestrefresh;

import android.app.Application;
import android.os.Handler;


/**
 * @Author sjc
 * @Date 2020/7/23
 * Description：
 */
public class MyApp extends Application {
    private static MyApp instance = null;
    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mHandler = new Handler();
    }

    public static MyApp getInstance() {
        return instance;
    }

    public Handler getHandler() {
        return mHandler;
    }
}

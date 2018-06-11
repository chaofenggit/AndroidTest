package com.altopay.cropimage;

import android.app.Application;

import com.altopay.cropimage.crashhandle.CrashHandler;

/**
 * @author zhaohe@iapppay.com
 * @date 2018/6/1.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}

package com.xiaoniu.nestedscrolldemo;

import android.app.Application;

/**
 * @author xiaoniu
 * @date 2018/9/14.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UMengUtil.initUMeng(this);
    }
}

package com.xiaoniu.reader;

import android.app.Application;

/**
 * @author xiaoniu
 * @date 2018/7/24.
 */

public class ReaderApplication extends Application {

    private static ReaderApplication instance;
    public ReaderApplication() {
    }

    public static ReaderApplication getInstance(){
        if (instance == null){
            synchronized (ReaderApplication.class){
                if (instance == null){
                    instance = new ReaderApplication();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

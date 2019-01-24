package com.xiaoniu.fragmentactivityresult.util;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;

/**
 * @author xiaoniu
 * @date 2019/1/24.
 */

public class ActivityResultHelper {

    private static final String FRAGMENT_TAG = "com.xiaoniu.fragmentactivityresult.fragment";

    /**
     * 调起activity
     * @param activity
     * @param intent
     * @param listener
     */
    public static void onActivityResult(Activity activity, Intent intent, ActivityResultListener listener){
        if (activity == null || intent == null || listener == null){
            throw new NullPointerException("activity == null || intent == null || listener == null");
        }

        FragmentManager fm = activity.getFragmentManager();
        ActivityResultFragment currentFragment = (ActivityResultFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (currentFragment == null){
            currentFragment = ActivityResultFragment.newInstance();
            fm.beginTransaction().add(currentFragment, FRAGMENT_TAG).commitAllowingStateLoss();
            fm.executePendingTransactions();//立刻执行
        }
        currentFragment.startActivityResult(intent, listener);
    }
}

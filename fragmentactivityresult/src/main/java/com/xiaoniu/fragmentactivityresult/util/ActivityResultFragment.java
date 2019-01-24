package com.xiaoniu.fragmentactivityresult.util;

import android.app.Fragment;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

/**
 *处理activity回调的fragment
 */
public class ActivityResultFragment extends Fragment {

    private static final String TAG = "ActivityResultFragment";

    private static int requestCode = 0;
    private Map<Integer, ActivityResultListener> activityResultListenerMap = new HashMap<>();

    public ActivityResultFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     */
    public static ActivityResultFragment newInstance() {
        ActivityResultFragment fragment = new ActivityResultFragment();
        return fragment;
    }

    /**
     * startActivityResult
     * @param intent
     * @param listener
     */
    public void startActivityResult(Intent intent, ActivityResultListener listener){
        requestCode ++;
        startActivityForResult(intent, requestCode);
        activityResultListenerMap.put(requestCode, listener);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (activityResultListenerMap.containsKey(requestCode)){
            ActivityResultListener activityResultListener = activityResultListenerMap.get(requestCode);
            if (activityResultListener != null){
                activityResultListener.onActivityResult(requestCode, resultCode, data);
                activityResultListenerMap.remove(requestCode);
            }
        }
    }

}

package com.xiaoniu.fragmentactivityresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xiaoniu.fragmentactivityresult.util.ActivityResultListener;
import com.xiaoniu.fragmentactivityresult.util.ActivityResultHelper;

/**
 * 测试不用activity处理onActivityResult的情况
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * activity处理
     * @param view
     */
    public void activityDeal(View view){
        Intent intent = new Intent(MainActivity.this, NextActivity.class);
        startActivityForResult(intent, 100);
    }

    /**
     * activity处理
     * @param view
     */
    public void fragmentDeal(View view){

        Intent intent = new Intent(MainActivity.this, NextActivity.class);
        ActivityResultHelper.onActivityResult(MainActivity.this, intent, new ActivityResultListener() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                Log.d(TAG, "fragment处理onActivityResult: requestCode = " + requestCode + " ,resultCode = " + resultCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "activity处理onActivityResult: requestCode = " + requestCode + " ,resultCode = " + resultCode);
    }
}

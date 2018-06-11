package com.altopay.cropimage.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.altopay.cropimage.R;
import com.altopay.cropimage.cropone.CaptureActivity;
import com.altopay.lib.utils.logutil.LogUtil;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_info = findViewById(R.id.tv_info);
        tv_info.setGravity(Gravity.NO_GRAVITY);
//        tv_info.setText(Html.fromHtml(getString(R.string.IdDialog_B0)));
        LogUtil.d(TAG, "onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, "onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy");
    }

    /**
     * 拍照片
     * @param view
     */
    public void onTakePhoto(View view){
        startActivity(new Intent(MainActivity.this, CaptureActivity.class));
    }
}

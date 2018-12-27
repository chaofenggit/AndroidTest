package com.xiaoniu.statusbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setStatusBar(this);
    }

    public void onNext(View view){
        Intent intent = new Intent(MainActivity.this, NextActivity.class);
        startActivity(intent);
    }
}

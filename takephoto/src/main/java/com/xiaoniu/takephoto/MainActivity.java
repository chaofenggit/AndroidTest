package com.xiaoniu.takephoto;

import android.app.Activity;
import android.os.Bundle;

/**
 * 1、拍照
 * 2、从相册选择图片
 * 3、截取图片
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

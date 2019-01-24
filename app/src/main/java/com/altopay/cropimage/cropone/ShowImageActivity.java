package com.altopay.cropimage.cropone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.altopay.cropimage.R;
import com.altopay.lib.utils.LogUtil;

public class ShowImageActivity extends Activity {
    private static final String TAG = "ShowImageActivity";

    private ImageView iv_show;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        iv_show = findViewById(R.id.iv_show);
        Intent intent = getIntent();
        if (intent != null){
            filePath = intent.getStringExtra("filePath");
        }

        Bitmap bitmap = null;
      if(!TextUtils.isEmpty(filePath)){
            LogUtil.d(TAG, "解析图片地址：" + filePath);
            bitmap = BitmapFactory.decodeFile(filePath);
        }
        if (bitmap != null) {
            iv_show.setImageBitmap(bitmap);
        }
    }


    public void reTakePhoto(View view){
        finish();
    }

    public void onSure(View view){
        finish();
    }

}

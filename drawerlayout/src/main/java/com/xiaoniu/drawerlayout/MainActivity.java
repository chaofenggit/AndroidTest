package com.xiaoniu.drawerlayout;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DrawerLayout drawerLayout;
    View content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTransparent(this);

        drawerLayout = findViewById(R.id.dl_main);
        content = drawerLayout.getChildAt(0);
        findViewById(R.id.iv_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
                Log.d(TAG, "打开抽屉");
            }
        });

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
//                Log.d(TAG, "V = " + v);
                content.setTranslationX(view.getWidth()*v);
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }
}

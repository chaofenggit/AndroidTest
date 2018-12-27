package com.xiaoniu.textswitcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Random;

public class MainActivity extends Activity implements ViewSwitcher.ViewFactory {

    private TextSwitcher textSwitcher;

    private  CustomTextSwitcher customTextSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSwitcher = findViewById(R.id.ts_one);
        textSwitcher.setInAnimation(this, R.anim.slide_in_bottom);
        textSwitcher.setOutAnimation(this, R.anim.slide_out_top);
        textSwitcher.setFactory(this);
        textSwitcher.setText("初始化");

        customTextSwitcher = findViewById(R.id.ts_two);
        customTextSwitcher.post(new Runnable() {
            @Override
            public void run() {
                customTextSwitcher.setText("初始化");
            }
        });
    }

    public void switchText(View view){
        int nextInt = new Random().nextInt(100);
        textSwitcher.setText("设置数字：" + nextInt);

        customTextSwitcher.setText("设置数字：" + nextInt);
    }

    @Override
    public View makeView() {
        TextView textView = new TextView(this);
        textView.setTextSize(30);
        textView.setTextColor(getResources().getColor(R.color.colorBlack));
        return textView;
    }
}

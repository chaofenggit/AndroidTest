package com.xiaoniu.viewswitch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class NextActivity extends Activity {
    private static final String TAG = "NextActivity";
    private HorizontalScrollView scroll;
    private LinearLayout ll_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        scroll = findViewById(R.id.scroll);
        ll_container = findViewById(R.id.ll_container);
    }

    public void switchOne(View view){
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_one, ll_container, false);
        ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
        layoutParams.width = scroll.getWidth();
        inflate.setLayoutParams(layoutParams);

        ll_container.addView(inflate);
        if (ll_container.getChildCount() > 1){
//            scroll.smoothScrollTo(-ll_container.getChildAt(1).getLeft(),0);
            scroll.fullScroll(View.FOCUS_RIGHT);
        }
    }

    public void switchTwo(View view){
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_two, ll_container, false);
        ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
        layoutParams.width = scroll.getWidth();
        inflate.setLayoutParams(layoutParams);

        ll_container.addView(inflate);
        if (ll_container.getChildCount() > 1){
//            scroll.smoothScrollTo(-ll_container.getChildAt(1).getLeft(),0);
            scroll.fullScroll(View.FOCUS_RIGHT);
        }
    }
}

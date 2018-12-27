package com.xiaoniu.viewswitch;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    private LinearLayout ll_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_container = findViewById(R.id.ll_container);
    }

    public void switchOne(View view){
        final View viewOne = LayoutInflater.from(this).inflate(R.layout.layout_one, ll_container, false);
        ll_container.addView(viewOne);
        if (ll_container.getChildCount() > 1){
            final View childAt = ll_container.getChildAt(0);
            LayoutAnimationUtil.exitToLeftRight(childAt.getWidth(),childAt, new LayoutAnimationUtil.AnimationListener() {
                @Override
                public void onFinish() {
                    ll_container.removeViewAt(0);
                }
            });
        }
    }

    public void switchTwo(View view){
        final View viewTwo = LayoutInflater.from(this).inflate(R.layout.layout_two, ll_container, false);
        ll_container.addView(viewTwo);
        if (ll_container.getChildCount() > 1){
            final View childAt = ll_container.getChildAt(0);
            LayoutAnimationUtil.exitToLeftRight(childAt.getWidth(),childAt, new LayoutAnimationUtil.AnimationListener() {
                @Override
                public void onFinish() {
                    ll_container.removeViewAt(0);
                }
            });
        }
    }
}

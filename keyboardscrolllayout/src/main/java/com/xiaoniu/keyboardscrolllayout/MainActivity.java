package com.xiaoniu.keyboardscrolllayout;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ScrollView;

/**
 * 测试键盘弹起时，
 */
public class MainActivity extends Activity {

    private Button btn_sure;
    private ScrollView test_srcoll;

    private int btnLocationY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        btn_sure = findViewById(R.id.btn_sure);
        test_srcoll = findViewById(R.id.test_srcoll);
        btn_sure.post(new Runnable() {
            @Override
            public void run() {
                int[] location = new int[2];
                btn_sure.getLocationInWindow(location);
                btnLocationY = location[1];
            }
        });
        initSoftKeyboard();
    }

    private void initSoftKeyboard(){

        final ViewTreeObserver viewTreeObserver = btn_sure.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                removeViewTreeObserver(viewTreeObserver, this);
                Rect rect = new Rect();
                btn_sure.getWindowVisibleDisplayFrame(rect);
                int heightDifference = btn_sure.getRootView().getHeight() - rect.bottom;
                if (heightDifference > 200){
                    if (rect.bottom < btnLocationY){
                        //btnLocationY button的左上角的Y值
                        //btn_sure.getHeight()  button的高度
                        // 10 多滚动10像素
                        test_srcoll.smoothScrollBy(0, btnLocationY + btn_sure.getHeight() + 10 - rect.bottom);
                    }
                }else {
                    test_srcoll.smoothScrollTo(0,0);
                }
            }
        });

    }

    private void removeViewTreeObserver(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (observer.isAlive()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                observer.removeOnGlobalLayoutListener(listener);
            } else {
                observer.removeGlobalOnLayoutListener(listener);
            }
        }
    }

}

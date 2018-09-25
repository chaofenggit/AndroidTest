package com.xiaoniu.hook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MainActivity extends Activity {

    private Button btn_hook;
    private TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_hook = findViewById(R.id.btn_hook);
        btn_hook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_info.append("original on Click \n");
            }
        });
        tv_info = findViewById(R.id.tv_info);

        hookListener(btn_hook);
    }

    /**
     * hook view 的
     * @param view
     */
    private void hookListener(View view) {
        try {

            //执行View#getListenerInfo方法
            Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
            getListenerInfo.setAccessible(true);
            Object listenerInfo = getListenerInfo.invoke(view);

            //获取ListenerInfo#mOnClickListener字段
            Class<?> info = Class.forName("android.view.View$ListenerInfo");
            Field mOnClickListener = info.getDeclaredField("mOnClickListener");
            mOnClickListener.setAccessible(true);
            View.OnClickListener oriListener = (View.OnClickListener) mOnClickListener.get(listenerInfo);

            //替换新的OnClickListener
            ClickLis clickLis = new ClickLis(oriListener);
            mOnClickListener.set(listenerInfo, clickLis);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public class ClickLis implements View.OnClickListener{
        private View.OnClickListener oriListener = null;

        public ClickLis(View.OnClickListener listener) {
            this.oriListener = listener;
        }

        @Override
        public void onClick(View v) {
            tv_info.setText("before hooked \n");
            if (oriListener != null){
                oriListener.onClick(v);
            }
            tv_info.append("after hooked");

        }
    }

}

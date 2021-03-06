package com.altopay.lib.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */

public class ToastUtil {

    private static Toast toast;

    public static void showToastShort(Context context, String text) {
        if (context == null || TextUtils.isEmpty(text)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            //如果toast不为空，则直接更改当前toast的文本
            toast.setText(text);
        }
        toast.show();
    }
    public static void showTimeToast(Context context,String text,long time){
        if (context == null || TextUtils.isEmpty(text)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG);
        } else {
            //如果toast不为空，则直接更改当前toast的文本
            toast.setText(text);
            showToast(toast,time);
        }
        toast.show();
    }

    public static void showToast(final Toast toast, long cnt) {
        final Timer m =new Timer();
        m.schedule(new TimerTask() {
            @Override public void run() {
                toast.show(); }
        },0,cnt);
        new Timer().schedule(new TimerTask() {
            @Override public void run() {
                toast.cancel();
                m.cancel();
            } }, cnt );
    }

    public static void showToastLong(Context context, String text) {
        if (context == null || TextUtils.isEmpty(text)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG);
        } else {
            //如果toast不为空，则直接更改当前toast的文本
            toast.setText(text);
        }
        toast.show();
    }

//    public static void showToastAtCenterShort(Context context, String text) {
//        if (context == null || TextUtils.isEmpty(text)) {
//            return;
//        }
//        if (toast == null) {
//            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
//        } else {
//            //如果toast不为空，则直接更改当前toast的文本
//            toast.setText(text);
//        }
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
//    }

//    public static void showToastAtCenterLong(Context context, String text) {
//        if (context == null || TextUtils.isEmpty(text)) {
//            return;
//        }
//        if (toast == null) {
//            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG);
//        } else {
//            //如果toast不为空，则直接更改当前toast的文本
//            toast.setText(text);
//        }
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
//    }

    public static void onDestroy() {
        toast = null;
    }
}

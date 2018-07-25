package com.xiaoniu.reader.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author xiaoniu
 * @date 2018/7/25.
 *
 * 吐司工具类
 */

public class ToastUtil {

    private static Toast toast = null;

    public static void showToast(Context context, String msg){
        if (context == null){
            return;
        }
        if (toast == null){
            toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }
}

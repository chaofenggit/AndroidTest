package com.altopay.lib.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.altopay.lib.utils.logutil.LogUtil;


/**
 * Created by Lcw on 2016/12/6.
 */

public class AltoPayLoadingDialog extends ProgressDialog {

    private static ProgressDialog progressDialog;
    private String mMessage;

    /**
     * loading开关
     */
    public static void showDialog(Context context, String message) {
        Activity activity = ((Activity) context);
        progressDialog = new AltoPayLoadingDialog(activity, R.style.custom_dialog);
        progressDialog.setMessage(message);
        if (activity != null && !activity.isFinishing()) {
            LogUtil.e("1111", "show");
            progressDialog.show();
        }
    }

    public static void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            LogUtil.e("1111", "dismiss");
            progressDialog.dismiss();
        }
    }

    public static AltoPayLoadingDialog showDialogLoading(Context context, String message) {
        Activity activity = ((Activity) context);
        AltoPayLoadingDialog progressDialog = new AltoPayLoadingDialog(activity, R.style.custom_dialog);
        progressDialog.setMessage(message);
        if (activity != null && !activity.isFinishing()) {
            LogUtil.e("1111", "show");
            progressDialog.show();
        }
        return progressDialog;
    }

    public static void dismissDialog(AltoPayLoadingDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            LogUtil.e("1111", "dismiss");
            dialog.dismiss();
            dialog = null;
        }
    }

    public AltoPayLoadingDialog(Context context, int theme) {
        super(context, theme);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public AltoPayLoadingDialog(Context context) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void setMessage(String msg) {
        mMessage = msg;
    }

    public void setMessage(int msgId) {
        mMessage = getContext().getString(msgId);
    }

    @Override
    public void setMessage(CharSequence msg) {
        mMessage = msg.toString();
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Context context = getContext();
        setContentView(R.layout.altopay_loading_dialog);
        TextView textView = findViewById(R.id.dialog_msg);
        textView.setText(TextUtils.isEmpty(mMessage) ? context.getString(R.string.common_loading) : mMessage);
    }

}

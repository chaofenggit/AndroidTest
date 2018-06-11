package com.altopay.lib.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * Created by kaylau on 2017/2/24.
 */

public class AltoPayCommonDialog extends Dialog {

    public static final int DIALOG_MARGIN_96 = 96;

    public AltoPayCommonDialog(Context context) {
        this(context, R.style.custom_dialog);
    }

    public AltoPayCommonDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {

        private Context mContext;
        private boolean mCancelable;
        private boolean messageIsCenter = false;
        private CharSequence mTitle;
        private CharSequence mMessage;
        private CharSequence mNegativeButtonText;
        private CharSequence mPositiveButtonText;
        private OnClickListener mNegativeButtonClickListener;
        private OnClickListener mPositiveButtonClickListener;

        private TextView mPositiveButton;
        private TextView mNegativeButton;
        private View v_dialog_line_vertical;
        private ScrollView sv_dialog;
        private int color = 0;

        private int margin;

        public Builder(Context context) {
            this.mContext = context;
            this.setCancelable(false);
            this.setMargin(DIALOG_MARGIN_96);
        }

        public Builder setMargin(int margin) {
            this.margin = margin;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public Builder setMessage(CharSequence message) {
            this.mMessage = message;
            return this;
        }
        public Builder setTitle(CharSequence title){
            this.mTitle = title;
            return this;
        }
        public Builder setMessageCenter(boolean messageIsCenter) {
            this.messageIsCenter = messageIsCenter;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.mNegativeButtonText = negativeButtonText;
            this.mNegativeButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.mPositiveButtonText = positiveButtonText;
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButtonColor(int color){
            this.color = color;
            return this;
        }
        public AltoPayCommonDialog show() {
            AltoPayCommonDialog dialog = onCreateDialog();
            if(dialog != null){
                dialog.setCancelable(false);
                dialog.setOwnerActivity((Activity) mContext);
                dialog.setCanceledOnTouchOutside(false);
//        	dialog.getWindow().setLayout(300, 200); //4.0以下的设备需要在 show之前 加上这个才起作用
                Activity mactivity = dialog.getOwnerActivity();
                if(mactivity != null && !mactivity.isFinishing()){
                    dialog.show();
                    return dialog;
                }
            }
            return null;
        }

        private AltoPayCommonDialog onCreateDialog() {
            final AltoPayCommonDialog dialog = new AltoPayCommonDialog(mContext);
            View layout = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_bg_round_rectangle, null);
            dialog.setContentView(layout);

            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();

            int dialogWidth = getDialogWidth((Activity) mContext, margin);
            layoutParams.width = dialogWidth; // 宽度

            sv_dialog = ((ScrollView) layout.findViewById(R.id.sv_dialog));
            sv_dialog.setVerticalScrollBarEnabled(false);

            v_dialog_line_vertical = layout.findViewById(R.id.v_dialog_line_vertical);

            // set the confirm button
            if (mPositiveButtonText != null) {
                mPositiveButton = (TextView) layout.findViewById(R.id.tv_dialog_confirm);
                mPositiveButton.setText(mPositiveButtonText);
                if(color != 0){
                    mPositiveButton.setTextColor(color);
                }
                mPositiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPositiveButtonClickListener != null) {
                            mPositiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                        }
                    }
                });
            } else {
                layout.findViewById(R.id.tv_dialog_confirm).setVisibility(View.GONE);
                v_dialog_line_vertical.setVisibility(View.GONE);
            }

            // set the cancel button
            if (mNegativeButtonText != null) {
                mNegativeButton = (TextView) layout.findViewById(R.id.tv_dialog_cancel);
                mNegativeButton.setText(mNegativeButtonText);
                mNegativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mNegativeButtonClickListener != null) {
                            mNegativeButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_NEGATIVE);
                        }
                    }
                });
            } else {
                layout.findViewById(R.id.tv_dialog_cancel).setVisibility(View.GONE);
                v_dialog_line_vertical.setVisibility(View.GONE);
            }
            TextView textView = ((TextView) layout.findViewById(R.id.tv_dialog_msg));
            TextView tv_title = layout.findViewById(R.id.tv_title);
            if (messageIsCenter) {
                textView.setGravity(Gravity.CENTER);
            }
            if (!TextUtils.isEmpty(mMessage)) {
                textView.setText(mMessage);
            }
            if(!TextUtils.isEmpty(mTitle)){
                tv_title.setText(mTitle);
                tv_title.setVisibility(View.VISIBLE);
            }else{
                tv_title.setVisibility(View.GONE);
            }
            return dialog;
        }

        private int getDialogWidth(Activity activity, int margin) {
            Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(outMetrics);
            int widthPixels = outMetrics.widthPixels;
            int heightPixels = outMetrics.heightPixels;
            int dialogWidth;
            if (activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    || activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE) {
                dialogWidth = widthPixels / 2;
            } else {
                if (1920 == heightPixels) {
                    dialogWidth = widthPixels - margin * 2;
                } else {
                    dialogWidth = widthPixels - margin;
                }
            }
            return dialogWidth;
        }
    }


}
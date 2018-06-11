package com.altopay.lib.utils;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Date: 2018/4/20 下午7:20
 * Author: kay lau
 * Description:
 */
public class LoginPasswordCheck {

    public static final int NEXT_PAGE = 0;
    public static final int LOCK_DURATION = 1001;
    public static final int VERIFICATION_FAILED = 1002;
    public static final int VERIFICATION_FAILED_ABOVE_NORM = 1003;

    private static LoginPasswordCheck instance;


    private LoginPasswordCheck() {
    }

    public synchronized static LoginPasswordCheck getInstance() {
        if (instance == null) {
            instance = new LoginPasswordCheck();
        }
        return instance;
    }

    public void showDialog(Context context, int errorCode, String errorMsg) {
        DialogInterface.OnClickListener positiveListener = null;
        DialogInterface.OnClickListener negativeListener = null;
        DialogData dialogData = new DialogData();
        dialogData.errorMsg = errorMsg;
        switch (errorCode) {
            case NEXT_PAGE:

                dialogData.positiveButtonText = context.getString(R.string.confirm);
                dialogData.negativeButtonText = context.getString(R.string.reset_pwd);

                positiveListener = new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                negativeListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                break;
            case LOCK_DURATION:
//                dialogData.positiveButtonText = "Confirm";
//                dialogData.negativeButtonText = "reset password";
//
//                positiveListener = new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                };
//                negativeListener = new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                };
                break;
            case VERIFICATION_FAILED:
                dialogData.positiveButtonText = context.getString(R.string.try_again);
                dialogData.negativeButtonText = context.getString(R.string.forget_pwd);

                positiveListener = new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                negativeListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                break;
            case VERIFICATION_FAILED_ABOVE_NORM:
                dialogData.positiveButtonText = context.getString(R.string.confirm);
                dialogData.negativeButtonText = context.getString(R.string.reset_pwd);

                positiveListener = new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                negativeListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                break;
        }
        onShowDialog(context, dialogData, positiveListener, negativeListener);
    }

    private void onShowDialog(Context context, DialogData dialogData, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        new AltoPayCommonDialog
                .Builder(context)
                .setMessage(dialogData.errorMsg)
                .setCancelable(false)
                .setPositiveButton(dialogData.positiveButtonText, positiveListener)
                .setNegativeButton(dialogData.negativeButtonText, negativeListener)
                .show();
    }

    class DialogData {
        public String errorMsg;
        public String positiveButtonText;
        public String negativeButtonText;

    }
}

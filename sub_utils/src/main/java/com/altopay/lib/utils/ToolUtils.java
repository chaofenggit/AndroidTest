package com.altopay.lib.utils;

import android.content.Context;
import android.graphics.Paint;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;

public class ToolUtils {

    private static final String TAG = ToolUtils.class.getSimpleName();

    public static String getImei(Context context) {
        String deviceId = "";
        String imei1 = "";
        String imei2 = "";
        TelephonyManager mTelephonyMgr = null;
        try {
            mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei1 = getDoubleImei(mTelephonyMgr, "getDeviceIdGemini", 0);
            imei2 = getDoubleImei(mTelephonyMgr, "getDeviceIdGemini", 1);
        } catch (Exception e) {
            try {
                imei1 = getDoubleImei(mTelephonyMgr, "getDeviceId", 0);
                imei2 = getDoubleImei(mTelephonyMgr, "getDeviceId", 1);
            } catch (Exception ex) {
                LogUtil.i(TAG, "get device id fail" + e.toString());
            }
        }

        if (!TextUtils.isEmpty(imei1) && !TextUtils.isEmpty(imei2)) {
            deviceId = imei1 + "," + imei2;
        } else if (!TextUtils.isEmpty(imei1)) {
            deviceId = imei1;
        } else if (!TextUtils.isEmpty(imei2)) {
            deviceId = imei2;
        } else if (mTelephonyMgr != null) {
            try {
                deviceId = mTelephonyMgr.getDeviceId();
            } catch (Exception e) {
                LogUtil.i(TAG, "mTelephonyMgr.getDeviceId() fail" + e.toString());
            }
        }
        return deviceId;
    }

    /**
     * 获取双卡手机的imei
     */
    private static String getDoubleImei(TelephonyManager telephony, String predictedMethodName, int slotID) throws Exception {
        String inumeric = null;

        Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
        Class<?>[] parameter = new Class[1];
        parameter[0] = int.class;
        Method getSimID = telephonyClass.getMethod(predictedMethodName, parameter);
        Object[] obParameter = new Object[1];
        obParameter[0] = slotID;
        Object ob_phone = getSimID.invoke(telephony, obParameter);
        if (ob_phone != null) {
            inumeric = ob_phone.toString();
        }
        return inumeric;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 获取基线与要显示的位置的Y距离
     * @param paint paint必须要设置文字大小才可以计算
     * @return
     */
    public static float getBaseLineDistance(Paint paint){
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float descent = fontMetrics.descent;
        float ascent = fontMetrics.ascent;
        return (descent - ascent)/2.0f - descent;
    }
}
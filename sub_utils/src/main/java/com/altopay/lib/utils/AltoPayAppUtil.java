package com.altopay.lib.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.altopay.lib.utils.logutil.LogUtil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 全局数据
 *
 * @author zhonghu
 */
public class AltoPayAppUtil {

    public final static String TAG = AltoPayAppUtil.class.getSimpleName();

    private static AltoPayAppUtil instance;

    public static AltoPayAppUtil getInstance() {
        if (instance == null) {
            synchronized (AltoPayAppUtil.class) {
                if (instance == null) {
                    instance = new AltoPayAppUtil();
                }
            }
        }
        return instance;
    }

    private Context context;

    public Context getContext() {
        return context;
    }

    public void initContext(Context mContext) {
        context = mContext.getApplicationContext();
    }


    /***----------*/
    private String mac;                //	mac地址
    private String model;            //	设备型号
    private String tid;
    /**
     * 是否有新消息
     */
    private boolean isHasNewMsg = false;

    public final String getPackageName() {
        if (context != null)
            return context.getPackageName();
        else
            return null;
    }

    public final File getFilesDir() {
        if (context != null)
            return context.getFilesDir();
        else
            return null;
    }


    public final Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (context != null)
            return context.registerReceiver(receiver, filter);
        else
            return null;
    }

    public final Object getSystemService(String name) {
        if (context != null)
            return context.getSystemService(name);
        else
            return null;
    }

    public String getDeviceID() {
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

    public String getMacAddress() {
        if (!TextUtils.isEmpty(mac))
            return mac;
        mac = ToolUtils.getMacAddress();
        return mac;
    }

    public String getIMSI() {
        try {
            TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imsi = mTelephonyMgr.getSubscriberId();
            return imsi;
        } catch (Exception e) {
            return "";
        }
    }

    public String getModel() {

        if (model != null)
            return model;
        else
            return model = Build.MODEL;
    }

    public String getAndroidId() {
        if (context != null) {
            String androidId = android.provider.Settings.System.getString(context.getContentResolver(), TerminalCacheTool.ANDROID_ID);
            return androidId == null ? "" : androidId;
        } else {
            return "";
        }
    }

    public void destroy() {
        instance = null;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public int getVerCode() {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    public String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取设备名称
     *
     * @return
     */

    public String getPhoneName() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String mDecideNamen = bluetoothAdapter.getName();
        if (!TextUtils.isEmpty(mDecideNamen)) {
            return mDecideNamen;
        }
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     *
     * @return
     */
    public String getBuildVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public int getDeviceWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public int getDeviceHeight() {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public String getMeid() {
        String meid = "N/A";
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            Method method = manager.getClass().getMethod("getDeviceId", int.class);

            meid = (String) method.invoke(manager, TelephonyManager.PHONE_TYPE_CDMA);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return meid;
    }

    public String getString(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }

    public String getStringWithDefault(Context context, String key, String mdefault) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, mdefault);
    }

    public void putString(Context context, String key, String str) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(key, str).commit();
    }

    public void removeKey(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().remove(key).commit();
    }

    public void sendBroadcastIID(Context context, String key, String value, String flag) {
        Intent intent = new Intent(flag);
        intent.putExtra(key, value);
        intent.setPackage(AltoPayAppUtil.getInstance().getPackageName());
        context.sendBroadcast(intent);
    }

    public void deleteFiles(String downloadpath) {
        try {
            if (!TextUtils.isEmpty(downloadpath)) {
                LogUtil.e("tag", "---downloadpath--- " + downloadpath);
                File folder = new File(downloadpath);
                File[] files = folder.listFiles();
                if (files == null) {
                    return;
                }
                if (files.length > 0) {
                    for (File file : files) {
                        LogUtil.e("tag", "delete fileName: " + file.getName());
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.e("tag", "删除文件出错：" + e.toString());
        }

    }

    public String getLanguage() {
        return getString(getContext(), Constants.LANGUAGE_KEY);
    }

    public boolean isHasNewMsg() {
        return isHasNewMsg;
    }

    public void setHasNewMsg(boolean hasNewMsg) {
        isHasNewMsg = hasNewMsg;
    }
}

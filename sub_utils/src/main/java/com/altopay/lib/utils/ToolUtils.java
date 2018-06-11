package com.altopay.lib.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.ImageView;

import com.altopay.lib.utils.logutil.LogUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

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

    public static String getAndroidId(Context context) {
        try {
            String androidId = android.provider.Settings.System.getString(
                    context.getContentResolver(), TerminalCacheTool.ANDROID_ID);
            return androidId == null ? "" : androidId;
        } catch (Exception e) {
            return "";
        }

    }


    /**
     * 获取CPU序列号
     *
     * @return CPU序列号(16位) 读取失败为"0000000000000000"
     */
    public static String getCPUSerial() {
        String str = "";
        String strCPU = "";
        String cpuAddress = "0000000000000000";
        try {
            // 读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            // 查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    // 查找到序列号所在行
                    if (str.indexOf("Serial") > -1) {
                        // 提取序列号
                        strCPU = str.substring(str.indexOf(":") + 1,
                                str.length());
                        // 去空格
                        cpuAddress = strCPU.trim();
                        break;
                    }
                } else {
                    // 文件结尾
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            LogUtil.e(TAG, ex.toString());
        }
        return cpuAddress;
    }

    /**
     * 格式化日期 2013-12-25 16:19:53格式化为2013-12-25
     *
     * @param dateTime
     * @return
     */
    public static String formatDate(String dateTime) {
        if (dateTime == null || "".equals(dateTime)) {
            return dateTime;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(dateTime.substring(0, 4)).append("-")
                .append(dateTime.substring(4, 6)).append("-")
                .append(dateTime.substring(6, 8));
        return buffer.toString();
    }

    /**
     * 格式化时间 2013-12-25 16:19:53格式化位16:19:53
     *
     * @param dateTime
     * @return
     */
    public static String formatTime(String dateTime) {
        if (dateTime == null || "".equals(dateTime)) {
            return dateTime;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(dateTime.substring(8, 10)).append(":")
                .append(dateTime.substring(10, 12)).append(":")
                .append(dateTime.substring(12, 14));
        return buffer.toString();
    }

    /**
     * 时间戳转为 26-12-2017 12:12:12
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(time));
    }

    /**
     * 时间戳转为 25-12-2017 12:12
     * 不要秒数
     *
     * @param time
     * @return
     */
    public static String formatTimeDMYNoS(long time) {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date(time));
    }

    /**
     * 时间戳转为 2017-12-12 12:12
     * 不要秒数
     *
     * @param time
     * @return
     */
    public static String formatTimeNoS(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(time));
    }

    /**
     * 时间戳转为 2017-12-12 12:12:12
     *
     * @param time
     * @return
     */
    public static String formatTime(Date time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    /**
     * 时间戳转为 26-12-2017
     *
     * @param time
     * @return
     */
    public static String formatTimeNoHMS(long time) {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date(time));
    }

    /**
     * 时间戳转为 2017-12-12
     *
     * @param time
     * @return
     */
    public static String formatTimeNoHMS(Date time) {
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

    /**
     * 去掉格式化
     *
     * @param unstr
     * @return
     */

    public static String unFormatString(String unstr) {
        if (TextUtils.isEmpty(unstr)) {
            return "";
        }
        return unstr.replace(".", "");
    }

    /**
     * 格式化金额
     *
     * @param str
     * @return
     */
    public static String formatAmount(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        str = new StringBuilder(str).reverse().toString(); // 先将字符串颠倒顺序
        String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            if (i * 3 + 3 > str.length()) {
                str2 += str.substring(i * 3, str.length());
                break;
            }
            str2 += str.substring(i * 3, i * 3 + 3) + ".";
        }
        if (str2.endsWith(".")) {
            str2 = str2.substring(0, str2.length() - 1);
        }
        return new StringBuilder(str2).reverse().toString();
    }

    /**
     * 带负号的以及货币单位的格式化金额
     *
     * @param currencyUnit 货币单位
     * @param amount       金额
     * @param amtSign      金额正负
     * @return
     */
    public static String getFormatAmount(String currencyUnit, String amount, String amtSign) {
        if (TextUtils.isEmpty(amount)) {
            return "";
        }
        String str = amtSign.concat(" " + currencyUnit);

        return str.concat(formatAmount(amount));
    }

    public static String getFormatAmount(String currencyUnit, String amount) {
        if (TextUtils.isEmpty(amount)) {
            return "";
        }
        return currencyUnit + " " + formatAmount(amount);
    }

    /**
     * 检验金额是否合法
     *
     * @param formatAmount
     * @return true 合法  false 不合法
     */
    public static boolean checkAmount(String formatAmount) {
        if (TextUtils.isEmpty(formatAmount)) {
            return false;
        }
        String unFormatAmount = unFormatString(formatAmount);
        if (TextUtils.isEmpty(unFormatAmount)) {
            return false;
        }
        if (Long.parseLong(unFormatAmount) == 0) {
            return false;
        }
        return true;
    }

    /**
     * 部分手机 设置最大可选时间为传入当前时间之后 ，日历中无法选中当前的日期。
     * 把传入的时间转成 对应日期的最后一秒即可。
     *
     * @param time 传入时间戳
     * @return 返回 time对应的日期的最后一分钟的时间戳
     */
    public static long getTodayLastMinute(long time) {
        /*try {
            SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date =  sdf.parse("2017-10-28 23:59:59");
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            time = System.currentTimeMillis();
        }*/

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime().getTime();
    }


    /**
     * 判断网络是否可用joe true:可用 false:不可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (null == context) {
            return false;
        }
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取可用的网络信息
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            // 网络可用
            return true;
        }
        // 网络不可用
        return false;
    }

    public static boolean hasSDcard() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }


    public static void main(String[] args) {
        String mEndTime = "2014-01-04 12:12:12";
        System.out.println(ToolUtils.formatDate(mEndTime) + "  "
                + ToolUtils.formatTime(mEndTime));
    }


    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            LogUtil.d("", "获取mac出错：" + ex.toString());
        }
        return "";
    }

    public static int getRandom(int max) {
        Random random = new Random();
        return random.nextInt(max + 1);
    }

    /**
     * 根据format生成一维码或者二维码
     *
     * @param content
     * @param format
     * @param codeWidth
     * @param codeHeight
     * @return
     */
    private static Bitmap onCreateCoder(String content, BarcodeFormat format, int codeWidth, int codeHeight) {
        BitMatrix matrix;
        Hashtable<EncodeHintType, Object> qrParam = null;
        if (BarcodeFormat.QR_CODE.equals(format)) {
            // 用于设置QR二维码参数
            qrParam = new Hashtable<>();
            // 设置QR二维码的纠错级别——这里选择最高H级别
            qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            qrParam.put(EncodeHintType.MARGIN, 0);
            // 设置编码方式
            qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        }

        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        try {
            matrix = new MultiFormatWriter().encode(content, format, codeWidth, codeHeight, qrParam);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {//这个else要加上去，否者保存的二维码全黑
                    pixels[y * width + x] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 生成指定大小的二维码
     *
     * @param content
     * @param imageView
     * @return
     */
    public static Bitmap getTwoDBitmap(String content, ImageView imageView) {
        int width = 500;
        int height = 500;
        if (imageView != null) {
            int w = imageView.getWidth();
            int h = imageView.getHeight();
            width = w <= 0 ? width : w;
            height = h <= 0 ? height : h;
        }
        return onCreateCoder(content, BarcodeFormat.QR_CODE, width, height);
    }

    /**
     * 生成指定大小的条形码
     *
     * @param content
     * @param imageView
     * @return
     */
    public static Bitmap getOneDBitmap(String content, ImageView imageView) {
        int width = 300;
        int height = 120;
        if (imageView != null) {
            int w = imageView.getWidth();
            int h = imageView.getHeight();
            width = w <= 0 ? width : w;
            height = h <= 0 ? height : h;
        }
        return onCreateCoder(content, BarcodeFormat.CODE_128, width, height);
    }

    public static boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }

    /**
     * 反射调用系统权限,
     * 用于判断sdk_int >=23 targetVersion < 23 判断权限是否打开
     *
     * @param permissionCode 相应的权限所对应的code
     * @return 0标识
     * @see {@link AppOpsManager }
     */
    public static int reflectPermission(Context context, int permissionCode) {
        int checkPermission = 0;
        if (Build.VERSION.SDK_INT >= 19) {
            AppOpsManager _manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class<?>[] types = new Class[]{int.class, int.class, String.class};
                Object[] args = new Object[]{permissionCode, Binder.getCallingUid(), context.getPackageName()};
                Method method = _manager.getClass().getDeclaredMethod("noteOp", types);
                method.setAccessible(true);
                Object _o = method.invoke(_manager, args);
                if ((_o instanceof Integer)) {
                    checkPermission = (Integer) _o;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            checkPermission = 0;
        }
        return checkPermission;
    }


    public static String formatPhoneNumber(String loginName) {
        if (!loginName.startsWith("62")) {
            return "62".concat(loginName);
        }
        return loginName;
    }
}
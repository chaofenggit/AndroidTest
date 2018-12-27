package com.xiaoniu.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.os.Build.VERSION_CODES.M;

/**
 *
 *
 * 系统版本	是否支持设置状态栏颜色	是否允许设置状态栏黑色图标
 4.4	        否	                    否
 5.0	        是	                    否
 6.0+	    是	                    是
 */
public class StatusBarUtil {

    private static final String TAG = "StatusBarUtil";
    /**状态栏颜色*/
    private static final int STATUS_BAR_COLOR = R.color.CC_FFFFFF ;
    /**需要配合状态栏手动设置true或者false.状态栏颜色是否为亮色 */
    private static final boolean STATUS_BAR_COLOR_LIGHT = true;


    /**MIUI相关的信息*/
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.build.version.incremental";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    /**miui版本的分界线，7713以下使用MIUI使用的是自定义的修改状态栏方法，之后的版本使用的系统方法*/
    private static final String MIUI_VERSION_NAME = "7.7.13";
    /**-1 代表没有判断过。 0 代表不是，1代表是。*/
    private static int isMIUIOS = -1;
    /**获取到的MIUI版本号*/
    private static String MIUIVersion = "";

    /**当前页面状态栏的颜色是否为亮色*/
    private static boolean mStatusBarLight = true;

    /**
     * 设置状态栏颜色
     * void setContentView(int layoutResID) {
     *     super.setContentView(layoutResID);
     *     StatusBarUtil.setStatusBar(this);
     * }
     * @param activity
     */
    public static void setStatusBar(Activity activity) {
        setStatusBar(activity, STATUS_BAR_COLOR, STATUS_BAR_COLOR_LIGHT);
    }

    /**
     *
     * @param activity
     * @param statusBarColor 状态栏颜色
     * @param light 状态栏颜色是否为亮色
     */
    public static void setStatusBar(Activity activity, int statusBarColor, boolean light){
        mStatusBarLight = light;
        if (mStatusBarLight && Build.VERSION.SDK_INT >= M){
            setStateBarLight(activity, statusBarColor);
        }else if(!mStatusBarLight){
            setStateBarDark(activity, statusBarColor);
        }
    }

    /**
     * 设置深色状态栏
     * @param activity
     * @param colorResId
     */
    @TargetApi(19)
    private static void setStateBarDark(Activity activity, int colorResId) {
        //底部导航栏
        //window.setNavigationBarColor(activity.getResources().getColor(colorResId));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < M ) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorResId));
        }else if (Build.VERSION.SDK_INT >= M) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorResId));
            //设置状态栏文字颜色
            setStatusBarTextColor(activity);
            //防止布局整体上移
            fitsSystemWindowsTrue(activity);
        }
    }

    /**
     * 设置亮色状态栏
     * @param activity
     * @param colorResId
     */
    @TargetApi(23)
    private static void setStateBarLight(Activity activity, int colorResId ) {
        if (isMIUI()){
            MIUIStatusBarLight(activity,colorResId);
        }else{
            setNormalStateBarLight(activity, colorResId);
        }
    }

    /**
     * 是否MIUI
     * @return
     */
    private static boolean isMIUI(){
//        if (isMIUIOS != -1){
//            return isMIUIOS == 1;
//        }
//        try{
//            Properties properties = new Properties();
//            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
//            MIUIVersion = properties.getProperty(KEY_MIUI_VERSION_NAME);//
//            if (!TextUtils.isEmpty(properties.getProperty(KEY_MIUI_VERSION_CODE))
//                    || !TextUtils.isEmpty(MIUIVersion)
//                    || !TextUtils.isEmpty(properties.getProperty(KEY_MIUI_INTERNAL_STORAGE))){
//                isMIUIOS = 1;
//                return true;
//            }
//        }catch (Exception e){
//            isMIUIOS = 0;
//            e.printStackTrace();
//            Log.d(TAG, "获取系统是否为miui时报错：" + e.getMessage());
//        }
        return false;
    }

    /**
     * miui 状态栏
     * @param activity
     */
    private static void MIUIStatusBarLight(Activity activity,  int colorResId) {
        if (isMIUIV7713OrAbove()) {
            setNormalStateBarLight(activity, colorResId);
        }else {
            Window window=activity.getWindow();
            if (window != null) {
                Class clazz = window.getClass();
                try {
                    int darkModeFlag = 0;
                    Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    darkModeFlag = field.getInt(layoutParams);
                    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                    extraFlagField.invoke(window,darkModeFlag,darkModeFlag);
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d(TAG, "设置小米状态栏颜色字体颜色失败：" + e.getMessage());
                }
            }
        }
    }

    @TargetApi(23)
    private static void setNormalStateBarLight(Activity activity, int colorResId ){
        //设置状态栏颜色
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getResources().getColor(colorResId));
        //设置状态栏文字颜色
        setStatusBarTextColor(activity);
        //防止布局整体上移
        fitsSystemWindowsTrue(activity);
    }

    /**
     * 修改状态栏的文字颜色（6.0以上）
     * @param activity
     */
    @TargetApi(23)
    private static void setStatusBarTextColor(Activity activity) {
        View decor = activity.getWindow().getDecorView();
        if (mStatusBarLight) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    /**
     * 防止设置状态栏文字颜色的flag导致布局整体上移与状态栏重叠一部分
     * @param activity
     */
    private static void fitsSystemWindowsTrue(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View content = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            if (content != null) {
                content.setFitsSystemWindows(true);
            }
        }
    }


    /**
     * 开发版 7.7.13 及以后版本采用了系统API
     * 判断版本是否大于7713
     * @return
     */
    private static boolean isMIUIV7713OrAbove() {
        try {
            if (TextUtils.isEmpty(MIUIVersion)){
                return false;
            }
            String[] currentVersions = MIUIVersion.split(".");
            String[] targetVersion = MIUI_VERSION_NAME.split(".");
            if (Integer.parseInt(currentVersions[0]) < Integer.parseInt(targetVersion[0])){
                return false;
            }
            if (Integer.parseInt(currentVersions[1]) < Integer.parseInt(targetVersion[1])){
                return false;
            }
            if (Integer.parseInt(currentVersions[2]) < Integer.parseInt(targetVersion[2])){
                return false;
            }
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
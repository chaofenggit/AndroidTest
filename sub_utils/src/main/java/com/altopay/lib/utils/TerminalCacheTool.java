package com.altopay.lib.utils;

import android.content.Context;
import android.text.TextUtils;


public class TerminalCacheTool {

    public static final String ANDROID_ID = "android_id";

    private static String getHWTerminal(Context context) {
        String imei1 = ToolUtils.getImei(context);
        String imei2 = ToolUtils.getImei(context);
        if ((imei1 != null && imei1.equals(imei2)) || (imei1 == null && imei2 == null)) {
            return MD5.MD5Tool(ToolUtils.getImei(context) + ToolUtils.getAndroidId(context) + ToolUtils.getMacAddress(), "UTF-8");
        } else {
            return MD5.MD5Tool(ToolUtils.getCPUSerial() + ToolUtils.getAndroidId(context) + ToolUtils.getMacAddress(), "UTF-8");
        }
    }

    /**
     * 生成terminalid时优先从本地缓存文件获取，如果没有缓存则现有规则生成之后再做文件缓存
     */
    public static String getTerminal() {
        if (!TextUtils.isEmpty(AltoPayAppUtil.getInstance().getTid())) {
            return AltoPayAppUtil.getInstance().getTid();
        }
        String terminlid = getHWTerminal(AltoPayAppUtil.getInstance().getContext());
        AltoPayAppUtil.getInstance().setTid(terminlid);
        return terminlid;
    }

}

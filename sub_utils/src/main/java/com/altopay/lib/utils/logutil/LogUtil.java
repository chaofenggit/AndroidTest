package com.altopay.lib.utils.logutil;


import android.text.TextUtils;

/**
 * 日志管理器
 */
public final class LogUtil {
    private static Logger printer = new Logger();
    private static LogConfigImpl logConfig = LogConfigImpl.getInstance();

    /**
     * 选项配置
     *
     * @return
     */
    public static LogConfig getLogConfig() {
        return logConfig;
    }

    public static Printer tag(String tag) {
        return printer.setTag(tag);
    }

    /**
     * verbose输出
     *
     * @param tag
     * @param args
     */
    public static void v(String tag, Object... args) {
        if(TextUtils.isEmpty(tag)){
            v(args);
        }else{
            tag(tag).v(args);
        }
    }
    public static void v(Object object) {
        printer.v(object);
    }
    public static void v(String tag, String msg) {
        if(TextUtils.isEmpty(tag)){
            v(msg);
        }else{
            tag(tag).v(msg);
        }
    }
    public static void v(String msg) {
        printer.v(msg);
    }

    /**
     * debug输出
     *
     * @param tag
     * @param args
     */
    public static void d(String tag, Object... args) {
        if(TextUtils.isEmpty(tag)){
            d(args);
        }else{
            tag(tag).d(args);
        }
    }
    public static void d(Object object) {
        printer.d(object);
    }
    public static void d(String tag, String msg) {
        if(TextUtils.isEmpty(tag)){
            d(msg);
        }else{
            tag(tag).d(msg);
        }
    }

    public static void d(String msg) {
        printer.d(msg);
    }
    /**
     * info输出
     *
     * @param tag
     * @param args
     */
    public static void i(String tag, Object... args) {
        if(TextUtils.isEmpty(tag)){
            i(args);
        }else{
            tag(tag).i(args);
        }
    }

    public static void i(Object object) {
        printer.i(object);
    }
    public static void i(String tag, String msg) {
        if(TextUtils.isEmpty(tag)){
            i(msg);
        }else{
            tag(tag).i(msg);
        }
    }

    public static void i(String msg) {
        printer.i(msg);
    }

    /**
     * warn输出
     *
     * @param tag
     * @param args
     */
    public static void w(String tag, Object... args) {
        if(TextUtils.isEmpty(tag)){
            w(args);
        }else{
            tag(tag).w(args);
        }
    }

    public static void w(Object object) {
        printer.w(object);
    }
    public static void w(String tag, String msg) {
        if(TextUtils.isEmpty(tag)){
            w(msg);
        }else{
            tag(tag).w(msg);
        }
    }

    public static void w(String msg) {
        printer.w(msg);
    }
    /**
     * error输出
     *
     * @param tag
     * @param args
     */
    public static void e(String tag, Object... args) {
        if(TextUtils.isEmpty(tag)){
            e(args);
        }else{
            tag(tag).e(args);
        }
    }

    public static void e(Object object) {
        printer.e(object);
    }
    public static void e(String tag, String msg) {
        if(TextUtils.isEmpty(tag)){
            e(msg);
        }else{
            tag(tag).e(msg);
        }
    }

    public static void e(String msg) {
        printer.e(msg);
    }
    /**
     * assert输出
     *
     * @param tag
     * @param args
     */
    public static void wtf(String tag, Object... args) {
        if(TextUtils.isEmpty(tag)){
            wtf(args);
        }else{
            tag(tag).wtf(args);
        }
    }

    public static void wtf(Object object) {
        printer.wtf(object);
    }
    public static void wtf(String tag, String msg) {
        if(TextUtils.isEmpty(tag)){
            wtf(msg);
        }else{
            tag(tag).wtf(msg);
        }
    }

    public static void wtf(String msg) {
        printer.wtf(msg);
    }
    /**
     * 打印json
     *
     * @param json
     */
    public static void json(String json) {
        printer.json(json);
    }
    public static void json(String tag, String json) {
        if(TextUtils.isEmpty(tag)){
            json(json);
        }else{
            tag(tag).json(json);
        }
    }
    public static void json(String tag, String directions, String json) {
        if(TextUtils.isEmpty(tag)){
            printer.json(directions, json);
        }else{
            tag(tag).json(directions, json);
        }
    }
    /**
     * 输出xml
     * @param xml
     */
    public static void xml(String xml) {
        printer.xml(xml);
    }
    public static void xml(String tag, String xml) {
        if(TextUtils.isEmpty(tag)){
            xml(xml);
        }else{
            tag(tag).xml(xml);
        }
    }
    public static void xml(String tag, String directions, String json) {
        if(TextUtils.isEmpty(tag)){
            printer.xml(directions, json);
        }else{
            tag(tag).xml(directions, json);
        }
    }

    public static boolean outDebugIsExist(){
        return Logger.fileIsExist();
    }
}

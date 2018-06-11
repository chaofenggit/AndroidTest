package com.altopay.lib.utils.logutil;

import android.text.TextUtils;
import android.util.Log;

import com.altopay.lib.utils.AltopayConfig;
import com.altopay.lib.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.MissingFormatArgumentException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static com.altopay.lib.utils.logutil.LogLevel.TYPE_DEBUG;
import static com.altopay.lib.utils.logutil.LogLevel.TYPE_ERROR;
import static com.altopay.lib.utils.logutil.LogLevel.TYPE_INFO;
import static com.altopay.lib.utils.logutil.LogLevel.TYPE_VERBOSE;
import static com.altopay.lib.utils.logutil.LogLevel.TYPE_WARM;
import static com.altopay.lib.utils.logutil.LogLevel.TYPE_WTF;
import static com.altopay.lib.utils.logutil.utils.ObjectUtil.objectToString;
import static com.altopay.lib.utils.logutil.utils.Utils.DIVIDER_BOTTOM;
import static com.altopay.lib.utils.logutil.utils.Utils.DIVIDER_CENTER;
import static com.altopay.lib.utils.logutil.utils.Utils.DIVIDER_CENTER_RIGHT;
import static com.altopay.lib.utils.logutil.utils.Utils.DIVIDER_NORMAL;
import static com.altopay.lib.utils.logutil.utils.Utils.DIVIDER_TOP;
import static com.altopay.lib.utils.logutil.utils.Utils.largeStringToList;
import static com.altopay.lib.utils.logutil.utils.Utils.printDividingLine;

class Logger implements Printer {

    private LogConfigImpl mLogConfig;
    private final ThreadLocal<String> localTags = new ThreadLocal<String>();

    protected Logger() {
        mLogConfig = LogConfigImpl.getInstance();
        mLogConfig.addParserClass(Constant.DEFAULT_PARSE_CLASS);
    }

    /**
     * 设置临时tag
     *
     * @param tag
     * @return
     */
    public Printer setTag(String tag) {
        if (!TextUtils.isEmpty(tag) && mLogConfig.isEnable()) {
            localTags.set(tag);
        }
        return this;
    }

    /**
     * 打印字符串
     *
     * @param type
     * @param msg
     * @param args
     */
    private synchronized void logString(@LogLevel.LogLevelType int type, String msg, Object... args) {
        String tag = generateTag();
        logString(type, tag, msg, false, args);
    }

    private void logString(@LogLevel.LogLevelType int type, String tag, String msg, boolean isPart, Object... args) {
        if (!mLogConfig.isEnable()) {
            return;
        }
        if (type < mLogConfig.getLogLevel()) {
            return;
        }

        //log日志写入本地
        if (fileIsExist()) {
            writeLogFromSDCard(tag, "", msg);
        }

        //情况一
        if (msg.length() > Constant.LINE_MAX) {
            if (mLogConfig.isShowBorder()) {
                printLog(type, tag, printDividingLine(DIVIDER_TOP));
                printLog(type, tag, printDividingLine(DIVIDER_CENTER) + getTopStackInfo() + printDividingLine(DIVIDER_CENTER_RIGHT));
                printLog(type, tag, printDividingLine(DIVIDER_NORMAL));
            }
            for (String subMsg : largeStringToList(msg)) {
                logString(type, tag, subMsg, true, args);
            }
            if (mLogConfig.isShowBorder()) {
                printLog(type, tag, printDividingLine(DIVIDER_BOTTOM));
            }
            return;
        }
        //情况二
        if (args.length > 0) {
            try {
                msg = String.format(msg, args);
            } catch (MissingFormatArgumentException e) {

            }
        }
        if (mLogConfig.isShowBorder()) {
            if (isPart) {
                for (String sub : msg.split(Constant.BR)) {
                    printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + sub);
                }
            } else {
                printLog(type, tag, printDividingLine(DIVIDER_TOP));
                printLog(type, tag, printDividingLine(DIVIDER_CENTER) + getTopStackInfo() + printDividingLine(DIVIDER_CENTER_RIGHT));
                printLog(type, tag, printDividingLine(DIVIDER_NORMAL));
                for (String sub : msg.split(Constant.BR)) {
                    printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + sub);
                }
                printLog(type, tag, printDividingLine(DIVIDER_BOTTOM));
            }
        } else {
            printLog(type, tag, msg);
        }
    }


    /**
     * 打印对象
     *
     * @param type
     * @param object
     */
    private void logObject(@LogLevel.LogLevelType int type, Object object) {
        logString(type, objectToString(object));
    }

    /**
     * 自动生成tag
     *
     * @return
     */
    private String generateTag() {
        String tempTag = localTags.get();
        if (!TextUtils.isEmpty(tempTag)) {
            localTags.remove();
            return tempTag;
        }
        return mLogConfig.getTagPrefix();
    }

    /**
     * 获取当前activity栈信息
     *
     * @return
     */
    private StackTraceElement getCurrentStackTrace() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int stackOffset = getStackOffset(trace, LogUtil.class);
        if (stackOffset == -1) {
            stackOffset = getStackOffset(trace, Logger.class);
            if (stackOffset == -1) {
                return null;
            }
        }
        StackTraceElement caller = trace[stackOffset];
        return caller;
    }

    /**
     * 获取最顶部stack信息
     *
     * @return
     */
    private String getTopStackInfo() {
        String customTag = mLogConfig.getFormatTag(getCurrentStackTrace());
        if (customTag != null) {
            return customTag;
        }
        StackTraceElement caller = getCurrentStackTrace();
        String stackTrace = caller.toString();
        stackTrace = stackTrace.substring(stackTrace.lastIndexOf('('), stackTrace.length());
        String tag = "%s.%s%s";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), stackTrace);
        return tag;
    }

    private int getStackOffset(StackTraceElement[] trace, Class cla) {
        for (int i = Constant.MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (cla.equals(Logger.class) && i < trace.length - 1 && trace[i + 1].getClassName()
                    .equals(Logger.class.getName())) {
                continue;
            }
            if (name.equals(cla.getName())) {
                return ++i;
            }
        }
        return -1;
    }

    @Override
    public void d(String message, Object... args) {
        logString(TYPE_DEBUG, message, args);
    }

    @Override
    public void d(Object object) {
        logObject(TYPE_DEBUG, object);
    }

    @Override
    public void d(String message) {
        logString(TYPE_DEBUG, message);
    }

    @Override
    public void e(String message, Object... args) {
        logString(TYPE_ERROR, message, args);
    }

    @Override
    public void e(Object object) {
        logObject(TYPE_ERROR, object);
    }

    @Override
    public void e(String message) {
        logString(TYPE_ERROR, message);
    }

    @Override
    public void w(String message, Object... args) {
        logString(TYPE_WARM, message, args);
    }

    @Override
    public void w(Object object) {
        logObject(TYPE_WARM, object);
    }

    @Override
    public void w(String message) {
        logString(TYPE_WARM, message);
    }

    @Override
    public void i(String message, Object... args) {
        logString(TYPE_INFO, message, args);
    }

    @Override
    public void i(Object object) {
        logObject(TYPE_INFO, object);
    }

    @Override
    public void i(String message) {
        logString(TYPE_INFO, message);
    }

    @Override
    public void v(String message, Object... args) {
        logString(TYPE_VERBOSE, message, args);
    }

    @Override
    public void v(Object object) {
        logObject(TYPE_VERBOSE, object);
    }

    @Override
    public void v(String message) {
        logString(TYPE_VERBOSE, message);
    }

    @Override
    public void wtf(String message, Object... args) {
        logString(TYPE_WTF, message, args);
    }

    @Override
    public void wtf(Object object) {
        logObject(TYPE_WTF, object);
    }

    @Override
    public void wtf(String message) {
        logString(TYPE_WTF, message);
    }

    /**
     * 采用orhanobut/logger的json解析方案
     * source:https://github.com/orhanobut/logger/blob/master/logger/src/main/java/com/orhanobut/logger/LoggerPrinter.java#L152
     *
     * @param json
     */
    @Override
    public void json(String json) {
        json(null, json);
    }

    @Override
    public void json(String directions, String json) {
        int indent = 4;
        if (TextUtils.isEmpty(json)) {
            if (TextUtils.isEmpty(directions)) {
                d("JSON{json is empty}");
            } else {
                d(directions + ":JSON{json is empty}");
            }
            return;
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String msg = jsonObject.toString(indent);
                if (!TextUtils.isEmpty(directions)) {
                    msg = directions + ":\n" + msg;
                }
                d(msg);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String msg = jsonArray.toString(indent);
                if (!TextUtils.isEmpty(directions)) {
                    msg = directions + ":\n" + msg;
                }
                d(msg);
            } else {
                if (!TextUtils.isEmpty(directions)) {
                    json = directions + ":\n" + json;
                }
                d(json);
            }
        } catch (JSONException e) {
            e(e.toString() + "\n\njson = " + json);
        }
    }


    /**
     * 采用orhanobut/logger的xml解析方案
     * source:https://github.com/orhanobut/logger/blob/master/logger/src/main/java/com/orhanobut/logger/LoggerPrinter.java#L180
     *
     * @param xml
     */
    @Override
    public void xml(String xml) {
        if (TextUtils.isEmpty(xml)) {
            d("XML{xml is empty}");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e(e.toString() + "\n\nxml = " + xml);
        }
    }

    @Override
    public void xml(String directions, String xml) {

    }


    /**
     * 打印日志
     *
     * @param type
     * @param tag
     * @param msg
     */
    private void printLog(@LogLevel.LogLevelType int type, String tag, String msg) {
        if (!AltopayConfig.DEBUG) {
            //非DEBUG模式，不打印
            return;
        }
        String logTag = Constant.LOG_TAG;
        tag = logTag + tag;

        if (!mLogConfig.isShowBorder()) {
            msg = getTopStackInfo() + ": \n" + msg;
        }
        switch (type) {
            case TYPE_VERBOSE:
                Log.v(tag, msg);
                break;
            case TYPE_DEBUG:
                Log.d(tag, msg);
                break;
            case TYPE_INFO:
                Log.i(tag, msg);
                break;
            case TYPE_WARM:
                Log.w(tag, msg);
                break;
            case TYPE_ERROR:
                Log.e(tag, msg);
                break;
            case TYPE_WTF:
                Log.wtf(tag, msg);
                break;
            default:
                break;
        }
    }


    /**
     * 判断outdebug文件是否存在
     **/
    public static boolean fileIsExist() {
        String sDStateString = android.os.Environment.getExternalStorageState();
        if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
            try {
                File SDFile = android.os.Environment.getExternalStorageDirectory();
                File logDir = new File(SDFile.getAbsolutePath() + File.separator + Constants.PLATFORMID_FOLDER);
                File logFlagFile = new File(SDFile.getAbsolutePath() + File.separator + Constants.PLATFORMID_FOLDER + File.separator + "outdebug");
                File logFile = new File(SDFile.getAbsolutePath() + File.separator + Constants.PLATFORMID_FOLDER + File.separator + "outdebuginfo.log");
                if (!logDir.exists()) {
                    logDir.mkdir();
                    return false;
                }

                if (!logFlagFile.exists()) {
                    if (logFile.exists()) {
                        logFile.delete();
                    }
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * SXLogUtils info 写入SD卡
     */
    private static void writeLogFromSDCard(String fileName, String funName, String msg) {
        String sDStateString = android.os.Environment.getExternalStorageState();
        String date_msg;
        if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
            try {
                File SDFile = android.os.Environment.getExternalStorageDirectory();
                File myDir = new File(SDFile.getAbsolutePath() + File.separator + Constants.PLATFORMID_FOLDER);
                File myFile = new File(SDFile.getAbsolutePath() + File.separator + Constants.PLATFORMID_FOLDER + File.separator + "outdebuginfo.log");
                if (!myDir.exists()) {
                    myDir.mkdir();
                    myFile.createNewFile();
                } else if (!myFile.exists()) {
                    myFile.createNewFile();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String dateString = sdf.format(new Date());
                if (TextUtils.isEmpty(msg)) {
                    date_msg = dateString + "    " + fileName + "---" + funName + "\r\n";
                } else {
                    date_msg = dateString + "    " + fileName + "---" + funName + "---" + msg + "\r\n";
                }
                BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(myFile, true)));
                outputStream.write(date_msg);
                outputStream.close();
            } catch (Exception e) {

            }
        }
    }

}

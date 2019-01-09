package com.xiaoniu.reader.utils;

import android.content.Context;
import android.graphics.Paint;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiaoniu
 * @date 2018/7/23.
 */

public class ToolUtil {

    /***
     * 格式化时间
     *  2018-07-23
     * @param time
     * @return
     */
    public static String formatTime(long time){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
    }

    /**
     * 格式化文件大小
     * @param size
     * @return
     */
    public static String formatFileSize(long size){
        if (size <= 0){
            return "0" + Constants.KEY_B;
        }
        if (size < Constants.SIZE_KB){
            return size  + Constants.KEY_B;
        }

        if (size < Constants.SIZE_MB){
            return formatDecimal(size * 1.0f/Constants.SIZE_KB) + Constants.KEY_KB;
        }

        return formatDecimal(size * 1.0f/Constants.SIZE_MB) + Constants.KEY_MB;
    }

    /**
     * 格式化小数 保留2位小数
     * @param data
     * @return
     */
    public static String formatDecimal(float data){
        return new DecimalFormat("0.00").format(data);
    }

}

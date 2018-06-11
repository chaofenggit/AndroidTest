package com.altopay.lib.utils.logutil;


import com.altopay.lib.utils.logutil.parser.BundleParse;
import com.altopay.lib.utils.logutil.parser.CollectionParse;
import com.altopay.lib.utils.logutil.parser.IntentParse;
import com.altopay.lib.utils.logutil.parser.MapParse;
import com.altopay.lib.utils.logutil.parser.ReferenceParse;
import com.altopay.lib.utils.logutil.parser.ThrowableParse;

import java.util.List;

public class Constant {

    public static final String STRING_OBJECT_NULL = "Object[object is null]";

    // 每行最大日志长度
    public static final int LINE_MAX = 1024 * 3;

    // 解析属性最大层级
    public static final int MAX_CHILD_LEVEL = 2;

    public static final int MIN_STACK_OFFSET = 5;

    // 换行符
    public static final String BR = System.getProperty("line.separator");

    // 空格
    public static final String SPACE = "\t";

    public static final String LOG_TAG = "<---pay--->";

    // 默认支持解析库
    public static final Class<? extends Parser>[] DEFAULT_PARSE_CLASS = new Class[]{
            BundleParse.class, IntentParse.class, CollectionParse.class,
            MapParse.class, ThrowableParse.class, ReferenceParse.class
    };


    /**
     * 获取默认解析类
     *
     * @return
     */
    public static final List<Parser> getParsers() {
        return LogConfigImpl.getInstance().getParseList();
    }
}

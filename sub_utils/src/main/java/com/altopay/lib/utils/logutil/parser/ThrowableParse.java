package com.altopay.lib.utils.logutil.parser;

import android.util.Log;

import com.altopay.lib.utils.logutil.Parser;

/**
 * Created by pengwei on 16/3/8.
 */
public class ThrowableParse implements Parser<Throwable> {
    @Override
    public Class<Throwable> parseClassType() {
        return Throwable.class;
    }

    @Override
    public String parseString(Throwable throwable) {
        return Log.getStackTraceString(throwable);
    }
}

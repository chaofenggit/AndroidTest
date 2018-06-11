package com.altopay.lib.utils.logutil;

public interface Printer {

    void d(String message, Object... args);
    void d(Object object);
    void d(String message);

    void e(String message, Object... args);
    void e(Object object);
    void e(String message);

    void w(String message, Object... args);
    void w(Object object);
    void w(String message);

    void i(String message, Object... args);
    void i(Object object);
    void i(String message);

    void v(String message, Object... args);
    void v(Object object);
    void v(String message);

    void wtf(String message, Object... args);
    void wtf(Object object);
    void wtf(String message);

    void json(String json);
    void json(String directions, String json);
    void xml(String xml);
    void xml(String directions, String xml);
}

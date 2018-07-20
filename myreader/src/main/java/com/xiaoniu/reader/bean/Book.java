package com.xiaoniu.reader.bean;

/**
 * @author xiaoniu
 * @date 2018/7/20.
 */

public class Book {
    private static final String TAG = "Book";

    public static final int TYPE_ADD = -1;
    public static final int TYPE_NORMAL = 0;
    /**添加书本*/
    public static final  Book addBook = new Book("","", -1);

    private String name;
    private String path;
    /** 0 正常书本 -1 添加书本*/
    private int type;

    public Book(String name, String path) {
        this.name = name;
        this.path = path;
        this.type = 0;
    }

    public Book(String name, String path, int type) {
        this.name = name;
        this.path = path;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "[ name = " + name + ",path = " + path + ",type = " + type + "]";
    }
}

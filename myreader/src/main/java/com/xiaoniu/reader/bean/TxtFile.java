package com.xiaoniu.reader.bean;

/**
 * @author xiaoniu
 * @date 2018/7/20.
 */

public class TxtFile {
    private static final String TAG = "TxtFile";

    private String name;
    private String path;
    /**格式*/
    private int format;
    /**大小*/
    private long size;
    /**修改时间*/
    private long modifyTime;
    /**是否被选中*/
    private boolean isSelected;

    public TxtFile() {
    }

    public TxtFile(String name, String path, long size, long modifyTime, boolean isSelected) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.modifyTime = modifyTime;
        this.isSelected = isSelected;
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

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "[ name = " + name
                + ",path = " + path
                + ",format = "  + format
                + ",size = " + size
                + ",modifyTime = " + modifyTime
                + ",isSelected = " + isSelected + "]";
    }
}

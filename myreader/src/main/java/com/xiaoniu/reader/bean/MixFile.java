package com.xiaoniu.reader.bean;

import android.text.TextUtils;

import com.xiaoniu.reader.utils.Constants;

/**
 * @author xiaoniu
 * @date 2018/7/20.
 */

public class MixFile extends TxtFile{

    public static final int TYPE_FILE = 0;
    public static final int TYPE_FOLDER = 1;

    private int type;
    private int childFileCount;

    public MixFile() {
    }

    public MixFile(String name, String path, long size, long modifyTime, boolean isSelected) {
        super(name, path, size, modifyTime, isSelected);
        this.type = TYPE_FILE;
    }

    public MixFile(String name,String path, int childFileCount) {
        setName(name);
        setPath(path);
        this.childFileCount = childFileCount;
        this.type = TYPE_FOLDER;
    }

    public boolean isFolder() {
        return type == TYPE_FOLDER;
    }
    public boolean isFile() {
        return type == TYPE_FILE;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChildFileCount() {
        return childFileCount;
    }

    public void setChildFileCount(int childFileCount) {
        this.childFileCount = childFileCount;
    }

    /**
     * 文件是否可以被选中
     * 非文件夹 and 以.txt结尾可以被选中
     * @return
     */
    public boolean getCanSelect(){
        if (isFile() && !TextUtils.isEmpty(getName()) && getName().endsWith(Constants.FORMAT_TXT)){
            return true;
        }
        return false;
    }
}

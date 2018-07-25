package com.xiaoniu.reader.listener;

import com.xiaoniu.reader.bean.TxtFile;

import java.util.List;

/**
 * @author xiaoniu
 * @date 2018/7/24.
 */

public interface FileUtilListener {
    /**
     * 文件过滤完成
     * @param fileList
     */
    void complete(List<TxtFile> fileList);
}

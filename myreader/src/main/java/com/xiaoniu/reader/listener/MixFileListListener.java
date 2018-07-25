package com.xiaoniu.reader.listener;

import com.xiaoniu.reader.bean.MixFile;

/**
 * @author xiaoniu
 * @date 2018/7/25.
 */

public interface MixFileListListener  {
    /**
     * item的点击
     * @param position
     * @param mixFile
     */
    void onItemClick(int position, MixFile mixFile);

    /**
     * item的选中
     * @param position
     * @param mixFile
     */
    void onItemChecked(int position, MixFile mixFile);
}

package com.xiaoniu.reader.listener;

/**
 * @author xiaoniu
 * @date 2018/7/26.
 */

public interface BookListListener{

    /**
     * item 长按
     * @param position
     */
    void onItemLongClick(int position);

    /**
     * item 点击
     * @param position
     */
    void onItemClick(int position);
}

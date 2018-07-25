package com.xiaoniu.reader.listener;

/**
 * @author xiaoniu
 * @date 2018/7/24.
 *
 * 图书变化的观察者
 */

public interface BookChangeObserver {

    /**
     * 图书变化
     */
    void bookChange();
}

package com.xiaoniu.reader.manager;

import com.xiaoniu.reader.listener.BookChangeObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoniu
 * @date 2018/7/24.
 *
 * 图书变化的被观察者
 */

public class BookChangeManager {

    private static List<BookChangeObserver> observers = new ArrayList<>();

    /**
     * 添加观察者
     * @param observer
     */
    public static void addBookChangeObserver(BookChangeObserver observer){
        if (observer != null){
            observers.add(observer);
        }
    }

    /**
     * 通知所有的观察者数据变化
     */
    public static void notifyAllObserver(){
        for (BookChangeObserver observer: observers) {
            observer.bookChange();
        }
    }

    /**
     * 移除观察者
     * @param observer
     */
    public static void removeObserver(BookChangeObserver observer){
        if (observer != null){
            observers.remove(observer);
        }
    }

}

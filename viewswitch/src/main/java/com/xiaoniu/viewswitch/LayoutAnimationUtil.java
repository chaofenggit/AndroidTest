package com.xiaoniu.viewswitch;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * @author xiaoniu
 * @date 2018/11/7.
 */

public class LayoutAnimationUtil {

    /** 动画执行时间*/
    private final static int DURATION = 3000;
    /**从左侧进入(退出)*/
    public final static int IN_OUT_LEFT = 0;
    /**从上侧进入(退出)*/
    public final static int IN_OUT_TOP = 1;
    /**从右侧进入(退出)*/
    public final static int IN_OUT_RIGHT = 2;
    /**从下侧进入(退出)*/
    public final static int IN_OUT_BOTTOM = 3;

    /**
     * 显示界面动画:从top或者bottom进入
     * 变化的是布局的高度
     */
    public static void enterFromTopBottom(int viewHeight, final View contentView) {
        contentView.setVisibility(View.VISIBLE);
        ValueAnimator enterAnimator = ValueAnimator.ofInt(0,viewHeight);
        enterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                contentView.getLayoutParams().height = (int) animation.getAnimatedValue();
                contentView.requestLayout();
            }
        });
        enterAnimator.setDuration(DURATION);
        enterAnimator.start();
    }

    /**
     * 退出界面动画
     * 从top或者bottom退出
     * 变化的是布局的高度
     */
    public static void exitToTopBottom(int viewHeight, final View contentView, final AnimationListener listener){
        ValueAnimator  exitAnimator = ValueAnimator.ofInt(viewHeight,0);
        exitAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                contentView.getLayoutParams().height = (int) animation.getAnimatedValue();
                contentView.requestLayout();
                if (animation.getAnimatedFraction() == 1){
                    contentView.setVisibility(View.GONE);
                    if (listener != null){
                        listener.onFinish();
                    }
                }
            }
        });
        exitAnimator.setDuration(DURATION);
        exitAnimator.start();
    }

    /**
     * 退出界面动画
     * 从top或者bottom退出
     * 变化的是布局的高度
     */
    public static void exitToLeftRight(int viewWidth, final View contentView, final AnimationListener listener){
        ValueAnimator  exitAnimator = ValueAnimator.ofInt(viewWidth,0);
        exitAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                contentView.getLayoutParams().width = (int) animation.getAnimatedValue();
                contentView.requestLayout();
                if (animation.getAnimatedFraction() == 1){
                    if (listener != null){
                        listener.onFinish();
                    }
                }
            }
        });
        exitAnimator.setDuration(DURATION);
        exitAnimator.start();
    }

    /**
     * 进入动画
     * 变化的是布局的位置
     * @param viewWidth
     * @param viewHeight
     * @param contentView
     * @param orientation
     */
    public static void enterAnimation(int viewWidth, int viewHeight, View contentView, int orientation){
        contentView.setVisibility(View.VISIBLE);
        ObjectAnimator animator = null;
        if (orientation == IN_OUT_LEFT){
            animator = ObjectAnimator.ofFloat(contentView, "translationX", -viewWidth, 0);
        }else if (orientation == IN_OUT_TOP){
            animator = ObjectAnimator.ofFloat(contentView, "translationY", -viewHeight,0);
        }else if (orientation == IN_OUT_RIGHT){
            animator = ObjectAnimator.ofFloat(contentView, "translationX", viewWidth, 0);
        }else if (orientation == IN_OUT_BOTTOM){
            animator = ObjectAnimator.ofFloat(contentView, "translationY", viewHeight, 0);
        }
        animator.setDuration(DURATION);
        animator.start();
    }
    /**
     * 退出动画
     * 变化的是布局的位置
     * @param viewWidth 如果移动方向是左右方向，此参数必须填写
     * @param viewHeight 如果移动方向是上下方向，此参数必须填写
     * @param contentView 被操作的view
     * @param orientation 移动的方向
     * @param listener 进度监听
     */
    public static void outAnimation(int viewWidth, int viewHeight, final View contentView, int orientation, final AnimationListener listener){
        contentView.setVisibility(View.VISIBLE);
        ObjectAnimator animator = null;
        if (orientation == IN_OUT_LEFT){
            animator = ObjectAnimator.ofFloat(contentView, "translationX", 0, -viewWidth);
        }else if (orientation == IN_OUT_TOP){
            animator = ObjectAnimator.ofFloat(contentView, "translationY",0, -viewHeight);
        }else if (orientation == IN_OUT_RIGHT){
            animator = ObjectAnimator.ofFloat(contentView, "translationX", 0, viewWidth);
        }else if (orientation == IN_OUT_BOTTOM){
            animator = ObjectAnimator.ofFloat(contentView, "translationY", 0, viewHeight);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getAnimatedFraction() == 1){
                    if (listener != null){
                        listener.onFinish();
                    }
                }
            }
        });
        animator.setDuration(DURATION);
        animator.start();
    }


    /**
     * 动画的结束监听
     */
    public interface AnimationListener{
        /**
         * 完成
         */
        void onFinish();
    }
}

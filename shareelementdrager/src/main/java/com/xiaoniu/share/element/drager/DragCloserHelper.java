package com.xiaoniu.share.element.drager;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.FloatRange;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * @author xiaoniu
 * @date 2019/5/5.
 */

public class DragCloserHelper {

    private static final String TAG = "DragCloserHelper";
    /**最大退出距离*/
    private final int MAX_EXIT_Y = 500;
    private final float MIN_SCALE = 0.2f, MAX_SCALE = 1.0F;

    private ViewConfiguration configuration;
    /** pView用来调整背景透明度，cView就是被拖拽的view */
    private View pView, cView;

    private DragCloseListener dragCloseListener;

    /**上次触摸的手机id*/
    private int lastPointerID;
    /**手指的位置*/
    private float mLastY, mLastX,mLastRowY,mLastRowX;
    /**上次移动的距离*/
    private float mLastTransLationX,mLastTransLationY;
    /**当前移动的距离*/
    private float mCurrentTransLationX,mCurrentTransLationY;
    /**是否处在滑动关闭中*/
    private boolean isSwipingToClose = false;
    /**最小缩放尺寸*/
    private float minScale = 0.2f;
    /**是否为共享元素模式*/
    private boolean isShareElementMode = false;

    public DragCloserHelper(Context context) {
        configuration = ViewConfiguration.get(context);
    }

    /**
     * 设置拖拽的view
     * @param parentView 用于调整布局的背景透明度
     * @param childView 拖拽的view
     */
    public void setDragCloseViews(View parentView,View childView){
        this.pView = parentView;
        this.cView = childView;
    }

    public void setDragCloseListener(DragCloseListener dragCloseListener) {
        this.dragCloseListener = dragCloseListener;
    }

    public void setMinScale(@FloatRange(from = 0.2f, to = 1.0f) float minScale) {
        this.minScale = minScale;
    }

    public void setShareElementMode(boolean shareElementMode) {
        isShareElementMode = shareElementMode;
    }

    /**
     * 处理拖拽
     * @param event
     */
    public boolean handleTouchEvent(MotionEvent event){
        if (dragCloseListener != null && dragCloseListener.intercept()){
            Log.d(TAG, "event被拦截，不进行滑动");
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            Log.d(TAG, "action_down");
            lastPointerID = event.getPointerId(0);
            reset(event);
        }else if (action == MotionEvent.ACTION_MOVE){
            Log.d(TAG, "action_move");
            //多个手指
            if (event.getPointerCount() > 1){
                if (isSwipingToClose){
                    isSwipingToClose = false;
                    resetCallBackAnimation();
                    return true;
                }
                reset(event);
                return false;
            }
            //手指发生变化
            if (event.getPointerId(0) != lastPointerID){
                if (isSwipingToClose){
                    resetCallBackAnimation();
                }
                reset(event);
                return false;
            }
            //开始拖拽滑动
            float currentX = event.getX();
            float currentY = event.getY();
            if (isSwipingToClose || Math.abs(currentY - mLastY) > 2*configuration.getScaledTouchSlop()){
                mLastY = currentY;
                mLastX = currentX;
                Log.d(TAG, "start move...");
                if (!isSwipingToClose){
                    isSwipingToClose = true;
                    if (dragCloseListener != null){
                        dragCloseListener.startDrag();
                    }
                }
                mCurrentTransLationX = event.getRawX() - mLastRowX + mLastTransLationX;
                mCurrentTransLationY = event.getRawY() - mLastRowY + mLastTransLationY;
                float percent = 1 - Math.abs(mCurrentTransLationY/(MAX_EXIT_Y + cView.getHeight()));
                if (percent > 1){
                    percent = 1;
                }else if (percent < 0){
                    percent = 0;
                }
                pView.getBackground().mutate().setAlpha((int)( percent*255));
                cView.setTranslationX(mCurrentTransLationX);
                cView.setTranslationY(mCurrentTransLationY);
                if(percent < MIN_SCALE){
                    percent = MIN_SCALE;
                }
                cView.setScaleX(percent);
                cView.setScaleY(percent);
                return true;
            }
        }else if (action == MotionEvent.ACTION_UP){
            Log.d(TAG, "action_up");
            if (isSwipingToClose){
                if (mCurrentTransLationY > MAX_EXIT_Y){
                    if (isShareElementMode){
                        if (dragCloseListener != null){
                            dragCloseListener.onClose();
                        }
                    }else {
                        exitWithTranslation(mCurrentTransLationY);
                    }
                }else {
                    resetCallBackAnimation();
                }
                isSwipingToClose = false;
                return true;
            }
        }else if (action == MotionEvent.ACTION_CANCEL){
            //滑动时来电话等导致操作取消
            if (isSwipingToClose){
                resetCallBackAnimation();
                isSwipingToClose = false;
                return true;
            }
        }
        return false;
    }

    /**
     * 重置坐标等数据
     */
    private void reset(MotionEvent event) {
        isSwipingToClose  = false;
        mLastX = event.getX();
        mLastY = event.getY();
        mLastRowX = event.getRawX();
        mLastRowY = event.getRawY();
        mLastTransLationX = 0;
        mLastTransLationY = 0;
    }

    private boolean isAnimationRunning = false;

    private void resetCallBackAnimation() {
        if (isAnimationRunning || mCurrentTransLationY == 0){
            return;
        }
        final float ratio = mCurrentTransLationX / mCurrentTransLationY;
        ValueAnimator animator = ValueAnimator.ofFloat(mCurrentTransLationY, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (isAnimationRunning){
                    mCurrentTransLationY = (float) animation.getAnimatedValue();
                    mCurrentTransLationX = ratio * mCurrentTransLationY;
                    mLastTransLationX = mCurrentTransLationX;
                    mLastTransLationY = mCurrentTransLationY;
                    updateChildView(mCurrentTransLationX, mCurrentTransLationY);
                }
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimationRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isAnimationRunning){
                    isAnimationRunning = false;
                    pView.getBackground().mutate().setAlpha(255);
                    mCurrentTransLationX = 0;
                    mCurrentTransLationY = 0;
                    if (dragCloseListener != null){
                        dragCloseListener.onCancel();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(100).start();
    }

    private void updateChildView(float mCurrentTranslationX, float mCurrentTranslationY) {
        cView.setTranslationY(mCurrentTranslationY);
        cView.setTranslationX(mCurrentTranslationX);
        float percent = 1 - Math.abs(mCurrentTranslationY/(MAX_EXIT_Y + cView.getHeight()));
        if (percent < minScale){
            percent = minScale;
        }
        cView.setScaleY(percent);
        cView.setScaleX(percent);
    }

    private void exitWithTranslation(float currentY) {
        int targetValue = currentY > 0? cView.getHeight() : -cView.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mCurrentTransLationY, targetValue);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateChildView(mCurrentTransLationX, (float) animation.getAnimatedValue());
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (dragCloseListener != null){
                    dragCloseListener.onClose();
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(100).start();
    }

    public interface DragCloseListener{
        /**
         * 是否拦截拖拽处理
         * @return
         */
        boolean intercept();

        /**
         * 开始滑动
         */
        void startDrag();

        /**
         * 取消
         */
        void onCancel();

        /**
         * 关闭页面
         */
        void onClose();
    }
}

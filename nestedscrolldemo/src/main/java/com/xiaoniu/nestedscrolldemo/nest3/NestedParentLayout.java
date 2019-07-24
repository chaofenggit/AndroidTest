package com.xiaoniu.nestedscrolldemo.nest3;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.xiaoniu.nestedscrolldemo.R;

import java.util.Arrays;

/**
 * @author xiaoniu
 * @date 2018/9/5.
 */

public class NestedParentLayout extends LinearLayout implements NestedScrollingParent{
    private static final String TAG = "NestedParentLayout";

    private int childOneHeight;
    private OverScroller mScroller;
    private ValueAnimator mOffsetAnimator;

    public NestedParentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mScroller = new OverScroller(context);
    }


    private View topView;
    private View centerView;
    private ViewPager viewPager;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        topView = findViewById(R.id.iv_top);
        centerView = findViewById(R.id.tv_center);
        viewPager = findViewById(R.id.viewpager);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        childOneHeight = topView.getHeight();
    }

    /**
     * 根据速度计算滚动动画持续时间
     * @param velocityY
     * @return
     */
    private int computeDuration(float velocityY) {
        final int distance;
        if (velocityY > 0) {
            distance = Math.abs(topView.getHeight() - getScrollY());
        } else {
            distance = Math.abs(topView.getHeight() - (topView.getHeight() - getScrollY()));
        }


        final int duration;
        velocityY = Math.abs(velocityY);
        if (velocityY > 0) {
            duration = 3 * Math.round(1000 * (distance / velocityY));
        } else {
            final float distanceRatio = (float) distance / getHeight();
            duration = (int) ((distanceRatio + 1) * 150);
        }

        return duration;

    }

    private void animateScroll(float velocityY, final int duration,boolean consumed) {
        final int currentOffset = getScrollY();
        final int topHeight = topView.getHeight();
        if (mOffsetAnimator == null) {
            mOffsetAnimator = new ValueAnimator();
            mOffsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (animation.getAnimatedValue() instanceof Integer) {
                        scrollTo(0, (Integer) animation.getAnimatedValue());
                    }
                }
            });
        } else {
            mOffsetAnimator.cancel();
        }
        mOffsetAnimator.setDuration(Math.min(duration, 200));

        if (velocityY >= 0) {
            mOffsetAnimator.setIntValues(currentOffset, topHeight);
            mOffsetAnimator.start();
        }else {
            //如果子View没有消耗down事件 那么就让自身滑倒0位置
            if(!consumed){
                mOffsetAnimator.setIntValues(currentOffset, 0);
                mOffsetAnimator.start();
            }

        }
    }
    /**
     * 有嵌套滑动到来了，问下该父View是否接受嵌套滑动
     *
     * @param child            嵌套滑动对应的父类的子类(因为嵌套滑动对于的父View不一定是一级就能找到的，可能挑了两级父View的父View，child的辈分>=target)
     * @param target           具体嵌套滑动的那个子类
     * @param nestedScrollAxes 支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return 是否接受该嵌套滑动
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
       return true;
    }

    /**
     * 该父View接受了嵌套滑动的请求该函数调用。onStartNestedScroll返回true该函数会被调用。
     * 参数和onStartNestedScroll一样
     */
    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
    }

    /**
     * 获取嵌套滑动的轴
     *
     * @see ViewCompat#SCROLL_AXIS_HORIZONTAL 垂直
     * @see ViewCompat#SCROLL_AXIS_VERTICAL 水平
     * @see ViewCompat#SCROLL_AXIS_NONE 都支持
     */
    @Override
    public int getNestedScrollAxes() {
        return 0;
    }

    /**
     * 停止嵌套滑动
     *
     * @param target 具体嵌套滑动的那个子类
     */
    @Override
    public void onStopNestedScroll(View target) {
//        mScrollingParentHelper.onStopNestedScroll(target);
    }

    /**
     * 嵌套滑动的子View在滑动之后报告过来的滑动情况
     *
     * @param target       具体嵌套滑动的那个子类
     * @param dxConsumed   水平方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dyConsumed   垂直方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dxUnconsumed 水平方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     * @param dyUnconsumed 垂直方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     */
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    /**
     * 在嵌套滑动的子View未滑动之前告诉过来的准备滑动的情况
     *
     * @param target   具体嵌套滑动的那个子类
     * @param dx       水平方向嵌套滑动的子View想要变化的距离
     * @param dy       垂直方向嵌套滑动的子View想要变化的距离
     * @param consumed 这个参数要我们在实现这个函数的时候指定，回头告诉子View当前父View消耗的距离
     *                 consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离 好让子view做出相应的调整
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.d(TAG, "onNestedPreScroll() called with: " + "dx = [" + dx + "], dy = [" + dy + "], consumed = [" + Arrays.toString(consumed) + "]");

        boolean hiddenTop = dy > 0 && getScrollY() < childOneHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop){
            scrollBy(0, -dy);
            consumed[1] = dy;
        }
    }

    /**
     * 嵌套滑动的子View在fling之后报告过来的fling情况
     *
     * @param target    具体嵌套滑动的那个子类
     * @param velocityX 水平方向速度
     * @param velocityY 垂直方向速度
     * @param consumed  子view是否fling了
     * @return true 父View是否消耗了fling
     */
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        //如果是recyclerView 根据判断第一个元素是哪个位置可以判断是否消耗
        //这里判断如果第一个元素的位置是大于TOP_CHILD_FLING_THRESHOLD的
        //认为已经被消耗，在animateScroll里不会对velocityY<0时做处理
        if (target instanceof RecyclerView && velocityY < 0) {
            final RecyclerView recyclerView = (RecyclerView) target;
            final View firstChild = recyclerView.getChildAt(0);
            final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
//            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD;
            consumed = childAdapterPosition > 3;
        }
        if (!consumed) {
            animateScroll(velocityY, computeDuration(0),consumed);
        } else {
            animateScroll(velocityY, computeDuration(velocityY),consumed);
        }
        return true;
    }

    /**
     * 在嵌套滑动的子View未fling之前告诉过来的准备fling的情况
     *
     * @param target    具体嵌套滑动的那个子类
     * @param velocityX 水平方向速度
     * @param velocityY 垂直方向速度
     * @return true 父View是否消耗了fling
     */
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    private boolean isScroll(int dy){
        if (dy != 0 && getScrollY() >= 0 && getScrollY() <= childOneHeight){
            return true;
        }
        return false;
    }

    @Override
    public void scrollTo(int x, int y) {

        if (y < 0)
        {
            y = 0;
        }
        if (y > childOneHeight)
        {
            y = childOneHeight;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }

//        if (y < 0){
//            y = 0;
//        }
//        if (y > childOneHeight){
//            y = childOneHeight;
//        }
//        Log.d("scrollTo", "Y = " + y);
//        super.scrollTo(x, y);
    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = getMeasuredHeight() - centerView.getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), topView.getMeasuredHeight() + centerView.getMeasuredHeight() + viewPager.getMeasuredHeight());

    }
}

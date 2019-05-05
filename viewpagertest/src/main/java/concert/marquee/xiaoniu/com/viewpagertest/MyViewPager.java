package concert.marquee.xiaoniu.com.viewpagertest;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author xiaoniu
 * @date 2019/4/22.
 */

public class MyViewPager extends ViewPager {

    /**
     * 是否可以跟着手指滑动
     */
    private boolean isCanScroll = true;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置是否可以跟随手指滑动
     * @param canScroll
     */
    public void setCanScroll(boolean canScroll) {
        isCanScroll = canScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.isCanScroll && super.onTouchEvent(ev);
    }
}

package com.xiaoniu.textswitcher;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author xiaoniu
 * @date 2018/12/14.
 */

public class CustomTextSwitcher extends View {

    private static final String TAG = "CustomTextSwitcher";

    /**
     * 控件的高度
     */
    private  int viewHeight;
    /**
     * 文字的高度
     */
    private int textHeight;
    private Paint paint;
    /**
     * 用来计算文字高度的默认值
     */
    private String defaultStr = "012345.678.9";
    /**
     * 上一次设置的文字
     */
    private String preText;
    /**
     * 当前设置的文字
     */
    private String currentText;
    /**
     * 文字滚动的距离
     */
    private float moveDistance;

    public CustomTextSwitcher(Context context) {
        super(context);
        init(context);
    }

    public CustomTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextSwitcher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = getMeasuredHeight();
    }


    private void init(Context context){
        //初始化画笔
        paint = new Paint();
        paint.setTextSize(dip2px(context, 24));
        paint.setAntiAlias(true);
        paint.setColor(context.getResources().getColor(R.color.CC_1A1B24));
        //计算文字高度
        Rect rect = new Rect();
        paint.getTextBounds(defaultStr, 0, defaultStr.length(), rect);
        textHeight = rect.height();
    }

    /**
     * 设置文字
     * @param text
     */
    public void setText(String text){
        if (TextUtils.isEmpty(text)){
            return;
        }
        //更新文字
        preText = currentText;
        currentText = text;

        refreshViewWidth();
        startAnimations();
    }

    /**
     * 滚动动画
     */
    private void startAnimations() {
        //最大值根据文字居中到消失的距离计算得来的
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, (viewHeight + textHeight) / 2.0f);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveDistance = (float) animation.getAnimatedValue();
                if (animation.getAnimatedFraction() == 1){
                    preText = "";
                    //刷新控件宽度
                    refreshViewWidth();
                }
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!TextUtils.isEmpty(preText)){
            canvas.drawText(preText, 0, viewHeight/2.0f  + textHeight/2.0f  - moveDistance, paint);
        }
        if (!TextUtils.isEmpty(currentText)){
            canvas.drawText(currentText, 0, viewHeight  + textHeight - moveDistance, paint);
        }
    }

    /**
     * 刷新控件的宽度
     * @return
     */
    private void refreshViewWidth( ){
        Rect rect = new Rect();
        paint.getTextBounds(currentText, 0, currentText.length(), rect);
        int currentTextWidth = rect.width();

        if (!TextUtils.isEmpty(preText)){
            paint.getTextBounds(preText, 0, preText.length(), rect);
        }
        int preTextWidth = rect.width();

        //刷新控件宽度
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = Math.max(currentTextWidth, preTextWidth) + 5;
        setLayoutParams(layoutParams);
    }

    /**
     * 获取当前正在展示的文字
     * @return
     */
    public String getCurrentText() {
        return currentText;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

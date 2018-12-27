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
    private String preTextStr;
    /**
     * 当前设置的文字
     */
    private String currentStr;
    /**
     * 文字滚动的距离
     */
    private float move;

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
        Log.d(TAG, "onMeasure height:" + viewHeight);
    }


    private void init(Context context){
        //初始化画笔
        paint = new Paint();
        paint.setTextSize(60);
        paint.setAntiAlias(true);
        paint.setColor(context.getResources().getColor(R.color.colorBlack));
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
        preTextStr = currentStr;
        this.currentStr = text;

        //计算当前文字和上次文字的宽度最大值
        Rect rect = new Rect();
        paint.getTextBounds(currentStr, 0, currentStr.length(), rect);
        int currentTextWidth = rect.width();

        if (!TextUtils.isEmpty(preTextStr)){
            paint.getTextBounds(preTextStr, 0, preTextStr.length(), rect);
        }
        int preTextWidth = rect.width();

        //刷新控件宽度
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = Math.max(currentTextWidth, preTextWidth) + 5;
        setLayoutParams(layoutParams);

        startAnimations();
    }

    /**
     * 滚动动画
     */
    private void startAnimations() {
        //最大值根据文字居中到消失的距离计算得来的
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, viewHeight / 2.0f + textHeight / 2.0f);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                    move = (float) animation.getAnimatedValue();
                    if (animation.getAnimatedFraction() == 1){
                        preTextStr = "";
                    }
                    postInvalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!TextUtils.isEmpty(preTextStr)){
            canvas.drawText(preTextStr, 0, viewHeight/2.0f  + textHeight/2.0f  - move, paint);
        }
        if (!TextUtils.isEmpty(currentStr)){
            canvas.drawText(currentStr, 0, viewHeight  + textHeight - move , paint);
        }
    }
}

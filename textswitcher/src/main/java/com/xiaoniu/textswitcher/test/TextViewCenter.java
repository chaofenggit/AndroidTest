package com.xiaoniu.textswitcher.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.altopay.lib.utils.ToolUtils;
import com.xiaoniu.textswitcher.R;

/**
 * @author xiaoniu
 * @date 2019/01/09.
 *
 * 测试自定义view中文字显示在中间
 */

public class TextViewCenter extends View {

    private static final String TAG = "TextViewCenter";

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

    public TextViewCenter(Context context) {
        super(context);
        init(context);
    }

    public TextViewCenter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextViewCenter(Context context, AttributeSet attrs, int defStyleAttr) {
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
        paint.setTextSize(ToolUtils.dip2px(context, 24));
        paint.setAntiAlias(true);
//        paint.setTextAlign(Paint.Align.LEFT);
        //计算文字高度
        Rect rect = new Rect();
        paint.getTextBounds(defaultStr, 0, defaultStr.length(), rect);
        textHeight = rect.height();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画背景
        canvas.drawColor(Color.GRAY);

        //第一种居中显示
        paint.setColor(Color.BLACK);
        canvas.drawText("居中显示的文字", 0, viewHeight/2.0f  + textHeight/2.0f , paint);

        //中间的横线
        paint.setColor(Color.RED);
        canvas.drawLine(0,viewHeight/2.0f, getWidth(), viewHeight/2.0f, paint);

        //第二种居中显示
        paint.setColor(Color.BLUE);
        canvas.drawText("居中显示的文字", 0, viewHeight/2.0f  + ToolUtils.getBaseLineDistance(paint), paint);
    }
}

package com.xiaoniu.reader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xiaoniu.reader.R;

/**
 * @author xiaoniu
 * @date 2018/7/20.
 */

public class AddBookView extends View {

    public AddBookView(Context context) {
        this(context, null);
    }

    public AddBookView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddBookView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private Paint paint = null;
    /**背景色*/
    private int bgColor;
    /**边框颜色*/
    private int strokeColor;
    /**边框宽度*/
    private int strokeWidth = 2;
    /**加号的粗*/
    private int lineWidth = 5;
    /**加号的一半的长度*/
    private int lineHalfLength = 15;

    private void initView(Context context) {
        paint = new Paint();
        bgColor = context.getResources().getColor(R.color.C_F6F7F7);
        strokeColor = context.getResources().getColor(R.color.C_E6E6E6);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY){
            int width = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(width, width*4/3);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景色
        canvas.drawColor(bgColor);
        //画边框
        paint.setColor(strokeColor);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        int width = getWidth();
        int height = getHeight();
        canvas.drawRect(0,0,width,height,paint);
        //画加号
        int centerX = width/2;
        int centerY = height/2;
        paint.setStrokeWidth(lineWidth);
        canvas.drawLine(centerX - lineHalfLength, centerY, centerX + lineHalfLength, centerY, paint);
        canvas.drawLine(centerX, centerY - lineHalfLength, centerX, centerY + lineHalfLength, paint);
    }
}

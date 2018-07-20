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
    private int bgColor;
    private int strokeColor;
    private void initView(Context context) {
        paint = new Paint();
        bgColor = context.getResources().getColor(R.color.C_F6F7F7);
        strokeColor = context.getResources().getColor(R.color.C_E6E6E6);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景色
        canvas.drawColor(bgColor);
        //画边框
        paint.setColor(strokeColor);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        int width = getWidth();
        int height = getHeight();
        canvas.drawRect(0,0,width,height,paint);
        //画加号
        int centerX = width/2;
        int centerY = height/2;
        paint.setStrokeWidth(5);
        canvas.drawLine(centerX - 10, centerY, centerX + 10, centerY, paint);
        canvas.drawLine(centerX, centerY - 10, centerX, centerY + 10, paint);
    }
}

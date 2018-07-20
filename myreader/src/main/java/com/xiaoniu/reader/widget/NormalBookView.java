package com.xiaoniu.reader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xiaoniu.reader.R;

/**
 * @author xiaoniu
 * @date 2018/7/20.
 */

public class NormalBookView extends View {

    public NormalBookView(Context context) {
        this(context, null);
    }

    public NormalBookView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NormalBookView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private Paint paint = null;
    private int bgColor;
    private int contentBgColor;
    private int strokeColor;
    private void initView(Context context) {
        paint = new Paint();
        bgColor = context.getResources().getColor(R.color.C_FFFFFF);
        contentBgColor = context.getResources().getColor(R.color.C_F6F7F7);
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
        //画小背景
        paint.setColor(contentBgColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(10,10,width - 10,height - 10,paint);

    }
}

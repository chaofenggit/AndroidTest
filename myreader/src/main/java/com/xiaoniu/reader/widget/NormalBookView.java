package com.xiaoniu.reader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
    /**背景色*/
    private int bgColor;
    /**中间的背景色*/
    private int contentBgColor;
    /**边框颜色*/
    private int strokeColor;
    /**边框宽度*/
    private int strokeWidth = 2;
    /**内容与边界的边距*/
    private int contentMargin = 10;
    /**文字与内容区域的边框的边距*/
    private int textMargin = 10;
    /**文字与边框的边距*/
    private int textBorderLength = 0;
    /**文字高度*/
    private int textHeight = 0;

    /**书名*/
    private String finalBookName = "";
    /***
     * 字体大小
     */
    private int textSize = 25;
    /**控件宽度*/
    private int width;

    private void initView(Context context) {
        paint = new Paint();
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        bgColor = context.getResources().getColor(R.color.C_FFFFFF);
        contentBgColor = context.getResources().getColor(R.color.C_F6F7F7);
        strokeColor = context.getResources().getColor(R.color.C_E6E6E6);
    }

    /***
     * 设置书名
     * @param bookName
     */
    public void setBookName(final String bookName) {
        if (TextUtils.isEmpty(bookName)){
            this.finalBookName = bookName;
            return;
        }
        //列表重复使用空间，恢复之前的数据
        textMargin = 10;
        textBorderLength = 0;
        textHeight = 0;

        //防止width还有赋值就处理book name
        post(new Runnable() {
            @Override
            public void run() {
                dealBookName(bookName);
            }
        });
    }

    /***
     * 处理书名
     * @param bookName
     */
    private void dealBookName(String bookName){
        bookName = bookName.trim();

        Rect bound = new Rect();
        int end = bookName.length()-1;
        paint.getTextBounds(bookName, 0, end, bound);
        textHeight = bound.height();
        textMargin = textHeight/2 + textMargin;

        textBorderLength = strokeWidth + contentMargin + textMargin;
        /**用于展示文字的最大宽度*/
        int maxUsableWidth = width - textBorderLength * 2;
        while (maxUsableWidth < bound.width()){
            --end;
            if (end <= 0){
                break;
            }
            paint.getTextBounds(bookName, 0, end, bound);
        }

        this.finalBookName = bookName.substring(0, end + 1);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY){
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
        width = getWidth();
        int height = getHeight();
        canvas.drawRect(0,0,width,height,paint);
        //画内容背景
        paint.setColor(contentBgColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(contentMargin,contentMargin,width - contentMargin,height - contentMargin,paint);
        if (TextUtils.isEmpty(finalBookName)){
            return;
        }
        //画书名
        paint.setColor(Color.BLACK);
        canvas.drawText(finalBookName, textBorderLength - textHeight/2, textBorderLength + textHeight/2, paint);
    }

}

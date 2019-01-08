package com.xiaoniu.keybordview.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.xiaoniu.keybordview.R;

import java.util.List;

/**
 * @author xiaoniu
 * @date 2019/1/3.
 */

public class DigitKeyBoardView extends KeyboardView {

    public DigitKeyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DigitKeyBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Keyboard keyboard = null;
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        keyboard = getKeyboard();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (keyboard == null){
            return;
        }
        List<Keyboard.Key> keys = keyboard.getKeys();

        if (keys == null){
            return;
        }
        for (int i = 0; i < keys.size(); i++) {
            Keyboard.Key key = keys.get(i);
            if (key.codes[0] == -3){
                Paint paint = new Paint();
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(getResources().getColor(R.color.colorAccent));
                paint.setAntiAlias(true);
                paint.setTextSize(20 * 2);
                Rect rect = new Rect(key.x, key.y, key.x + key.width, key.y + key.height);
                Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                int baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("000", rect.centerX(), baseline, paint);
            }
        }

    }
}

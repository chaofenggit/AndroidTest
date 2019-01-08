package com.xiaoniu.keybordview.keyboard;

import android.annotation.SuppressLint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.xiaoniu.keybordview.R;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author xiaoniu
 * @date 2019/1/8.
 */

public class KeyBoardUtil {
    private static final String TAG = "KeyBoardUtil";

    private EditText editText;
    private KeyboardView keyboardView;

    public KeyBoardUtil(EditText editText, KeyboardView keyboardView) {
        this.editText = editText;
        this.keyboardView = keyboardView;
        init();
    }

    private void init() {
        onHiddenSystemKeyboard(editText);
        //初始化按键
        final Keyboard keyboard = new Keyboard(keyboardView.getContext(), R.xml.keyboard_number2);
        //键盘设置按键
        keyboardView.setKeyboard(keyboard);
        //键盘添加按钮回调
        keyboardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {
            }

            @Override
            public void onRelease(int primaryCode) {
            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                Log.d(TAG, "onKey primaryCode = " + primaryCode);
                if (primaryCode == -1){
                    //delete
                    String s = editText.getText().toString();
                    if (s.length() > 0){
                        String substring = s.substring(0, s.length() - 1);
                        editText.setText(substring);
                    }
                }else if (primaryCode == -2){
                    //clear
                    editText.setText("");
                }else if(primaryCode == -3){
                    //000
                    editText.setText(editText.getText().toString() + "000");
                }else {
                    List<Keyboard.Key> keys = keyboard.getKeys();
                    for (Keyboard.Key key: keys) {
                        if (key.codes[0] == primaryCode){
                            editText.setText(editText.getText().toString() + key.label);
                            break;
                        }
                    }
                }
                editText.setSelection(editText.getText().toString().length());
            }

            @Override
            public void onText(CharSequence text) {
            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        });
    }

    private void onHiddenSystemKeyboard(EditText edit) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {// 4.0以下
            edit.setInputType(InputType.TYPE_NULL);
        } else {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus = cls.getMethod(
                        "setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(false);
                setShowSoftInputOnFocus.invoke(edit, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

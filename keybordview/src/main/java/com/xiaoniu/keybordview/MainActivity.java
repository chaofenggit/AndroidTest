package com.xiaoniu.keybordview;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.xiaoniu.keybordview.keyboard.DigitKeyBoardView;

import java.util.List;

/**
 * 测试自定义keyBoard
 * @author xiaoniu
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private DigitKeyBoardView digitKeyBoardView;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.et);
        digitKeyBoardView = findViewById(R.id.key_board);

        final Keyboard keyboard = new Keyboard(this, R.xml.keyboard_number);
        digitKeyBoardView.setKeyboard(keyboard);
        digitKeyBoardView.setEnabled(true);
        digitKeyBoardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {
                Log.d(TAG, "onPress primaryCode = " + primaryCode);
            }

            @Override
            public void onRelease(int primaryCode) {
                Log.d(TAG, "onRelease primaryCode = " + primaryCode);
            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                Log.d(TAG, "onKey primaryCode = " + primaryCode);
                List<Keyboard.Key> keys = keyboard.getKeys();
                for (Keyboard.Key key: keys) {
                    if (key.codes[0] == primaryCode){
                        et.setText(et.getText().toString() + key.label);
                        et.setSelection(et.getText().toString().length());
                        break;
                    }
                }

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
}

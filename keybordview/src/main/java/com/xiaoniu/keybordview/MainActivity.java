package com.xiaoniu.keybordview;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.xiaoniu.keybordview.keyboard.DigitKeyBoardView;
import com.xiaoniu.keybordview.keyboard.KeyBoardUtil;

import java.util.List;

/**
 * 测试自定义keyBoard
 * @author xiaoniu
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.et);
        new KeyBoardUtil(et, (DigitKeyBoardView)findViewById(R.id.key_board));
    }
}

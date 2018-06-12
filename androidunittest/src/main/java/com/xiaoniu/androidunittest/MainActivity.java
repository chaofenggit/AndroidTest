package com.xiaoniu.androidunittest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView tv_tip;
    private EditText et_input;
    private Button btn_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_tip = findViewById(R.id.tv_tip);
        et_input = findViewById(R.id.et_input);
        btn_click = findViewById(R.id.btn_click);
        btn_click.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String inputText = et_input.getText().toString();
        tv_tip.setText(inputText);
    }
}

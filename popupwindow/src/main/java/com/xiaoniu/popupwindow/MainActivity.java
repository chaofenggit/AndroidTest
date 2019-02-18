package com.xiaoniu.popupwindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private LinearLayout ll_container;
    private TextView tv_phone_number;
    private ImageView iv_test;

    private PhoneListPopUpWindow popUpWindow;
    private ArrayList<String> phoneList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        ll_container = findViewById(R.id.ll_container);
        tv_phone_number = findViewById(R.id.tv_phone_number);
        iv_test = findViewById(R.id.iv_test);
    }

    private void initData(){
        popUpWindow = new PhoneListPopUpWindow(MainActivity.this);
        phoneList = new ArrayList<>();
        phoneList.add("812345671");
        phoneList.add("812345672");
        phoneList.add("812345673");
        phoneList.add("812345674");
        phoneList.add("812345675");
        phoneList.add("812345676");
        phoneList.add("812345677");
        phoneList.add("812345678");
        popUpWindow.setData(phoneList);
        popUpWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_phone_number.setText(phoneList.get(position));
                popUpWindow.dismiss();
            }
        });
        //添加默认值
        tv_phone_number.setText(phoneList.get(0));

        iv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popUpWindow.isShowing()){
                    popUpWindow.dismiss();
                }else {
                    popUpWindow.showAtLocation(ll_container);
                }
            }
        });
    }
}

package com.altopay.cropimage.wheelview;

import android.app.Activity;
import android.os.Bundle;

import com.altopay.cropimage.R;
import com.altopay.cropimage.wheelview.wheelview.WheelView;
import com.altopay.lib.utils.ToastUtil;

import java.util.Arrays;

/**
 * @author zhaohe@iapppay.com
 * @date 2018/5/26.
 */

public class WheelViewActivity extends Activity {
    WheelView wheelView1 = null;
    WheelView wheelView2 = null;
    String [] years = {"2001", "2002", "2003", "2004", "2005", "2006","2003", "2004", "2005", "2006","2003", "2004", "2005", "2006"};
    String [] months = {"Julyssss", "Julyssss","Julyssss","Julyssss","Julyssss","Julyssss","Julyssss","Julyssss","Julyssss","Julyssss","Julyssss","Julyssss","Julyssss"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wheel);
        wheelView1 = findViewById(R.id.wheel1);
        wheelView1.setItems(Arrays.asList(years), 0);
        wheelView1.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {
                ToastUtil.showToastShort(WheelViewActivity.this, item);
            }
        });

        wheelView2 = findViewById(R.id.wheel2);
        wheelView2.setItems(Arrays.asList(months), 0);
        wheelView2.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {
                ToastUtil.showToastShort(WheelViewActivity.this, item);
            }
        });

    }



}

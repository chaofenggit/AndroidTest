package com.xiaoniu.reader.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xiaoniu.reader.R;
import com.xiaoniu.reader.adapter.SelectBookAdapter;
import com.xiaoniu.reader.fragment.AutoSelectBookFragment;
import com.xiaoniu.reader.fragment.SdCardBookFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择图书页面
 * @author xiaoniu
 */
public class SelectBookActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TextView tv_auto_select, tv_sd_card_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_book);

        initView();
        initData();
    }

    private void initView() {
        toolbar = findViewById(R.id.titleBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewPager = findViewById(R.id.viewPager);
        tv_auto_select = findViewById(R.id.tv_auto_select);
        tv_sd_card_select = findViewById(R.id.tv_sd_card_select);
        tv_auto_select.setOnClickListener(this);
        tv_sd_card_select.setOnClickListener(this);
    }

    private void initData(){
        List<Fragment> fragments = new ArrayList<>(2);
        fragments.add(new AutoSelectBookFragment());
        fragments.add(new SdCardBookFragment());
        viewPager.setAdapter(new SelectBookAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changePageSelect(position);
            }
        });
    }

    /**
     * 修改页面选择
     * @param position
     */
    private void changePageSelect(int position){
        if (position == 0){
            tv_auto_select.setTextColor(getResources().getColor(R.color.C_EF4145));
            tv_sd_card_select.setTextColor(getResources().getColor(R.color.C_4A4A4A));
        }else {
            tv_auto_select.setTextColor(getResources().getColor(R.color.C_4A4A4A));
            tv_sd_card_select.setTextColor(getResources().getColor(R.color.C_EF4145));
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_auto_select){
            viewPager.setCurrentItem(0, true);
        }else if (id == R.id.tv_sd_card_select){
            viewPager.setCurrentItem(1, true);
        }
    }
}

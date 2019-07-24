package com.xiaoniu.nestedscrolldemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoniu.nestedscrolldemo.R;
import com.xiaoniu.nestedscrolldemo.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoniu
 * @date 2018/9/25.
 */

public class FragmentThree extends Fragment {

    private ViewPager viewpager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        viewpager = view.findViewById(R.id.viewpager);
        List<Fragment> list = new ArrayList<>(3);
        list.add(new FragmentList());
        list.add(new FragmentList());
        list.add(new FragmentList());
        viewpager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), list));
    }

}

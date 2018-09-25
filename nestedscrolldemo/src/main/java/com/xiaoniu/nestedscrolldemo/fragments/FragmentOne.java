package com.xiaoniu.nestedscrolldemo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoniu.nestedscrolldemo.R;

/**
 * @author xiaoniu
 * @date 2018/9/25.
 */

public class FragmentOne extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment1, container, false);
    }
}

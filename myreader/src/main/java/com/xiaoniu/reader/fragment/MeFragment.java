package com.xiaoniu.reader.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoniu.reader.R;

/**
 * @author xiaoniu
 * @date 2018/7/19.
 */

public class MeFragment extends Fragment {
    public static final String TAG = "MeFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_me, container, false);
        return mainView;
    }


}

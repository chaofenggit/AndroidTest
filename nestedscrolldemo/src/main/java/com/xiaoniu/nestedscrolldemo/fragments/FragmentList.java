package com.xiaoniu.nestedscrolldemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoniu.nestedscrolldemo.R;
import com.xiaoniu.nestedscrolldemo.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoniu
 * @date 2018/9/25.
 */

public class FragmentList extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        List<String> list = new ArrayList<>(32);
        for (int i =0; i < 32; i++){
            list.add("我是条目：" + i);
        }
        recyclerView = view.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ListAdapter(list));
    }

}

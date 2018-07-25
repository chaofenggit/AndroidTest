package com.xiaoniu.reader.fragment;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoniu.reader.R;
import com.xiaoniu.reader.adapter.MixFileListAdapter;
import com.xiaoniu.reader.bean.MixFile;
import com.xiaoniu.reader.bean.TxtFile;
import com.xiaoniu.reader.database.FileDBManager;
import com.xiaoniu.reader.database.ReaderSqlHelper;
import com.xiaoniu.reader.listener.MixFileListListener;
import com.xiaoniu.reader.manager.BookChangeManager;
import com.xiaoniu.reader.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoniu
 * @date 2018/7/23.
 */

public class SdCardBookFragment extends Fragment implements View.OnClickListener {

    private RecyclerView file_list = null;
    private TextView tv_filepath, tv_back_last_level;
    private LinearLayout ll_bottom_area;
    private TextView tv_select_all, tv_add_bookrack;

    private MixFileListAdapter mixFileListAdapter = null;
    private List<MixFile> mixFiles = new ArrayList<>();
    private File parentFile = null;
    private FileUtil fileUtil = null;
    private FileDBManager fileDBManager = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sd_card_book, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View mainView) {
        file_list = mainView.findViewById(R.id.rv_file_list);
        file_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        mixFileListAdapter = new MixFileListAdapter(getActivity(), mixFiles);
        mixFileListAdapter.setListListener(new ItemClickListener());
        file_list.setAdapter(mixFileListAdapter);

        tv_filepath = mainView.findViewById(R.id.tv_filepath);
        tv_back_last_level = mainView.findViewById(R.id.tv_back_last_level);
        tv_back_last_level.setOnClickListener(this);

        ll_bottom_area = mainView.findViewById(R.id.ll_bottom_area);
        tv_select_all = mainView.findViewById(R.id.tv_select_all);
        tv_add_bookrack = mainView.findViewById(R.id.tv_add_bookrack);
        tv_select_all.setOnClickListener(this);
        tv_add_bookrack.setOnClickListener(this);
    }

    private void initData() {
        if (fileUtil == null){
            fileUtil = new FileUtil();
            parentFile = fileUtil.getFile();
        }
        getSubFiles();
    }

    /**
     * 获取文件的子目录
     */
    private void getSubFiles(){
        fileUtil.getSubFiles(mixFiles, parentFile);
        mixFileListAdapter.notifyDataSetChanged();
        tv_filepath.setText(parentFile.getAbsolutePath());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tv_back_last_level:
                backLastLevel();
                break;
            case R.id.tv_select_all:
                selectAll();
                break;
            case R.id.tv_add_bookrack:
                addToBookRack();
                break;
            default:
                break;
        }
    }

    /**
     * 返回上一级
     */
    private void backLastLevel(){
        if (TextUtils.equals(parentFile.getAbsolutePath(), fileUtil.getFile().getAbsolutePath())){
            getActivity().finish();
        }else {
            parentFile = parentFile.getParentFile();
            if (parentFile == null || !parentFile.exists()){
                getActivity().finish();
            }else {
                getSubFiles();
            }
        }
    }

    /***
     * 选中所有文件
     */
    private void selectAll(){
        mixFileListAdapter.selectAll();
        mixFileListAdapter.notifyDataSetChanged();
    }

    /**
     * 把选中的图书添加到书架
     */
    private void addToBookRack(){
        if (mixFileListAdapter.getSelectedSize() <= 0){
            return;
        }
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("处理中");
        dialog.setCancelable(false);
        dialog.show();
        if (fileDBManager == null){
            fileDBManager = new FileDBManager(getActivity());
        }
        List<MixFile> selectedFiles = mixFileListAdapter.getSelectedFiles();
        for (MixFile mixFile:selectedFiles) {
            ContentValues values = new ContentValues();
            values.put(ReaderSqlHelper.NAME, mixFile.getName());
            values.put(ReaderSqlHelper.PATH, mixFile.getPath());
            fileDBManager.insert(values);
        }
        dialog.dismiss();
        BookChangeManager.notifyAllObserver();
        getActivity().finish();
    }


    private class ItemClickListener implements MixFileListListener {

        @Override
        public void onItemClick(int position, MixFile mixFile) {
            parentFile = new File(mixFile.getPath());
            mixFileListAdapter.selectClear();
            ll_bottom_area.setVisibility(View.GONE);
            getSubFiles();
        }

        @Override
        public void onItemChecked(int position, MixFile mixFile) {
            if (mixFileListAdapter.getSelectedSize() > 0){
                ll_bottom_area.setVisibility(View.VISIBLE);
            }else {
                ll_bottom_area.setVisibility(View.GONE);
            }
            mixFileListAdapter.notifyDataSetChanged();
        }
    }
}

package com.xiaoniu.reader.fragment;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoniu.reader.R;
import com.xiaoniu.reader.adapter.TxtFileListAdapter;
import com.xiaoniu.reader.bean.TxtFile;
import com.xiaoniu.reader.database.FileDBManager;
import com.xiaoniu.reader.database.ReaderSqlHelper;
import com.xiaoniu.reader.listener.FileUtilListener;
import com.xiaoniu.reader.listener.OnItemClickListener;
import com.xiaoniu.reader.manager.BookChangeManager;
import com.xiaoniu.reader.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoniu
 * @date 2018/7/23.
 */

public class AutoSelectBookFragment extends Fragment implements View.OnClickListener {

    private SwipeRefreshLayout refreshLayout = null;
    private RecyclerView recyclerView = null;
    private LinearLayout ll_bottom_area;
    private TextView tv_select_all,tv_add_bookrack;

    private TxtFileListAdapter fileListAdapter;
    private List<TxtFile> fileList;
    private FileUtil fileUtil = null;

    private FileDBManager fileDBManager = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auto_select_book, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View mainView) {
        refreshLayout = mainView.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.C_EF4145));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTxtFileList();
            }
        });
        recyclerView = mainView.findViewById(R.id.rv_file_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ll_bottom_area = mainView.findViewById(R.id.ll_bottom_area);

        tv_select_all = mainView.findViewById(R.id.tv_select_all);
        tv_add_bookrack = mainView.findViewById(R.id.tv_add_bookrack);
        tv_select_all.setOnClickListener(this);
        tv_add_bookrack.setOnClickListener(this);

    }

    private void initData(){

        fileList = new ArrayList<>();
        fileListAdapter = new TxtFileListAdapter(fileList);
        recyclerView.setAdapter(fileListAdapter);
        fileListAdapter.setListListener(new TxtListListener());
        refreshLayout.setRefreshing(true);
        getTxtFileList();
    }

    /**
     * 获取txt文件列表
     */
    private void getTxtFileList(){
        ll_bottom_area.setVisibility(View.GONE);
       if (fileUtil == null){
            fileUtil = new FileUtil();
            fileUtil.setCallBack(new FileUtilCallBack());
        }
        fileUtil.filterTxtFile();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_select_all){
            selectAll();
        }else if (id == R.id.tv_add_bookrack){
            addToBookRack();
        }
    }

    /***
     * 选中所有文件
     */
    private void selectAll(){
       fileListAdapter.selectAll();
       fileListAdapter.notifyDataSetChanged();
    }

    /**
     * 把选中的图书添加到书架
     */
    private void addToBookRack(){
        if (fileListAdapter.getSelectedSize() <= 0){
            return;
        }
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("处理中");
        dialog.setCancelable(false);
        dialog.show();
        if (fileDBManager == null){
            fileDBManager = new FileDBManager(getActivity());
        }
        List<TxtFile> selectedFiles = fileListAdapter.getSelectedFiles();
        for (TxtFile txtFile:selectedFiles) {
            ContentValues values = new ContentValues();
            values.put(ReaderSqlHelper.NAME, txtFile.getName());
            values.put(ReaderSqlHelper.PATH, txtFile.getPath());
            fileDBManager.insert(values);
        }
        dialog.dismiss();
        BookChangeManager.notifyAllObserver();
        getActivity().finish();
    }

    private class FileUtilCallBack implements FileUtilListener {

        @Override
        public void complete(List<TxtFile> files) {
            refreshLayout.setRefreshing(false);
            fileList.clear();
            fileList.addAll(files);
            fileListAdapter.notifyDataSetChanged();
        }
    }

    private class TxtListListener implements OnItemClickListener {

        @Override
        public void onItemClick(int position) {
            if (fileListAdapter.getSelectedSize() > 0){
                ll_bottom_area.setVisibility(View.VISIBLE);
            }else {
                ll_bottom_area.setVisibility(View.GONE);
            }
            fileListAdapter.notifyDataSetChanged();
        }
    }

}

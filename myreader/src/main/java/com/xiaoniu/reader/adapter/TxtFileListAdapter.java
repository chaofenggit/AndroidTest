package com.xiaoniu.reader.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xiaoniu.reader.R;
import com.xiaoniu.reader.bean.TxtFile;
import com.xiaoniu.reader.listener.OnItemClickListener;
import com.xiaoniu.reader.utils.ToolUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoniu
 * @date 2018/7/23.
 */

public class TxtFileListAdapter extends RecyclerView.Adapter<TxtFileListAdapter.TetHolder> implements View.OnClickListener {
    /**文件列表*/
    private List<TxtFile> fileList = null;
    /**选中的文件列表*/
    private List<TxtFile> selectedFiles = null;

    private OnItemClickListener listListener = null;

    public TxtFileListAdapter(List<TxtFile> fileList) {
        this.fileList = fileList;
        selectedFiles = new ArrayList<>();
    }

    public void setListListener(OnItemClickListener listListener) {
        this.listListener = listListener;
    }

    @NonNull
    @Override
    public TetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_txt_file_item, parent, false);
        view.setOnClickListener(this);
        return new TetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TetHolder holder, int position) {
        TxtFile textFile = fileList.get(position);
        holder.fileName.setText(textFile.getName());
        holder.fileSize.setText(ToolUtil.formatFileSize(textFile.getSize()));
        holder.fileModifyTime.setText(ToolUtil.formatTime(textFile.getModifyTime()));
        holder.fileSelected.setChecked(textFile.isSelected());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        TxtFile txtFile = fileList.get(position);
        if (selectedFiles.contains(txtFile)){
            selectedFiles.remove(txtFile);
        }else {
            selectedFiles.add(txtFile);
        }
        txtFile.setSelected(!txtFile.isSelected());
        if (listListener != null){
            listListener.onItemClick(position);
        }
    }

    public class TetHolder extends RecyclerView.ViewHolder {
        public TextView fileName;
        public TextView fileSize;
        public TextView fileModifyTime;
        public CheckBox fileSelected;

        public TetHolder(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.tv_file_name);
            fileSize = itemView.findViewById(R.id.tv_file_size);
            fileModifyTime = itemView.findViewById(R.id.tv_file_modify_time);
            fileSelected = itemView.findViewById(R.id.cb_file_selected);
        }
    }

    /**
     * 获取选中的文件列表
     * @return
     */
    public List<TxtFile> getSelectedFiles() {
        return selectedFiles;
    }

    /***
     * 获取选中文件列表的长度
     */
    public int getSelectedSize(){
       if (selectedFiles == null || selectedFiles.size() == 0){
           return 0;
       }
       return selectedFiles.size();
    }

    /**
     * 选中所有的文件
     */
    public void selectAll(){
        for (int i = 0; i < fileList.size(); i++) {
            fileList.get(i).setSelected(true);
        }
        selectedFiles.clear();
        selectedFiles.addAll(fileList);
    }

}

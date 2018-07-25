package com.xiaoniu.reader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xiaoniu.reader.R;
import com.xiaoniu.reader.bean.MixFile;
import com.xiaoniu.reader.listener.MixFileListListener;
import com.xiaoniu.reader.listener.OnItemClickListener;
import com.xiaoniu.reader.utils.ToolUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoniu
 * @date 2018/7/23.
 *
 * 文件夹和文件的混合
 */

public class MixFileListAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context = null;

    /**文件列表*/
    private List<MixFile> fileList = null;
    /**选中的文件列表*/
    private List<MixFile> selectedFiles = null;

    private MixFileListListener listListener = null;

    public MixFileListAdapter(Context context, List<MixFile> fileList) {
        this.context = context;

        this.fileList = fileList;
        selectedFiles = new ArrayList<>();
    }

    public void setListListener(MixFileListListener listListener) {
        this.listListener = listListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MixFile.TYPE_FILE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_txt_file_item, parent, false);
            view.setOnClickListener(this);
            return new TxtHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_folder_item, parent, false);
            view.setOnClickListener(this);
            return new FolderHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return fileList.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MixFile mixFile = fileList.get(position);
        if (holder instanceof TxtHolder){
            TxtHolder txtHolder = (TxtHolder)holder;
            txtHolder.fileName.setText(mixFile.getName());
            txtHolder.fileSize.setText(ToolUtil.formatFileSize(mixFile.getSize()));
            txtHolder.fileModifyTime.setText(ToolUtil.formatTime(mixFile.getModifyTime()));
            txtHolder.fileSelected.setChecked(mixFile.isSelected());
            if (!mixFile.getCanSelect()){
                txtHolder.fileSelected.setEnabled(false);
            }else {
                txtHolder.fileSelected.setEnabled(true);
            }
        }else if (holder instanceof FolderHolder){
            FolderHolder folderHolder = (FolderHolder)holder;
            folderHolder.tv_folder_name.setText(mixFile.getName());
            folderHolder.tv_sub_count.setText(context.getString(R.string.file_sub_count, mixFile.getChildFileCount()));
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    @Override
    public void onClick(View v) {

        if (listListener == null){
            return;
        }
        int position = (int) v.getTag();
        MixFile mixFile = fileList.get(position);
        if (mixFile.isFolder()) {
            listListener.onItemClick(position, mixFile);
        }else {
            if (!mixFile.getCanSelect()){
                return;
            }
            if (selectedFiles.contains(mixFile)) {
                selectedFiles.remove(mixFile);
            } else {
                selectedFiles.add(mixFile);
            }
            mixFile.setSelected(!mixFile.isSelected());
            listListener.onItemChecked(position, mixFile);
        }
    }

    public class TxtHolder extends RecyclerView.ViewHolder {
        public TextView fileName;
        public TextView fileSize;
        public TextView fileModifyTime;
        public CheckBox fileSelected;

        public TxtHolder(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.tv_file_name);
            fileSize = itemView.findViewById(R.id.tv_file_size);
            fileModifyTime = itemView.findViewById(R.id.tv_file_modify_time);
            fileSelected = itemView.findViewById(R.id.cb_file_selected);
        }
    }

    public class FolderHolder extends RecyclerView.ViewHolder {
        public TextView tv_folder_name;
        public TextView tv_sub_count;

        public FolderHolder(View itemView) {
            super(itemView);
            tv_folder_name = itemView.findViewById(R.id.tv_folder_name);
            tv_sub_count = itemView.findViewById(R.id.tv_sub_count);
        }
    }

    /**
     * 获取选中的文件列表
     * @return
     */
    public List<MixFile> getSelectedFiles() {
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
        selectedFiles.clear();
        MixFile mixFile = null;
        for (int i = 0; i < fileList.size(); i++) {
            mixFile = fileList.get(i);
            if (mixFile.getCanSelect()){
                mixFile.setSelected(true);
                selectedFiles.add(mixFile);
            }
        }
    }
    /**
     * 清理选中所有的文件
     */
    public void selectClear(){
        selectedFiles.clear();
    }

}

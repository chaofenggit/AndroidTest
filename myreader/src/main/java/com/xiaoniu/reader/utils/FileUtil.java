package com.xiaoniu.reader.utils;

import android.os.AsyncTask;
import android.os.Environment;

import com.xiaoniu.reader.bean.MixFile;
import com.xiaoniu.reader.bean.TxtFile;
import com.xiaoniu.reader.listener.FileUtilListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoniu
 * @date 2018/7/23.
 */

public class FileUtil {

    private File file;
    private FileUtilListener listener = null;

    public FileUtil() {
//        file = new File("sdcard/");
        file = Environment.getExternalStorageDirectory();
    }

    public FileUtil(File file) {
        this.file = file;
    }

    public FileUtil(String filePath) {
        this.file = new File(filePath);
    }

    public FileUtil setCallBack(FileUtilListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 过滤出文件夹下的所有的txt文件
     */
    public void filterTxtFile(){
        new FileAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 异步任务获取件夹下的所有的txt文件
     */
    private class FileAsyncTask extends AsyncTask<Void, Void, Void>{
        private List<TxtFile> list = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            getList(file);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (listener != null){
                listener.complete(list);
            }
        }

        /**
         * 递归
         * @param file
         */
        private void getList(File file){
            if (file == null || !file.exists()){
                return;
            }
            if (file.isFile() && file.getName().endsWith(Constants.FORMAT_TXT)){
                list.add(new TxtFile(file.getName(), file.getAbsolutePath(), file.length(), file.lastModified(), false));
            }else if (file.isDirectory()){
                File[] files = file.listFiles();
                for (File subFile: files) {
                    getList(subFile);
                }
            }

        }

    }

    public File getFile() {
        return file;
    }

 /**
     * 获取文件下的子文件列表
     * @param parent
     */
    public List<MixFile> getSubFiles(List<MixFile> list, File parent){
        list.clear();
        if (parent == null || !parent.isDirectory()){
            return list;
        }

        File[] files = parent.listFiles();
        MixFile mixFile = null;
        for (File file : files) {
            if (file.isFile()){
                mixFile = new MixFile(file.getName(), file.getAbsolutePath(), file.length(), file.lastModified(), false);
            }else {
                mixFile = new MixFile(file.getName(), file.getAbsolutePath(), file.list().length);
            }
            list.add(mixFile);
        }
        return list;
    }

}

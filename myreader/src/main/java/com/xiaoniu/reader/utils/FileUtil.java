package com.xiaoniu.reader.utils;

import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;

import com.xiaoniu.reader.bean.MixFile;
import com.xiaoniu.reader.bean.TxtFile;
import com.xiaoniu.reader.listener.FileUtilListener;
import com.xiaoniu.reader.listener.ReadFileListener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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

    /**
     * 读取文件内容
     * @param file
     */
    public void readFileContent(File file, ReadFileListener listener){
        new ReadFileTask(file, listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class ReadFileTask extends AsyncTask<Void, Void, String>{

        private ReadFileListener listener;
        private File bookFile;

        public ReadFileTask(File bookFile, ReadFileListener listener) {
            this.bookFile = bookFile;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... voids) {
            BufferedReader reader = null;
            try {
                FileInputStream inputStream = new FileInputStream(bookFile);
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                byte[] format = new byte[3];
                bis.mark(4);
                bis.read(format);
                bis.reset();
                if (format[0] == (byte) 0xEF && format[1] == (byte) 0xBB && format[2] == (byte) 0xBF) {
                    reader = new BufferedReader(new InputStreamReader(bis, "utf-8"));
                } else if (format[0] == (byte) 0xFF && format[1] == (byte) 0xFE) {
                    reader = new BufferedReader( new InputStreamReader(bis, "unicode"));
                } else if (format[0] == (byte) 0xFE && format[1] == (byte) 0xFF) {
                    reader = new BufferedReader(new InputStreamReader(bis,  "utf-16be"));
                } else if (format[0] == (byte) 0xFF && format[1] == (byte) 0xFF) {
                    reader = new BufferedReader(new InputStreamReader(bis, "utf-16le"));
                } else {
                    reader = new BufferedReader(new InputStreamReader(bis, "GBK"));
                }
                StringBuilder sb = new StringBuilder();
                String str = reader.readLine();
                while (!TextUtils.isEmpty(str)){
                    sb.append(str);
                    str = reader.readLine();
                }
                inputStream.close();
                bis.close();
                reader.close();
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (listener != null){
                listener.fileContent(s);
            }
        }
    }

}

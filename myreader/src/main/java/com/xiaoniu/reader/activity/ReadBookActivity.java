package com.xiaoniu.reader.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.xiaoniu.reader.R;
import com.xiaoniu.reader.database.FileDBManager;
import com.xiaoniu.reader.database.ReaderSqlHelper;
import com.xiaoniu.reader.listener.ReadFileListener;
import com.xiaoniu.reader.manager.BookChangeManager;
import com.xiaoniu.reader.utils.Constants;
import com.xiaoniu.reader.utils.FileUtil;
import com.xiaoniu.reader.utils.ToastUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author xiaoniu
 * 读书activity
 */
public class ReadBookActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tv_book;

    private File bookFile = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        setContentView(R.layout.activity_read_book);
        initView();
        initData();
    }

    private void initIntent() {
        Intent intent = getIntent();
        String bookPath = intent.getStringExtra(Constants.BOOK_PATH);
        if (!TextUtils.isEmpty(bookPath)){
            bookFile = new File(bookPath);
            if (bookFile == null || !bookFile.exists()){
                ToastUtil.showToast(ReadBookActivity.this, getString(R.string.toast_book_path_error));
                deleteBookFromDB(bookPath);
                finish();
            }
        }else {
            finish();
        }
    }

    /**
     * 删除文件
     * @param bookPath
     */
    private void deleteBookFromDB(String bookPath){
        FileDBManager manager = new FileDBManager(ReadBookActivity.this);
        manager.delete(ReaderSqlHelper.PATH , new String[]{bookPath});
        BookChangeManager.notifyAllObserver();
    }

    private void initView() {
        toolbar = findViewById(R.id.titleBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(bookFile.getName().replace(Constants.FORMAT_TXT, ""));
        tv_book = findViewById(R.id.tv_book);
        tv_book.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void initData(){
        tv_book.post(new Runnable() {
            @Override
            public void run() {
                new FileUtil().readFileContent(bookFile, new ReadFileListener() {
                    @Override
                    public void fileContent(final String fileContent) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_book.setText(fileContent);
                            }
                        });
                    }
                });
            }
        });

    }


}

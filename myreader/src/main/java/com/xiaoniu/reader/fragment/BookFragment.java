package com.xiaoniu.reader.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoniu.reader.R;
import com.xiaoniu.reader.activity.ReadBookActivity;
import com.xiaoniu.reader.activity.SelectBookActivity;
import com.xiaoniu.reader.adapter.BookListAdapter;
import com.xiaoniu.reader.bean.Book;
import com.xiaoniu.reader.database.FileDBManager;
import com.xiaoniu.reader.database.ReaderSqlHelper;
import com.xiaoniu.reader.listener.BookChangeObserver;
import com.xiaoniu.reader.listener.BookListListener;
import com.xiaoniu.reader.manager.BookChangeManager;
import com.xiaoniu.reader.utils.Constants;
import com.xiaoniu.reader.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoniu
 * @date 2018/7/19.
 */

public class BookFragment extends Fragment implements BookChangeObserver{

    public static final String TAG = "BookFragment";

    private RecyclerView rv_book_list;
    private Toolbar toolbar;
    private BookListAdapter bookAdapter;
    private List<Book> bookList = new ArrayList<>();
    private FileDBManager fileDBManager = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_book, container, false);
        initView(mainView);
        initData();
        return mainView;
    }

    private void initView(View mainView) {
        toolbar = mainView.findViewById(R.id.titleBar);
        toolbar.inflateMenu(R.menu.book_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                menuClick(item.getItemId());
                return false;
            }
        });
        rv_book_list = mainView.findViewById(R.id.rv_book_list);
        rv_book_list.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        bookAdapter = new BookListAdapter(bookList);
        bookAdapter.setBookListListener(new OnItemClickListener());
        rv_book_list.setAdapter(bookAdapter);

        BookChangeManager.addBookChangeObserver(this);
    }

    private void initData(){
        if (fileDBManager == null){
            fileDBManager = new FileDBManager(getActivity());
        }
        Cursor cursor = fileDBManager.query(new String[]{ReaderSqlHelper.NAME, ReaderSqlHelper.PATH});
        if (cursor != null){
            bookList.clear();
            Book book = null;
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ReaderSqlHelper.NAME));
                String path = cursor.getString(cursor.getColumnIndex(ReaderSqlHelper.PATH));
                book = new Book(name, path);
                bookList.add(book);
            }
        }
        bookAdapter.notifyDataSetChanged();
    }

    /**
     * 处理menu的更新
     * @param menuID
     */
    private void menuClick(int menuID){
        if (menuID == R.id.menu_wifi){
            //todo wifi传书
        }else if (menuID == R.id.menu_local){
            Intent intent = new Intent(getActivity(), SelectBookActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void bookChange() {
        initData();
    }

    private class OnItemClickListener implements BookListListener{

        @Override
        public void onItemLongClick(int position) {
            showDialog(position);
        }

        @Override
        public void onItemClick(int position) {
            Book book = bookList.get(position);
            Intent intent = new Intent(getActivity(), ReadBookActivity.class);
            intent.putExtra(Constants.BOOK_PATH, book.getPath());
            startActivity(intent);
        }
    }


    private void showDialog(int position){
        final String bookPath = bookList.get(position).getPath();
        new AlertDialog
                .Builder(getActivity())
                .setTitle(R.string.dialog_title)
                .setMessage(getString(R.string.delete_book_msg,bookPath))
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteBook(bookPath);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 删除图书
     * @param bookPath
     */
    private void deleteBook(String bookPath) {
        if (TextUtils.isEmpty(bookPath)){
            return;
        }
        boolean delete = fileDBManager.delete(ReaderSqlHelper.PATH, new String[]{bookPath});
        if (delete){
            ToastUtil.showToast(getActivity(), getString(R.string.delete_success));
            BookChangeManager.notifyAllObserver();
        }else {
            ToastUtil.showToast(getActivity(), getString(R.string.delete_fail));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BookChangeManager.removeObserver(this);
    }
}

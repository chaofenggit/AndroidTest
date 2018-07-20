package com.xiaoniu.reader.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoniu.reader.R;
import com.xiaoniu.reader.adapter.BookListAdapter;
import com.xiaoniu.reader.bean.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoniu
 * @date 2018/7/19.
 */

public class BookFragment extends Fragment {

    public static final String TAG = "BookFragment";

    private RecyclerView rv_book_list;
    private BookListAdapter bookAdapter;
    private List<Book> bookList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_book, container, false);
        initView(mainView);
        initData();
        return mainView;
    }

    private void initView(View mainView) {
        rv_book_list = mainView.findViewById(R.id.rv_book_list);
        rv_book_list.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        bookAdapter = new BookListAdapter(bookList);
        rv_book_list.setAdapter(bookAdapter);
    }

    private void initData(){
        bookList.clear();
        Book book = null;
        for (int i= 0; i < 10; i++){
            book = new Book("name" + i, "");
            bookList.add(book);
        }
        bookList.add(Book.addBook);
        bookAdapter.notifyDataSetChanged();
    }


}

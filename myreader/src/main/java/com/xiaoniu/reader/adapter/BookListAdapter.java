package com.xiaoniu.reader.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoniu.reader.R;
import com.xiaoniu.reader.bean.Book;

import java.util.List;

/**
 * @author xiaoniu
 * @date 2018/7/20.
 */

public class BookListAdapter extends RecyclerView.Adapter{

    public static final int TYPE_ADD = -1;
    public static final int TYPE_NORMAL = 0;

    private List<Book> bookList;

    public BookListAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_NORMAL){
            viewHolder = new AddBookViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_book_item, parent, false));
        }else if (viewType == TYPE_ADD){
            viewHolder = new NormalBookViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_add_book_item, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return bookList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return bookList == null ? 0:bookList.size();
    }

    /**
     * 正常书本holder
     */
    class NormalBookViewHolder extends RecyclerView.ViewHolder {

        public NormalBookViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 添加书本holder
     */
    class AddBookViewHolder extends RecyclerView.ViewHolder {

        public AddBookViewHolder(View itemView) {
            super(itemView);
        }
    }

}

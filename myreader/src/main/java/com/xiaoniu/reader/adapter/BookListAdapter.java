package com.xiaoniu.reader.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoniu.reader.R;
import com.xiaoniu.reader.bean.Book;
import com.xiaoniu.reader.listener.OnItemLongClickListener;
import com.xiaoniu.reader.widget.NormalBookView;

import java.util.List;

/**
 * @author xiaoniu
 * @date 2018/7/20.
 */

public class BookListAdapter extends RecyclerView.Adapter implements View.OnLongClickListener {

    public static final int TYPE_ADD = -1;
    public static final int TYPE_NORMAL = 0;

    private List<Book> bookList;
    private OnItemLongClickListener longClickListener;

    public BookListAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void setLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_NORMAL){
            View normalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_book_item, parent, false);
            normalView.setOnLongClickListener(this);
            viewHolder = new NormalBookViewHolder(normalView);
        }else if (viewType == TYPE_ADD){
            View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_add_book_item, parent, false);
            addView.setOnLongClickListener(this);
            viewHolder = new AddBookViewHolder(addView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalBookViewHolder){
            NormalBookViewHolder normalBook = (NormalBookViewHolder) holder;
            normalBook.bookView.setBookName(bookList.get(position).getName());
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemViewType(int position) {
        return bookList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return bookList == null ? 0:bookList.size();
    }

    @Override
    public boolean onLongClick(View v) {
        if (longClickListener != null){
            longClickListener.onItemLongClick((int)v.getTag());
        }
        return false;
    }

    /**
     * 正常书本holder
     */
    class NormalBookViewHolder extends RecyclerView.ViewHolder {
        public NormalBookView bookView;
        public NormalBookViewHolder(View itemView) {
            super(itemView);
            bookView = itemView.findViewById(R.id.bv_normal);
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

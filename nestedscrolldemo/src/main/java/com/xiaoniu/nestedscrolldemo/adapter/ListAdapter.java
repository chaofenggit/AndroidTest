package com.xiaoniu.nestedscrolldemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoniu.nestedscrolldemo.R;

import java.util.List;

/**
 * @author xiaoniu
 * @date 2019/7/23.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>{

    private List<String> datas;

    public ListAdapter(List<String> list) {
        datas = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_item.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0:datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
        }
    }
}

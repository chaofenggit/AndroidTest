package com.xiaoniu.kolitstart.music

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xiaoniu.kolitstart.R

/**
 * @author zhaohe@iapppay.com
 * @date  2018/6/5.
 */
class MusicAdapter(private var context:Context, private var musicList:List<String>) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(LayoutInflater.from(context).inflate(R.layout.music_item,parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.titleView?.text = musicList.get(position)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }


    inner  class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        var titleView: TextView = itemView.findViewById(R.id.item_title)
    }
}
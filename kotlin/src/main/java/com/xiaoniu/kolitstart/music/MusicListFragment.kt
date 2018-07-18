package com.xiaoniu.kolitstart.music

import android.app.Fragment
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xiaoniu.kolitstart.R
import kotlinx.android.synthetic.main.fragment_music_list.view.*

/**
 * @author zhaohe@iapppay.com
 * @date  2018/7/17.
 */
class MusicListFragment : Fragment() {

    var adapter: MusicAdapter?= null
    private var musicList = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        val mainView = inflater.inflate(R.layout.fragment_music_list, container, false)
        initView(mainView)
        return mainView
    }

    private fun initView(view:View) {
        adapter = MusicAdapter(activity, musicList)
        view.recycler.layoutManager = LinearLayoutManager(activity)
        view.recycler.adapter =adapter
        view.btn_refresh.setOnClickListener(View.OnClickListener {
            onRefresh()
        })
    }


    /**
     * 刷新列表
     */
    private fun onRefresh(){
        for (i in 0..10){
            musicList.add("" + i)
        }
        adapter!!.notifyDataSetChanged()
        querySong()
    }

    private fun querySong(){
        val query = activity.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Audio.Media.TITLE), null, null, null)

        if(query.count > 0){
            query.moveToFirst()
            do {
                musicList.add(query.getString(0))
            } while (query.moveToNext())
        }
        query.close()
        adapter!!.notifyDataSetChanged()
    }

}
package com.xiaoniu.kolitstart

import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

     var TAG:String  = "121212"
     var adapter:MusicAdapter?= null
    private var musicList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MusicAdapter(this, musicList)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter =adapter
    }

    /**
     * 刷新列表
     */
    fun onRefresh(view: View?){
        for (i in 0..10){
            musicList.add("" + i)
        }
        adapter!!.notifyDataSetChanged()
        querySong()
    }

    fun querySong(){
        val query = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Audio.Media.TITLE), null, null, null)

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

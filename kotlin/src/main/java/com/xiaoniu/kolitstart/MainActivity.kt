package com.xiaoniu.kolitstart

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.xiaoniu.kolitstart.music.MusicListFragment

class MainActivity : Activity() {

    var TAG:String  = "MainActivity"
    val KEY_TYPE:String = "KEY_TYPE"
    val musicListType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intiData()
    }

    private fun intiData(){
        val type = intent.getIntExtra(KEY_TYPE, 0)
        Log.d(TAG, "type = " + type)
        if (type == musicListType){
            openMusicListFragment()
        }
    }

    private fun openMusicListFragment() {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, MusicListFragment()).commit()
    }

}

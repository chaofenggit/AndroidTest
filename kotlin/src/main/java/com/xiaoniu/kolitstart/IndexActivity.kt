package com.xiaoniu.kolitstart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_index.*

/**
 * @author zhaohe@iapppay.com
 * @date  2018/7/17.
 *
 * 索引activity
 */
class IndexActivity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        initView()
    }

    /**
     * 初始化
     */
    private fun initView() {
        btn_music_list.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val btnId = v?.id
        when(btnId){
            R.id.btn_music_list -> openMusicList()
        }
    }

    private fun openMusicList(){
        startActivity(Intent(IndexActivity@this, MainActivity::class.java))

//        val intent = Intent()
//        intent.setClass(this, MainActivity::class.java)
//        intent.putExtra("KEY_TYPE", 0)
//        startActivity(intent)
    }

}
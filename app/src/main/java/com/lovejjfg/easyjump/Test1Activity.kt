package com.lovejjfg.easyjump

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lovejjfg.easyjump.utils.Constants
import kotlinx.android.synthetic.main.activity_content.*

class Test1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        val text = intent.getStringExtra(Constants.TITLE)
        title = text
        content.text = text
    }

}

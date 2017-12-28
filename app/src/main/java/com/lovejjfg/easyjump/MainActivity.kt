package com.lovejjfg.easyjump

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lovejjfg.easyjump.utils.Constants
import com.lovejjfg.easyjump.utils.JumpUtils
import com.lovejjfg.easyjump.utils.NotifyUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_t1.setOnClickListener {
            NotifyUtils.createNotify(this, Constants.KNOWN_SCHEME + "test1?title=首页测试1", "首页测试11")

        }
        bt_t2.setOnClickListener {
            NotifyUtils.createNotify(this, Constants.KNOWN_SCHEME + "test1?title=首页测试2", "首页测试22")

        }
        bt_web.setOnClickListener {
            NotifyUtils.createNotify(this, "https://www.baidu.com", "首页网页跳转")
        }

        bt_t11.setOnClickListener {
            JumpUtils.jumpTest1(this)
        }
        bt_t22.setOnClickListener {
            JumpUtils.jumpTest2(this)

        }
        bt_web1.setOnClickListener {
            JumpUtils.jumpWeb(this, "https://www.baidu.com")

        }
    }
}

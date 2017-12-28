package com.lovejjfg.easyjump

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lovejjfg.easyjump.utils.JumpUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isTaskRoot) {
            //if there is the MainActivity just finish it
            finish()
            return
        }
        setContentView(R.layout.activity_splash)
        logo.postDelayed({
            JumpUtils.jumpHome(this)
            finish()
        }, 1000)

    }
}

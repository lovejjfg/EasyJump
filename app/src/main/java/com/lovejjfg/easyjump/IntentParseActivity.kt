package com.lovejjfg.easyjump

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.TaskStackBuilder
import android.util.Log
import com.lovejjfg.easyjump.utils.JumpUtils
import com.lovejjfg.easyjump.utils.ViewUtils

class IntentParseActivity : AppCompatActivity() {
    val TAG = IntentParseActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.data
        try {
            if (data != null) {
                Log.i(TAG, "url: " + data.toString())
                val resultIntent = JumpUtils.parseIntent(this, data.toString(), null)
                if (resultIntent == null) {
                    finish()
                    return
                }
                resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                if (ViewUtils.isLaunchedActivity(this, MainActivity::class.java)) {
                    startActivity(resultIntent)
                } else {
                    val stackBuilder = TaskStackBuilder.create(this)
                            .addParentStack(resultIntent.component)
                            .addNextIntent(resultIntent)
                    stackBuilder.startActivities()
                }
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            finish()
        }

    }
}

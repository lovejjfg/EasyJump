/*
 * Copyright (c) 2017.  Joe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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

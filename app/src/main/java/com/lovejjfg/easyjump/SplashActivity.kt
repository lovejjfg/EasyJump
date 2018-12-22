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

import android.os.Bundle
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v7.app.AppCompatActivity
import com.lovejjfg.easyjump.utils.JumpUtils
import kotlinx.android.synthetic.main.activity_splash.logo

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isTaskRoot) {
            //if there is the MainActivity just finish it
            finish()
            return
        }
        setContentView(R.layout.activity_splash)
        logo.alpha = 0F
        val d3 = (Math.random() * 10F).toInt()
        if (d3 % 2 == 0) {
            val d = (Math.random() * 10F).toInt()
            logo.translationY = if (d % 2 == 0) 100F else -100F
        } else {
            val d1 = (Math.random() * 10F).toInt()
            logo.translationX = if (d1 % 2 == 1) 100F else -100F
        }
        logo.scaleX = .6F
        logo.scaleY = .6F
        logo.animate()
            .alpha(1F)
            .translationY(0F)
            .translationX(0F)
            .scaleX(1F)
            .scaleY(1F)
            .setInterpolator(FastOutSlowInInterpolator())
            .setDuration(300).startDelay = 300
        logo.postDelayed({
            JumpUtils.jumpHome(this)
            finish()
        }, 1000)
    }
}

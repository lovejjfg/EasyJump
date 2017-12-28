/*
 *
 *   Copyright (c) 2017.  Joe
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.lovejjfg.easyjump

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import com.lovejjfg.easyjump.utils.Constants
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {

    var mWeb: WebView? = null
    var loading: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        val title = intent.getStringExtra(Constants.TITLE)
//        setMyTitle(title)
        setContentView(R.layout.activity_web)
        mWeb = web
        loading = pb
        toolbar?.setNavigationOnClickListener({ finish() })

        val url = intent.getStringExtra(Constants.URL)
        if (TextUtils.isEmpty(url)) {
            finish()
            return
        }
        mWeb!!.isVerticalScrollBarEnabled = false
        mWeb!!.isHorizontalScrollBarEnabled = false
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //            WebView.setWebContentsDebuggingEnabled(true);
        //        }
        val webSettings = mWeb!!.settings
        webSettings!!.javaScriptEnabled = true
        //        webSettings.setUseWideViewPort(true);
        //        webSettings.setLoadWithOverviewMode(true);
        mWeb!!.isClickable = true
        webSettings.domStorageEnabled = true
        webSettings.loadsImagesAutomatically = true
        webSettings.builtInZoomControls = true
        webSettings.blockNetworkImage = false
        webSettings.displayZoomControls = false
        //        mWeb.setWebViewClient(new WebViewClient());
        mWeb!!.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(webView: WebView, s: String) {
                super.onReceivedTitle(webView, s)
                toolbar?.title = s
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                loading?.progress = newProgress
                if (newProgress == 100)
                    loading?.visibility = View.GONE

            }
        }
        mWeb!!.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
//                loading?.show()
                loading?.visibility = View.VISIBLE

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                loading?.visibility = View.GONE
//                loading?.dismiss()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                loading?.visibility = View.GONE
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                super.onReceivedSslError(view, handler, error)
                loading?.visibility = View.GONE
            }

            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                super.onReceivedHttpError(view, request, errorResponse)
                loading?.visibility = View.GONE
            }

        }

        mWeb!!.loadUrl(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        mWeb?.destroy()
    }

    override fun onBackPressed() {
        if (mWeb?.canGoBack()!!) {
            mWeb?.goBack()
            return
        }
        super.onBackPressed()
    }

}

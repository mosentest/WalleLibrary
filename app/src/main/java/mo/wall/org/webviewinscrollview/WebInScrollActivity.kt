package mo.wall.org.webviewinscrollview

import android.os.Bundle
import android.os.Message
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-11-22 10:11
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class WebInScrollActivity : BaseAppCompatActivity() {

    private lateinit var mWebview: FixWebView
    private lateinit var mScrollview: FixScrollView


    private lateinit var mFlay: FrameLayout

    override fun handleMessageAct(msg: Message?) {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_in_scroll)

        mFlay = findViewById<FrameLayout>(R.id.flay)
        mWebview = FixWebView(ContextX(this))

        mFlay.addView(mWebview)
//        mWebview = findViewById<FixWebView>(R.id.webview)
        mScrollview = findViewById<FixScrollView>(R.id.scrollview)

        mWebview.loadUrl("https://www.toouds.top/newGame/index.html?gameChannelId=1078")


        mScrollview.webView = mWebview

        /**
         * 当webview不设置固定的高度的时候，需要重新测量位置
         */
//        mWebview.setWebViewClient(object : WebViewClient() {
//            override fun onPageFinished(view: WebView, url: String) {
//                super.onPageFinished(view, url)
//                val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//                val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//                //重新测量
//                mWebview.measure(w, h)
//            }
//        })


        /**
         * 改为 lambda
         *
         * mWebview.setOnTouchListener(object : View.OnTouchListener {
         *       override fun onTouch(v: View?, event: MotionEvent?): Boolean {
         *      return true
         *     }
         * })
         *
         */
//        mWebview.setOnTouchListener { v, event ->
//            //这也是网上普遍说的解决方法，可是没有解决到问题
//            v.parent.requestDisallowInterceptTouchEvent(true)
//            true
//        }

    }
}
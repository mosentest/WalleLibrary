package mo.wall.org.webviewinscrollview

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView
import android.widget.ScrollView

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-11-22 10:17
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class FixScrollView : ScrollView {


    lateinit var webView: WebView;

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }


    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {

        }
    }

    /**
     * 解决webview固定高度在scrollview问题
     */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (webView == null) {
            return super.onInterceptTouchEvent(ev)
        }
        return if (webView.isFocusable()
                || webView.isFocused()
                || webView.isActivated()
                || webView.isInEditMode()
                || webView.isPressed()) {
            false
        } else super.onInterceptTouchEvent(ev)
    }


}

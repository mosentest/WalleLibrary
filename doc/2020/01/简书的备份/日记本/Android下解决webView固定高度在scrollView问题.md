用了webview作为富文本输入框，嵌套在scrollview里面发现没办法滚动起来，参考了下网上的资料，没找到好的结果，自己重写了下scrollview的onInterceptTouchEvent实现事件拦截，实现了webview能在固定高度下滚动起来
```
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
```
由于我是菜逼，没办法上传视频，只能发出GitHub的地址视频连接

[WalleLibrary](https://github.com/moz1q1/WalleLibrary)


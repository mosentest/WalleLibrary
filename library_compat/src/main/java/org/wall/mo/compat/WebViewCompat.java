package org.wall.mo.compat;

import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2019/5/27-17:23
 * desc   :
 * version: 1.0
 */
public class WebViewCompat {

    /**
     * 对于Android 4.2以前，需要采用拦截prompt（）的方式进行漏洞修复
     * 移除漏洞
     *
     * @param webView
     */
    public static void removeBug(WebView webView) {
        // 在Android 3.0以下 去除远程代码执行漏洞
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");
    }

    public static void setWebSettings(@NonNull WebSettings ws) {
        // 设置编码格式 the default is "UTF-8", so we can not set it.
        // ws.setDefaultTextEncodingName("UTF-8")

        // 加快HTML网页加载完成的速度，等页面finish再加载图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ws.setLoadsImagesAutomatically(true);
        } else {
            ws.setLoadsImagesAutomatically(false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0 以后的WebView加载的链接为Https开头，但是链接里面的内容，
            // 比如图片为Http链接，这时候，图片就会加载不出来
            // 下面两者都可以
            // Android 5.0上Webview默认不允许加载Http与Https混合内容
            // ws.setMixedContentMode(ws.getMixedContentMode())
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 定位相关
        ws.setGeolocationEnabled(true);
        // Set cache size to 2 mb by default. should be more than enough
        ws.setDomStorageEnabled(true);
        ws.setAppCacheMaxSize(1024 * 1024 * 2);
        // This next one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line.
        // UPDATE: no hardcoded path. Thanks to Kevin Hawkins

//        String appCachePath = App.getInstance().getCacheDir().getAbsolutePath();
//        ws.setAppCachePath(appCachePath);
        // js
        ws.setJavaScriptEnabled(true);
        // 获取到UserAgentString
        String userAgent = ws.getUserAgentString();
        ws.setUserAgentString(userAgent);
        // 打印结果
        // if (BuildConfig.LOG_DEBUG){
        // LogUtil.i("BaseWebActivity1", "User Agent:" + userAgent)
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        // catch
        ws.setAppCacheEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // access Assets and resources
        // 设置是否允许webview使用File协议 - 加载本地html文件
        ws.setAllowFileAccess(false);
        // 4.1以后默认禁止
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ws.setAllowFileAccessFromFileURLs(false);
            ws.setAllowUniversalAccessFromFileURLs(false);
        }
        // WebView加上这个设置后,WebView里的字体就不会随系统字体大小设置发生变化了
        ws.setTextZoom(100);

        // 支持屏幕缩放
        // zoom page
        ws.setBuiltInZoomControls(true);
        ws.setSupportZoom(true);
        // 不显示webview缩放按钮
        ws.setDisplayZoomControls(false);

        // 设置加载进来的页面自适应手机屏幕
        // 这个方法有些界面不起作用
        // ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
        // 设置webview推荐使用的窗口
        ws.setUseWideViewPort(true);
        // 设置webview加载的页面的模式
        ws.setLoadWithOverviewMode(true);
        // 不保存密码，已经废弃了该方法，以后的版本都不会保存密码
        ws.setSavePassword(false);
    }
}

package org.wall.mo.widgets.webview.headerbug;


import android.util.Log;
import android.webkit.WebView;


import org.wall.mo.utils.relect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * @author ziqi-mo
 * 为了修改webview携带的包名，正常app用不上
 */
public class WebViewCompat {

    private final static String TAG = "WebViewCompat";

    /**
     * @param webView
     * @param packageName
     */
    public static void handleLoadUrlPackageName(WebView webView, String packageName, ClassLoader classLoader) {
        try {
            //获取WebView 的 mProvider(android.webkit.WebViewProvider)成员变量
            //对应的实现类是com.android.webview.chromium.WebViewChromium
            Field mProviderField = FieldUtils.getDeclaredField(webView.getClass(), "mProvider", true);
            //读取mProvider的值
            Object mProviderObject = FieldUtils.readField(mProviderField, webView, true);
            //获取WebViewChromium的mAwContents(org.chromium.android_webview.AwContents)成员变量值 ,对应的接口类(org.chromium.content.browser.SmartClipProvider)
            Field mAwContentsField = FieldUtils.getDeclaredField(mProviderObject.getClass(), "mAwContents", true);
            //读取mAwContents的值
            Object mAwContentsObject = FieldUtils.readField(mAwContentsField, mProviderObject, true);
            //获取AwContents的mNavigationController(org.chromium.content_public.browser.NavigationController)成员变量
            Field mNavigationControllerField = FieldUtils.getDeclaredField(mAwContentsObject.getClass(), "mNavigationController", true);
            Object mNavigationControllerObject = FieldUtils.readField(mNavigationControllerField, mAwContentsObject, true);
            //这里做动态代理,对NavigationController接口做动态代理,这是最终的地址
            Class<?>[] inter = mNavigationControllerObject.getClass().getInterfaces();
            NavigationControllerInvocationHandler mHandler = new NavigationControllerInvocationHandler(mNavigationControllerObject, packageName);
            Object mObj = Proxy.newProxyInstance(classLoader, inter, mHandler);
            FieldUtils.writeField(mNavigationControllerField, mAwContentsObject, mObj);
        } catch (Exception e) {
            Log.wtf(TAG, e);
        }
    }


}

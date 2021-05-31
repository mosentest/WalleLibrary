package org.wall.mo.widgets.webview.systembug;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class HooKWebViewBug {

    /**
     * 在Application上的 onCreate 进行初始化
     */
    public static void hookWebView() {
        int sdkInt = Build.VERSION.SDK_INT;
        try {
            Class<?> factoryClass = Class.forName("android.webkit.WebViewFactory");
            Field field = factoryClass.getDeclaredField("sProviderInstance");
            field.setAccessible(true);
            Object sProviderInstance = field.get(null);
            if (sProviderInstance != null) {
                Log.i("A", "sProviderInstance isn't null");
                return;
            }
            Method getProviderClassMethod;
            if (sdkInt > 22) {
                getProviderClassMethod = factoryClass.getDeclaredMethod("getProviderClass");
            } else if (sdkInt == 22) {
                getProviderClassMethod = factoryClass.getDeclaredMethod("getFactoryClass");
            } else {
                Log.i("A", "Don't need to Hook WebView");
                return;
            }
            getProviderClassMethod.setAccessible(true);
            Class<?> providerClass = (Class<?>) getProviderClassMethod.invoke(factoryClass);
            Class<?> delegateClass = Class.forName("android.webkit.WebViewDelegate");
            Constructor<?> providerConstructor = providerClass.getConstructor(delegateClass);
            if (providerConstructor != null) {
                providerConstructor.setAccessible(true);
                Constructor<?> declaredConstructor = delegateClass.getDeclaredConstructor();
                declaredConstructor.setAccessible(true);
                sProviderInstance = providerConstructor.newInstance(declaredConstructor.newInstance());
                Log.i("A", "sProviderInstance:{}" + sProviderInstance);
                field.set("sProviderInstance", sProviderInstance);
            }
            Log.i("A", "Hook done!");
        } catch (Throwable e) {
            Log.wtf("A", e);
        }
    }
}

package org.wall.mo.ui.webview.systembug;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.wall.mo.utils.ua.UAHelper;

/**
 * 参考链接<br/>
 * http://blog.csdn.net/codingnotes/article/details/79020542
 *
 * @author moziqi
 * 为了修改webview在打了系统签名，奔溃问题
 */
public class HookSystemWebView extends WebView {

    private static boolean isHookSuccess = false;

    static {
        hookWebView();
    }

    public static void hookWebView() {
        int sdkInt = Build.VERSION.SDK_INT;
        try {
            final int uid = android.os.Process.myUid();
            if (uid == 0 || uid == android.os.Process.SYSTEM_UID) {
                Class<?> factoryClass = Class.forName("android.webkit.WebViewFactory");
                Field field = factoryClass.getDeclaredField("sProviderInstance");
                field.setAccessible(true);
                Object sProviderInstance = field.get(null);
                if (sProviderInstance != null) {
                    // log.debug("sProviderInstance isn't null");
                    isHookSuccess = true;
                    return;
                }
                Method getProviderClassMethod;
                if (sdkInt > 22) {
                    getProviderClassMethod = factoryClass.getDeclaredMethod("getProviderClass");
                } else if (sdkInt == 22) {
                    getProviderClassMethod = factoryClass.getDeclaredMethod("getFactoryClass");
                } else {
                    // log.info("Don't need to Hook WebView");
                    isHookSuccess = true;
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
                    // log.debug("sProviderInstance:{}", sProviderInstance);
                    field.set("sProviderInstance", sProviderInstance);
                    isHookSuccess = true;
                }
                // log.debug("Hook done!");
            } else {
                isHookSuccess = true;
            }
        } catch (Throwable e) {
            // log.error(e);
            isHookSuccess = false;
        }
    }

    public HookSystemWebView(Context context) {
        super(context);
        hookUA();
    }

    public HookSystemWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        hookUA();
    }

    public HookSystemWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        hookUA();
    }

    /**
     * 修改UA
     */
    private void hookUA() {
        WebSettings settings = getSettings();
        settings.setUserAgentString(UAHelper.instance(getContext()));
    }

    public static boolean isHookSuccess() {
        return isHookSuccess;
    }
}

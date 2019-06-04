package org.wall.mo.widgets.webview.headerbug;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;


import android.text.TextUtils;
import android.util.Log;

import org.wall.mo.utils.relect.FieldUtils;


/**
 * @author ziqi-mo
 */
public class NavigationControllerInvocationHandler implements InvocationHandler {

    private final static String TAG = "NavigationControllerInvocationHandler";

    private Object mTargetNavigationController;

    private String mPackageName;

    public NavigationControllerInvocationHandler(Object mObject, String packageName) {
        mTargetNavigationController = mObject;
        mPackageName = packageName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if ("loadUrl".equals(method.getName())) {
                if (TextUtils.isEmpty(mPackageName)) {
                    mPackageName = "com.android.default";
                }
                Class<?> clazz = args[0].getClass();
                Field mExtraHeadersField = FieldUtils.getDeclaredField(clazz, "mExtraHeaders", true);
                Map<String, String> headerMap = (Map<String, String>) FieldUtils.readField(mExtraHeadersField, args[0], true);
                headerMap.put("X-Requested-With", mPackageName);
                FieldUtils.writeField(mExtraHeadersField, args[0], headerMap);
            }
            return method.invoke(mTargetNavigationController, args);
        } catch (Exception e) {
            Log.wtf(TAG, e);
        }
        return method.invoke(mTargetNavigationController, args);
    }
}
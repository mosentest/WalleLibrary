package org.wall.mo.utils;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/22 9:05 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public interface ILifecycleObserver extends LifecycleObserver {

    final static String TAG = ILifecycleObserver.class.getSimpleName();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onLifeClear();

    public static class InnerClass {
        public static void clear(Object o) {
            if (o == null) {
                return;
            }
            Log.i(TAG, "clear>>" + o.getClass());
            Field[] fields = o.getClass().getFields();
            clearField(o, fields);
            Field[] declaredFields = o.getClass().getDeclaredFields();
            clearField(o, declaredFields);
        }

        private static void clearField(Object o, Field[] fields) {
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    String name = field.getName();
                    Log.i(TAG, "clearField>>" + name);
                    Object fieldObj = field.get(o);
                    if (fieldObj != null) {
                        Method[] methods = field.getClass().getMethods();
                        clearMethod(fieldObj, methods);
                        Method[] declaredMethods = field.getClass().getDeclaredMethods();
                        clearMethod(fieldObj, declaredMethods);
                        field.set(o, null);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }

        private static void clearMethod(Object o, Method[] methods) {
            for (Method method : methods) {
                method.setAccessible(true);
                try {
                    String name = method.getName();
                    Log.i(TAG, "clearMethod>>" + name);
                    if (name.toLowerCase().contains("clear")
                            || name.toLowerCase().contains("destroy")
                            || name.toLowerCase().contains("close")
                            || name.toLowerCase().contains("cancel")
                    ) {
                        method.invoke(o);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }


}

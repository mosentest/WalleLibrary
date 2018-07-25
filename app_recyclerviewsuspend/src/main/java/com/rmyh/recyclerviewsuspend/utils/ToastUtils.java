package com.rmyh.recyclerviewsuspend.utils;

import android.widget.Toast;

import com.rmyh.recyclerviewsuspend.common.MyApplication;

/**
 * Toast工具类
 */

public class ToastUtils {

    private static Toast toast = null;

    public static void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}

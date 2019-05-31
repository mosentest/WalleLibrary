package org.wall.mo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2019/5/27-16:41
 * desc   : 兼容所有情况的跳转模式
 * version: 1.0
 */
public class StartActivityCompat {


    public static final String NEXT_PARCELABLE = "next_parcelable";

    public static final String NEXT_EXTRA = "next_extra";

    /**
     * 基本跳转
     *
     * @param context
     * @param fragment
     * @param intent
     */
    public static void startActivity(Context context, Fragment fragment, Intent intent) {
        startActivity(context, fragment, intent, -1);
    }

    public static void startActivity(Context context, Fragment fragment, Intent intent, int requestCode) {
        startActivity(context, fragment, intent, requestCode, null);
    }

    /**
     * 多个参数都封装实现Parcelable 方便管理参数的传递，就不需要写多个 key
     *
     * @param context
     * @param fragment
     * @param intent
     * @param requestCode
     * @param parcelable
     */
    public static void startActivity(Context context, Fragment fragment, Intent intent, int requestCode, Parcelable parcelable) {
        if (parcelable != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(NEXT_PARCELABLE, parcelable);
            intent.putExtra(NEXT_EXTRA, bundle);
        }
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            } else {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }
}

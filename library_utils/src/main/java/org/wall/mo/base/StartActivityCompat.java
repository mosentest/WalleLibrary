package org.wall.mo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import org.wall.mo.utils.StringUtils;

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2019/5/27-16:41
 * desc   : 兼容所有情况的跳转模式
 * version: 1.0
 */
public class StartActivityCompat {


    //public static final String NEXT_EXTRA = "next_extra";

    /**
     * 封装其他，其他参数都转成parcelable对象，方便代码跟踪
     */
    public static final String NEXT_PARCELABLE = "next_parcelable";


    /**
     * 下个页面的标题
     */
    public static final String NEXT_TITLE = "next_title";

    /**
     * 控制下个页面是否显示标题
     */
    public static final String NEXT_SHOW_BACK = "next_show_back";

    /**
     * 基本跳转，不带标题和返回处理
     *
     * @param context
     * @param fragment
     * @param intent
     */
    public static void startActivity(Context context, Fragment fragment, Intent intent) {
        startActivity(context, fragment, intent, -1);
    }

    /**
     * 基本跳转，带标题和返回处理
     *
     * @param context
     * @param fragment
     * @param title
     * @param showBack
     * @param intent
     */
    public static void startActivity(Context context, Fragment fragment, String title, boolean showBack, Intent intent) {
        startActivity(context, fragment, title, showBack, intent, -1);
    }

    /**
     * requestCode跳转，带标题和返回处理
     *
     * @param context
     * @param fragment
     * @param title
     * @param showBack
     * @param intent
     * @param requestCode
     */
    public static void startActivity(Context context,
                                     Fragment fragment,
                                     String title,
                                     boolean showBack,
                                     Intent intent,
                                     int requestCode) {
        startActivity(context, fragment, title, showBack, intent, requestCode, null);
    }

    /**
     * requestCode跳转，不带标题和返回处理
     *
     * @param context
     * @param fragment
     * @param intent
     * @param requestCode
     */
    public static void startActivity(Context context,
                                     Fragment fragment,
                                     Intent intent,
                                     int requestCode) {
        startActivity(context, fragment, null, false, intent, requestCode, null);
    }

    /**
     * 多个参数都封装实现Parcelable 方便管理参数的传递，就不需要写多个 key
     *
     * @param context
     * @param fragment
     * @param intent
     * @param title       是否显示展示标题
     * @param showBack    是否显示返回按钮
     * @param requestCode
     * @param parcelable
     */
    public static void startActivity(Context context,
                                     Fragment fragment,
                                     String title,
                                     boolean showBack,
                                     Intent intent,
                                     int requestCode,
                                     Parcelable parcelable) {
        Bundle bundle = new Bundle();
        //设置相关参数
        if (parcelable != null) {
            bundle.putParcelable(NEXT_PARCELABLE, parcelable);
        }
        //设置相关参数
        if (!StringUtils.isEmpty(title)) {
            bundle.putString(NEXT_TITLE, title);
        }
        //设置相关参数
        bundle.putBoolean(NEXT_SHOW_BACK, showBack);

        intent.putExtras(bundle);

        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }
}

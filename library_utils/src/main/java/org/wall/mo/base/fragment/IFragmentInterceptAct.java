package org.wall.mo.base.fragment;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/5/31 11:33 AM
 * Description: ${DESCRIPTION}
 * History: 拦截返回键
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public interface IFragmentInterceptAct {

    /**
     * called when you want to intercept the  {@link KeyEvent#KEYCODE_BACK} event of
     * {@link AppCompatActivity#onBackPressed()} method
     * <p>
     * （1）没有弹框，没有菜单，没有共享变换-----该情况，finish和onBackPressed是完全一样的。
     * （2）存在弹框、菜单等-----该情况，onBackPressed要先关闭popWindow
     * （3）存在共享变化-----该情况，finish不会调用变换动画，必须使用onBackPressed方法
     *
     * @return 是否拦截Activity的onBackPressed事件，默认false不拦截
     * @author LuoHao
     * Created on 2018/3/18 2:06
     */
    boolean onFragmentInterceptOnBackPressed();

    /**
     * 拦截getIntent获取参数
     *
     * @return
     */
    boolean onFragmentInterceptGetIntent(Intent intent);

    /**
     * 获取startActivity的intent参数
     * 参数传递
     *
     * @param parcelableNextExtra
     * @return
     */
    void onFragmentInterceptNextParcelableExtra(Parcelable parcelableNextExtra);
}

package org.wall.mo.ui.swiperefreshlayout;

import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * 作者 create by moziqi on 2018/7/12
 * 邮箱 709847739@qq.com
 * 说明
 **/
public class SwipeRefreshLayoutUtils {

    /**
     *
     * @param swipeRefreshLayout
     * @param onRefreshListener
     * @param colorResIds
     */
    public static void setBase(SwipeRefreshLayout swipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener onRefreshListener, @ColorRes int... colorResIds) {
        // 设置下拉进度的背景颜色，默认就是白色的
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        swipeRefreshLayout.setColorSchemeResources(colorResIds);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }
}

package org.wall.mo.widgets.recyclerview;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 作者 create by moziqi on 2018/7/12
 * 邮箱 709847739@qq.com
 * 说明
 **/
public class RecyclerViewUtils {

    public static void setLinearLayoutBase(Context context, RecyclerView recyclerView) {
        setLinearLayoutBase(context, recyclerView, new DefaultItemAnimator());
    }

    public static void setLinearLayoutBase(Context context, RecyclerView recyclerView, RecyclerView.ItemAnimator animator) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(animator);
        recyclerView.setNestedScrollingEnabled(false);
    }
}

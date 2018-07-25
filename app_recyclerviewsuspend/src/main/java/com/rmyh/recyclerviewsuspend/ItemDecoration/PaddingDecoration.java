package com.rmyh.recyclerviewsuspend.ItemDecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rmyh.recyclerviewsuspend.R;

/**
 * Created by wen on 2017/8/8.
 */

public class PaddingDecoration extends RecyclerView.ItemDecoration{

    private int padding;

    public PaddingDecoration(Context context) {
        //即你要设置的分割线的宽度 --这里设为10dp
        padding = context.getResources().getDimensionPixelSize(R.dimen.padding);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //outRect就是你那个item条目的矩形
        outRect.left = padding;  //相当于 设置 left padding
        outRect.top = padding;   //相当于 设置 top padding
        outRect.right = padding; //相当于 设置 right padding
        outRect.bottom = padding;  //相当于 设置 bottom padding
    }
}
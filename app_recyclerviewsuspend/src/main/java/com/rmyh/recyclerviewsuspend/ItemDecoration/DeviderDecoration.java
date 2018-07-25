package com.rmyh.recyclerviewsuspend.ItemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rmyh.recyclerviewsuspend.R;

/**
 * Created by wen on 2017/8/8.
 */

public class DeviderDecoration extends RecyclerView.ItemDecoration {

    private int deviderHeight;
    private Paint dividerPaint;

    public DeviderDecoration(Context context) {
        //设置画笔
        dividerPaint = new Paint();
        //设置分割线颜色
        dividerPaint.setColor(context.getResources().getColor(R.color.colorAccent));
        //设置分割线宽度
        deviderHeight = context.getResources().getDimensionPixelSize(R.dimen.divider_height);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //改变宽度
        outRect.bottom = deviderHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //得到列表所有的条目
        int childCount = parent.getChildCount();
        //得到条目的宽和高
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            //计算每一个条目的顶点和底部 float值
            float top = view.getBottom();
            float bottom = view.getBottom() + deviderHeight;
            //重新绘制
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }
}

package com.rmyh.recyclerviewsuspend.common;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SimpleCursorTreeAdapter;

/**
 * Created by wen on 2017/8/8.
 */

public class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

    private RecyclerView mRecyclerView;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public ItemTouchHelperGestureListener(RecyclerView mRecyclerView, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.mRecyclerView = mRecyclerView;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    //一次单独的轻触抬起手指操作，就是普通的点击事件
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //这个ChildHelper类，它会协助获取RecyclerView中的childVIew。 可点击看源码
        View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
        if (childViewUnder != null) {
            RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
            onRecyclerItemClickListener.onItemClick(childViewHolder);
        }
        return true;
    }

    //长按屏幕超过一定时长，就会触发，就是长按事件
    @Override
    public void onLongPress(MotionEvent e) {
        View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
        if (childViewUnder != null) {
            RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
            onRecyclerItemClickListener.onLongClick(childViewHolder);
        }
    }


}

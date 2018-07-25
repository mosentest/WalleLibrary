package com.rmyh.recyclerviewsuspend.common;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import butterknife.OnTouch;

/**
 * Created by wen on 2017/8/8.
 */

public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    //手势探测器
    private GestureDetectorCompat mGestureDetectorCompat;
    private RecyclerView mRecyclerView;

    public OnRecyclerItemClickListener(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        mGestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(),
                new ItemTouchHelperGestureListener(mRecyclerView,this));
    }

    //第一个是拦截触摸事件的，第二个是处理触摸事件的，第三个是处理触摸冲突的。第三个这里我们用不到
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
    //提供单机 长按的方法
    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);
    public abstract void onLongClick(RecyclerView.ViewHolder viewHolder);
}

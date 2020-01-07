package mo.wall.org.nestedrecyclerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import mo.wall.org.nestedrecyclerview.utils.FlingHelper;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-06 20:46
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class ChildRecyclerView extends RecyclerView {


    private FlingHelper mFlingHelper;


    private ParentRecyclerView parentRecyclerView;


    private int mVelocityY = 0;

    boolean isStartFling = false;

    int totalDy = 0;


    public ChildRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public ChildRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChildRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        mFlingHelper = new FlingHelper(context);


        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    dispatchParentFling();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isStartFling) {
                    totalDy = 0;
                    isStartFling = false;
                }
                totalDy += dy;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mVelocityY = 0;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        if (!isAttachedToWindow()) {
            return false;
        }
        boolean fling = super.fling(velocityX, velocityY);
        if (!fling || velocityY >= 0) {
            //fling为false表示加速度达不到fling的要求，将mVelocityY重置
            mVelocityY = 0;
        } else {
            //正在进行fling
            isStartFling = true;
            mVelocityY = velocityY;
        }
        return fling;
    }

    private void dispatchParentFling() {
        if (parentRecyclerView != null) {
            if (isScrollTop() && mVelocityY != 0) {
                //当前ChildRecyclerView已经滑动到顶部，且竖直方向加速度不为0,如果有多余的需要交由父RecyclerView继续fling
                double flingDistance = mFlingHelper.getSplineFlingDistance(mVelocityY);
                if (flingDistance > (Math.abs(totalDy))) {
                    fling(0, -mFlingHelper.getVelocityByDistance(flingDistance + totalDy));
                }
                totalDy = 0;
                mVelocityY = 0;
            }
        }
    }

    boolean isScrollTop() {
        //RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
        return !canScrollVertically(-1);
    }

    public void setParentRecyclerView(ParentRecyclerView parentRecyclerView) {
        this.parentRecyclerView = parentRecyclerView;
    }
}

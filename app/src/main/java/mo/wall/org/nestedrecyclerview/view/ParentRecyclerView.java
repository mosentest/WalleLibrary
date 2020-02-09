package mo.wall.org.nestedrecyclerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mo.wall.org.nestedrecyclerview.NestedParentMultiItemEntity;
import mo.wall.org.nestedrecyclerview.NestedParentMultiItemQuickAdapter;
import mo.wall.org.nestedrecyclerview.utils.FlingHelper;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-06 20:46
 * Description:
 * History: 参考
 * <p>
 * NestedRecyclerView，仿淘宝、京东首页，通过两层嵌套的RecyclerView实现tab的吸顶效果。
 * https://juejin.im/post/5d5f4cfcf265da03e61b18b8
 * https://github.com/JasonGaoH/NestedRecyclerView
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class ParentRecyclerView extends RecyclerView {

    private FlingHelper mFlingHelper;

    private double totalDy = 0;

    /**
     * 记录当前滑动的y轴加速度
     */
    private int velocityY = 0;

    /**
     * 用于判断RecyclerView是否在fling
     */
    private boolean isStartFling = false;

    /**
     * 记录上次Event事件的y坐标
     */
    private float lastY;

    public ParentRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public ParentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mFlingHelper = new FlingHelper(context);

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //如果父RecyclerView fling过程中已经到底部，需要让子RecyclerView滑动神域的fling
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    dispatchChildFling();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isStartFling) {
                    totalDy = 0;
                    isStartFling = false;
                }
                //在RecyclerView fling情况下，记录当前RecyclerView在y轴的便宜
                totalDy += dy;
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //ACTION_DOWN的时候重置加速度
                velocityY = 0;
                stopScroll();
                break;
            case MotionEvent.ACTION_MOVE:
                //在ACTION_MOVE的情况下，将lastY置为0
                lastY = 0f;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (canScroll()) {
                    return super.onInterceptTouchEvent(e);
                } else {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (lastY == 0.0f) {
            lastY = e.getY();
        }
        if (isScrollEnd()) {
            if (childRecyclerView != null) {
                float deltaY = lastY - e.getY();
                if (deltaY != 0) {
                    scrollBy(0, (int) deltaY);
                }
            }
        }
        lastY = e.getY();
        return super.onTouchEvent(e);
    }

    @Override
    public boolean fling(int velX, int velY) {
        boolean fling = super.fling(velX, velY);
        if (!fling || velY <= 0) {
            velocityY = 0;
        } else {
            isStartFling = true;
            velocityY = velY;
        }
        return fling;
    }

    @Override
    public void scrollToPosition(int position) {
        if (childRecyclerView != null) {
            childRecyclerView.scrollToPosition(position);
        }
        postDelayed(() -> {
            super.scrollToPosition(position);
        }, 50);
    }

    /**
     * 初始化布局
     *
     * @param spanCount
     * @return
     */
    public GridLayoutManager initLayoutManager(int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount,
                RecyclerView.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return canScroll();
            }
        };
        setLayoutManager(gridLayoutManager);
        return gridLayoutManager;
    }

    private boolean canScroll() {
        return childRecyclerView == null || childRecyclerView.isScrollTop();
    }


    private void dispatchChildFling() {
        if (isScrollEnd() && velocityY != 0) {
            double splineFlingDistance = mFlingHelper.getSplineFlingDistance(velocityY);
            if (splineFlingDistance > totalDy) {
                childFling(mFlingHelper.getVelocityByDistance(splineFlingDistance - totalDy));
            }
        }
        totalDy = 0;
        velocityY = 0;
    }

    private void childFling(int velY) {
        if (childRecyclerView != null) {
            childRecyclerView.fling(0, velY);
        }
    }


    private ChildRecyclerView childRecyclerView;

    public void setChildRecyclerView(ChildRecyclerView childRecyclerView) {
        this.childRecyclerView = childRecyclerView;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        childRecyclerView = null;
    }

    private boolean isScrollEnd() {
        //RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
        return !canScrollVertically(1);
    }

}

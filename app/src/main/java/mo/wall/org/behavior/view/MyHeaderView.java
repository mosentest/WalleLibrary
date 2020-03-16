package mo.wall.org.behavior.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.wall.mo.utils.log.WLog;

import mo.wall.org.BuildConfig;
import mo.wall.org.R;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-17 17:28
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class MyHeaderView extends LinearLayout {
    private int lastY;
    private int lastX;
    private int lastViewHeight;

    private int currentY = 0;
    private int offY;
    private int mHeight;

    private View stopView;


    public MyHeaderView(Context context) {
        this(context, null, 0);
    }

    public MyHeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        boolean b = super.dispatchTouchEvent(event);
//        int y;
//        int x;
//        int dy;
//        int dx;
//
//        int top;
//        int topAdd;
//        int bottom;
//        int bottomAdd;
//
//        top = getTop();
//        bottom = getBottom();
//
//
//        int childCount = getChildCount();
//
//        switch (event.getAction()) {
//            //按下
//            case MotionEvent.ACTION_DOWN:
//                lastY = (int) event.getY();
//                lastX = (int) event.getX();
//                for (int i = 0; i < childCount; i++) {
//                    getChildAt(i).setEnabled(true);
//                }
//                break;
//            //移动
//            case MotionEvent.ACTION_MOVE:
//                y = (int) (event.getY());
//                x = (int) (event.getX());
//                dy = y - lastY;
//                dx = x - lastX;
//                float diffY = Math.abs(dy);
//                float diffX = Math.abs(dx);
//
//                if (diffY < diffX) {
//                    return b;
//                }
//
//                topAdd = top + dy;
//                bottomAdd = bottom + dy;
//
////                if (BuildConfig.DEBUG) {
////                    WLog.i("aaaa", "dy:" + dy
////                            + ",offY:" + offY
////                            + ",top:" + top
////                            + ",topAdd:" + topAdd
////                            + ",bottom:" + bottom
////                            + ",bottomAdd:" + bottomAdd);
////                }
//
//                if (topAdd >= -offY && bottomAdd <= mHeight) {
//                    offsetTopAndBottom(dy);
//                    currentY = dy;
//                    for (int i = 0; i < childCount; i++) {
//                        getChildAt(i).setEnabled(false);
//                    }
//                    return true;
//                } else {
//                    for (int i = 0; i < childCount; i++) {
//                        getChildAt(i).setEnabled(true);
//                    }
//                }
//                break;
//        }
//        return b;
//    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return super.onInterceptTouchEvent(event);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        boolean b = super.onTouchEvent(event);
//        return true;
//    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        stopView = findViewById(R.id.stopView);
        if (stopView != null) {
            lastViewHeight = stopView.getMeasuredHeight();
        }

        mHeight = getHeight();

        offY = mHeight - lastViewHeight;
    }

    @Override
    public void scrollTo(int x, int y) {
        WLog.i("aaaa", String.format("y:%d", y));
        if (y <= 0) {
            y = 0;
        } else if (y >= getHeight() - lastViewHeight) {
            y = getHeight() - lastViewHeight;
        }
        currentY = y;
        super.scrollTo(x, y);
    }

    public int getCurrentY() {
        return currentY;
    }

    public int getLastViewHeight() {
        return lastViewHeight;
    }
}

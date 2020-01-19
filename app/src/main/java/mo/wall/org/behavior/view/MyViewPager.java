package mo.wall.org.behavior.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import org.wall.mo.utils.log.WLog;

import mo.wall.org.BuildConfig;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-18 20:46
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class MyViewPager extends ViewPager {
    private int lastY;
    private int lastX;
    private int currentY;

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean b = super.dispatchTouchEvent(event);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean b = super.onTouchEvent(event);

        int y;
        int x;
        int dy;
        int dx;

        int top;
        int topAdd;
        int bottom;
        int bottomAdd;

        top = getTop();
        bottom = getBottom();


        int childCount = getChildCount();

        switch (event.getAction()) {
            //按下
            case MotionEvent.ACTION_DOWN:
                lastY = (int) event.getY();
                lastX = (int) event.getX();
                for (int i = 0; i < childCount; i++) {
                    getChildAt(i).setEnabled(true);
                }
                break;
            //移动
            case MotionEvent.ACTION_MOVE:
                y = (int) (event.getY());
                x = (int) (event.getX());
                dy = y - lastY;
                dx = x - lastX;
                float diffY = Math.abs(dy);
                float diffX = Math.abs(dx);

                if (diffY < diffX) {
                    return b;
                }

                topAdd = top + dy;
                bottomAdd = bottom + dy;

                int height = getHeight();
                int headerHeight = myHeaderView.getHeight();

                int lastViewHeight = myHeaderView.getLastViewHeight();


                if (BuildConfig.DEBUG) {
                    WLog.i("aaaa", "dy:" + dy
                            + ",headerHeight:" + headerHeight
                            + ",lastViewHeight:" + lastViewHeight
                            + ",top:" + top
                            + ",topAdd:" + topAdd
                            + ",bottom:" + bottom
                            + ",bottomAdd:" + bottomAdd);
                }


//                if (topAdd >= lastViewHeight
//                        && topAdd <= headerHeight - lastViewHeight) {
//                    offsetTopAndBottom(dy);
//                    currentY = dy;
//                    if (myHeaderView != null) {
//                        myHeaderView.offsetTopAndBottom(-dy);
//                    }
//                }
                break;
        }

        return b;
    }

    public int getCurrentY() {
        return currentY;
    }

    private MyHeaderView myHeaderView;

    public void setMyHeaderView(MyHeaderView myHeaderView) {
        this.myHeaderView = myHeaderView;
    }
}

package mo.wall.org.behavior.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import mo.wall.org.behavior.view.MyHeaderView;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-03-17 21:12
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class MyStopViewBehavior extends CoordinatorLayout.Behavior<TabLayout> {


    private int childHeight;

    public MyStopViewBehavior() {
    }

    public MyStopViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull TabLayout child, int layoutDirection) {
        boolean onLayoutChild = super.onLayoutChild(parent, child, layoutDirection);
        View childAt = parent.getChildAt(0);
        if (childAt instanceof MyHeaderView) {
            childHeight = childAt.getMeasuredHeight();
        }
        child.layout(0, childHeight, parent.getMeasuredWidth(), child.getMeasuredHeight() + childHeight);
        return true;
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull TabLayout child, @NonNull View dependency) {
        if (dependency instanceof MyHeaderView) {
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }


    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull TabLayout child, @NonNull View dependency) {
        int scrollY = dependency.getScrollY();
        float translationY = dependency.getTranslationY();
        int top = dependency.getTop();
        Log.i("AA", "scrollY>" + scrollY + ",translationY>" + translationY + ",top:" + top);
        child.setY(dependency.getMeasuredHeight() + top);
        return true;
    }


}

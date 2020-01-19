package mo.wall.org.behavior.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import mo.wall.org.behavior.view.MyHeaderView;

/**
 * 参考 AppBarLayout 和ScrollingViewBehavior
 */
public class MyHeaderViewScrollViewBehavior extends CoordinatorLayout.Behavior<ViewPager> {

    private int childHeight;

    /**
     * http://www.hackerav.com/?post=60261
     *
     * @param context
     * @param attrs
     */
    public MyHeaderViewScrollViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull ViewPager child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        boolean measureChild = super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        View childAt = parent.getChildAt(0);
        int lastViewHeight = 0;
        if (childAt instanceof MyHeaderView) {
            childHeight = childAt.getMeasuredHeight();
            lastViewHeight = ((MyHeaderView) childAt).getLastViewHeight();
        }
        int mode = View.MeasureSpec.getMode(parentHeightMeasureSpec);
        int i = View.MeasureSpec.makeMeasureSpec(lastViewHeight, mode);
        child.measure(parentWidthMeasureSpec, parentHeightMeasureSpec - i);
        return true;
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull ViewPager child, int layoutDirection) {
        boolean onLayoutChild = super.onLayoutChild(parent, child, layoutDirection);
        View childAt = parent.getChildAt(0);
        int lastViewHeight = 0;
        if (childAt instanceof MyHeaderView) {
            childHeight = childAt.getMeasuredHeight();
            lastViewHeight = ((MyHeaderView) childAt).getLastViewHeight();
        }
        child.layout(0, childHeight, parent.getMeasuredWidth(), parent.getMeasuredHeight() + childHeight - lastViewHeight);
        return true;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull ViewPager child, @NonNull View dependency) {
        if (dependency instanceof MyHeaderView) {
            child.offsetTopAndBottom(((MyHeaderView) dependency).getCurrentY());
        }
        return true;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ViewPager child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ViewPager child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull ViewPager child, @NonNull View dependency) {
        if (dependency instanceof MyHeaderView) {
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }
}
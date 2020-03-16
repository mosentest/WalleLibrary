package mo.wall.org.behavior.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import java.lang.ref.WeakReference;

import mo.wall.org.behavior.view.MyHeaderView;

/**
 * 参考 AppBarLayout 和ScrollingViewBehavior
 */
public class MyHeaderViewScrollViewBehavior extends CoordinatorLayout.Behavior<ViewPager> {

    private int childHeight;

    private WeakReference<View> dependentView;
    private int lastViewHeight;

    private View getDependentView() {
        return dependentView.get();
    }


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
        /**
         *
         *         int mode = View.MeasureSpec.getMode(parentHeightMeasureSpec);
         *         int i = View.MeasureSpec.makeMeasureSpec(lastViewHeight, mode);
         *         child.measure(parentWidthMeasureSpec, parentHeightMeasureSpec - i);
         */
        //获取父布局确切的值
        int height = View.MeasureSpec.getSize(parentHeightMeasureSpec);
        //减去头部的高度
        height = height - lastViewHeight;
        //转为相应的
        child.measure(parentWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        return true;
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull ViewPager child, int layoutDirection) {
        boolean onLayoutChild = super.onLayoutChild(parent, child, layoutDirection);
        View childAt = parent.getChildAt(0);
        lastViewHeight = 0;
        if (childAt instanceof MyHeaderView) {
            childHeight = childAt.getMeasuredHeight();
            lastViewHeight = ((MyHeaderView) childAt).getLastViewHeight();
        }
        child.layout(0, childHeight, parent.getMeasuredWidth(), parent.getMeasuredHeight() + childHeight - lastViewHeight);
        return true;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull ViewPager child, @NonNull View dependency) {
        child.setTranslationY(dependency.getTranslationY());
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ViewPager child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
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
            dependentView = new WeakReference<>(dependency);
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    int lastY;


    private int downY;

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull ViewPager child, @NonNull MotionEvent ev) {
        int dy = (int) ev.getY();
        View dependView = getDependentView();
        if (dependView == null) {
            return super.onInterceptTouchEvent(parent, child, ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                lastY = dy;
                if (Math.abs(lastY - downY) > 1 && dy < (dependView.getMeasuredHeight() + dependView.getTranslationY())) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull ViewPager child, @NonNull MotionEvent ev) {
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int dy = y - lastY;
                getDependentView().scrollBy(0, -dy);
                child.offsetTopAndBottom(dy);
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return super.onTouchEvent(parent, child, ev);
    }
}
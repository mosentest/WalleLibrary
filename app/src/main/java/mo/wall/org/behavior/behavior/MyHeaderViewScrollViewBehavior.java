package mo.wall.org.behavior.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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

    private static final String TAG = MyHeaderViewScrollViewBehavior.class.getSimpleName();

    private int childHeight;

    private WeakReference<MyHeaderView> dependentView;
    private int lastViewHeight;

    private MyHeaderView getDependentView() {
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
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull ViewPager child, @NonNull View dependency) {
        int scrollY = dependency.getScrollY();
        float translationY = dependency.getTranslationY();
        int top = dependency.getTop();
        Log.i("AA", "scrollY>" + scrollY + ",translationY>" + translationY + ",top:" + top);
        child.setY(dependency.getMeasuredHeight() + top);
        return true;
    }


    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull ViewPager child, @NonNull View dependency) {
        if (dependency instanceof MyHeaderView) {
            dependentView = new WeakReference<>((MyHeaderView) dependency);
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    int lastY;


    private int downY;

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull ViewPager child, @NonNull MotionEvent ev) {

        int y = (int) ev.getY();
        View dependView = getDependentView();
        if (dependView == null) {
            return super.onInterceptTouchEvent(parent, child, ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = y - lastY;
                lastY = y;
                Log.i(TAG, "dy" + dy);
                Log.i(TAG, "dependView.getMeasuredHeight():" + dependView.getMeasuredHeight());
                Log.i(TAG, "dependView.getTranslationY():" + dependView.getTranslationY());
//                if (Math.abs(lastY - downY) > 1 && dy < (dependView.getMeasuredHeight() + dependView.getTranslationY())) {
//                    return true;
//                }
                if (getDependentView().getTop() > -(getDependentView().getMeasuredHeight() - lastViewHeight) + dy
                        && getDependentView().getTop() + dy < 0) {
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
                int currentY = getDependentView().getMeasuredHeight() + getDependentView().getTop();
                int lastViewHeight = getDependentView().getLastViewHeight();
                Log.i(TAG, "currentY:" + currentY);
                Log.i(TAG, "getDependentView().getTop():" + getDependentView().getTop());
                Log.i(TAG, "lastViewHeight:" + lastViewHeight);
                Log.i(TAG, "dy:" + dy);
                if (getDependentView().getTop() + dy > -(getDependentView().getMeasuredHeight() - lastViewHeight)
                        && getDependentView().getTop() + dy < 0) {
                    getDependentView().offsetTopAndBottom(dy);
                }
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
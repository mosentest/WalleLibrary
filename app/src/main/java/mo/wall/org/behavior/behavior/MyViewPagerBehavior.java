package mo.wall.org.behavior.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import java.lang.ref.WeakReference;

import mo.wall.org.R;
import mo.wall.org.behavior.view.MyHeaderView;

/**
 * 参考 AppBarLayout 和ScrollingViewBehavior
 */
public class MyViewPagerBehavior extends CoordinatorLayout.Behavior<LinearLayout> {

    private static final String TAG = MyViewPagerBehavior.class.getSimpleName();

    /**
     * http://www.hackerav.com/?post=60261
     *
     * @param context
     * @param attrs
     */
    public MyViewPagerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        boolean measureChild = super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        /**
         *
         *         int mode = View.MeasureSpec.getMode(parentHeightMeasureSpec);
         *         int i = View.MeasureSpec.makeMeasureSpec(lastViewHeight, mode);
         *         child.measure(parentWidthMeasureSpec, parentHeightMeasureSpec - i);
         */
        //获取父布局确切的值
        int height = View.MeasureSpec.getSize(parentHeightMeasureSpec);


        //减去头部的高度
        height = height;
        //转为相应的
        child.measure(parentWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        return true;
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, int layoutDirection) {
        boolean onLayoutChild = super.onLayoutChild(parent, child, layoutDirection);
        View childAt = parent.getChildAt(0);
        child.layout(0, childAt.getBottom(), parent.getMeasuredWidth(), childAt.getBottom() + parent.getMeasuredHeight());
        return true;
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull LinearLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull LinearLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull LinearLayout child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull View dependency) {
//        int scrollY = dependency.getScrollY();
//        float translationY = dependency.getTranslationY();
        int top = dependency.getTop();
//        Log.i("AA", "scrollY>" + scrollY + ",translationY>" + translationY + ",top:" + top);
        child.setY(dependency.getMeasuredHeight() + top);
        return true;
    }


    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull View dependency) {
        if (dependency instanceof MyHeaderView) {
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    int lastY;

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull MotionEvent ev) {

        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = y - lastY;
                lastY = y;
                View childAt = parent.getChildAt(0);
                //Log.i(TAG, "onInterceptTouchEvent.dy" + dy);
                //Log.i(TAG, "onInterceptTouchEvent.childAt.getTop():" + childAt.getTop());
                //Log.i(TAG, "onInterceptTouchEvent.dependView.getMeasuredHeight():" + childAt.getMeasuredHeight());

                float translationY = child.getTranslationY();

                if (childAt.getTop() + dy > -childAt.getMeasuredHeight() && childAt.getTop() + dy < 0

                        && translationY + dy < childAt.getTop()) {
                    if (translationY + dy == childAt.getTop()) {
                        return false;
                    }
                    //在这里做判断
                    int childTop = child.getScrollY();
                    float getY = child.getY();
                    Log.i(TAG, "onInterceptTouchEvent.childTop:" + childTop);
                    Log.i(TAG, "onInterceptTouchEvent.getY:" + getY);
                    Log.i(TAG, "onInterceptTouchEvent.translationY:" + translationY);
                    Log.i(TAG, "onInterceptTouchEvent.childAt.getTop():" + childAt.getTop());
                    Log.i(TAG, "onInterceptTouchEvent.childAt.getMeasuredHeight():" + childAt.getMeasuredHeight());

                    return true;
                } else {
                    return false;
                }
            default:
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull MotionEvent ev) {
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int dy = y - lastY;
                lastY = y;
                View childAt = parent.getChildAt(0);
                //Log.i(TAG, "onTouchEvent.getDependentView().getTop():" + childAt.getTop());
                //Log.i(TAG, "onTouchEvent.dy:" + dy);
                if (childAt.getTop() + dy > -childAt.getMeasuredHeight() && childAt.getTop() + dy < 0) {
                    childAt.offsetTopAndBottom(dy);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(parent, child, ev);
    }
}
package mo.wall.org.nestedscrolling;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingParent2;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/8/12 4:06 PM
 * Description: ${DESCRIPTION}
 * History:
 * 参考学习资料 这个仅仅实现接口，用于下次复制
 * https://www.cnblogs.com/wjtaigwh/p/6398562.html
 *
 * https://github.com/543441727/MyNestedScrolling
 *
 *
 * 下一期学习
 * https://blog.csdn.net/lib739449500/article/details/51850276
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class NestedScrollingParentViewGroup extends ViewGroup implements NestedScrollingParent2, NestedScrollingChild2 {

    public NestedScrollingParentViewGroup(Context context) {
        super(context);
    }

    public NestedScrollingParentViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollingParentViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NestedScrollingParentViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    //====================================
    //NestedScrollingParent2
    //====================================
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return false;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {

    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

    }


    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes) {
        return false;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int nestedScrollAxes) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {

    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {

    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }


    //====================================
    //NestedScrollingChild2
    //====================================

    /**
     * 开始嵌套滚动
     *
     * @param axes
     * @param type
     * @return
     */
    @Override
    public boolean startNestedScroll(int axes, int type) {
        return false;
    }

    /**
     * 停止嵌套滚动
     *
     * @param type
     */
    @Override
    public void stopNestedScroll(int type) {

    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return false;
    }

    /**
     * 触摸滚动相关
     *
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param offsetInWindow
     * @param type           ViewCompat. TYPE_TOUCH：正常的屏幕触控驱动事件
     *                       ViewCompat. TYPE_NON_TOUCH：非手指触碰手势输入类型，通常是手指离开屏幕后的惯性滑动事件
     * @return
     */
    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow, int type) {
        return false;
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow,
                                           int type) {
        return false;
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {

    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return false;
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return false;
    }

    @Override
    public void stopNestedScroll() {

    }

    @Override
    public boolean hasNestedScrollingParent() {
        return false;
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return false;
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return false;
    }

    /**
     * 惯性滚动相关
     *
     * @param velocityX
     * @param velocityY
     * @param consumed
     * @return
     */
    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return false;
    }
}

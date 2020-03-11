package org.wall.mo.compat.statusbar.view;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import org.wall.mo.compat.statusbar.StatusBarUtil;

/**
 * 适配透明状态栏的情况
 * https://blog.csdn.net/u014418171/article/details/81223681
 */
public class StatusBarHeightRelativeLayout extends RelativeLayout {

    private int statusBarHeight;

    public StatusBarHeightRelativeLayout(Context context) {
        super(context);
        init();
    }

    public StatusBarHeightRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusBarHeightRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StatusBarHeightRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBarHeight = StatusBarUtil.getStatusBarHeight(getContext());
        } else {
            //低版本 直接设置0
            statusBarHeight = 0;
        }
        setPadding(getPaddingLeft(), getPaddingTop() + statusBarHeight, getPaddingRight(), getPaddingBottom());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
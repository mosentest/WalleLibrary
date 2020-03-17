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
        if (y <= 0) {
            y = 0;
        } else if (y >= getHeight() - lastViewHeight) {
            y = getHeight() - lastViewHeight;
        }
        currentY = y;
        super.scrollTo(x, y);
    }

    public int getLastViewHeight() {
        return lastViewHeight;
    }
}

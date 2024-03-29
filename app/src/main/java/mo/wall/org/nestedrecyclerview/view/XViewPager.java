package mo.wall.org.nestedrecyclerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-14 10:16
 * Description:
 * History: https://blog.csdn.net/u011002668/article/details/72884893
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class XViewPager extends ViewPager {


    public XViewPager(@NonNull Context context) {
        super(context);
    }

    public XViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}

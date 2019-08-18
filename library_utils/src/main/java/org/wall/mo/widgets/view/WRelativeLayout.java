package org.wall.mo.widgets.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-08-16 22:39
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class WRelativeLayout extends RelativeLayout {
    public WRelativeLayout(Context context) {
        super(context);
    }

    public WRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}

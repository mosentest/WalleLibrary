package org.wall.mo.compat.statusbar;

import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-13 10:28
 * Description: 启动页全屏适配问题
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class StatusBar28 {


    public void splash(Activity activity) {
        /**
         * https://www.jianshu.com/p/87e74de4eaf2
         * 适配9.0启动页面的问题
         */
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            activity.getWindow().setAttributes(lp);
        }
    }
}

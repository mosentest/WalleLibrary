package org.wall.mo.utils;

import android.os.SystemClock;

/**
 * 判断屏幕两次有效点击事件
 * Created by luohao on 2017/7/10.
 *
 * @author luohao
 */
public class ClickUtil {
    /**
     * 双击拦截的时间间隔
     */
    private static final long DOUBLE_CLICK_LIMIT_TIME = 300L;
    /**
     * 最后一次点击的时间
     */
    private static long mLastClickTime = 0;

    public static synchronized boolean isFastDoubleClick() {
        long clickSpace = SystemClock.elapsedRealtime() - mLastClickTime;
        mLastClickTime = SystemClock.elapsedRealtime();
        return clickSpace <= DOUBLE_CLICK_LIMIT_TIME;
    }
}

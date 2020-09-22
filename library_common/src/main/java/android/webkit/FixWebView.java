package android.webkit;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/8/27 9:49 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class FixWebView extends WebView {
    public FixWebView(Context context) {
        super(context);
    }

    public FixWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FixWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FixWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }
}

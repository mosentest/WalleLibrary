package org.wall.mo.base.fragment;

import androidx.databinding.ViewDataBinding;

import org.wall.mo.utils.log.WLog;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-17 09:45
 * Description: MaxLifecycle实现懒加载
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class MaxLifecycleFragment<B extends ViewDataBinding> extends InterceptActBackFragment<B> {

    public final static String TAG = MaxLifecycleFragment.class.getSimpleName();

    private boolean isLoad;

    @Override
    public void onResume() {
        super.onResume();
        tryLoad();
    }

    private void tryLoad() {
        if (!isLoad) {
            WLog.i(TAG, getName() + ".tryLoad");
            onFragmentFirstVisible();
            isLoad = true;
        }
    }

    public abstract void onFragmentFirstVisible();
}

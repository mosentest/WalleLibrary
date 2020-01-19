package org.wall.mo.base.cview;

import org.wall.mo.base.interfaces.IDialogView;

import java.lang.ref.WeakReference;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-19 14:51
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class LoadDialogView {

    /**
     * 展示dialog次数
     */
    protected int showDialogCount = 0;


    private WeakReference<IDialogView> mLoadView;

    public LoadDialogView(IDialogView dialogView) {
        this.mLoadView = new WeakReference<>(dialogView);
    }

    public void onDetachView() {
        if (mLoadView != null) {
            mLoadView.clear();
            mLoadView = null;
        }
    }

    public void loadStart(boolean showLoading, String tipMsg) {
        if (!showLoading) {
            return;
        }
        showDialogCount++;
        if (showDialogCount == 1) {
            if (isAdd()) {
                mLoadView.get().showDialog(tipMsg);
            }
        }
    }

    public void loadEnd(boolean showLoading) {
        if (!showLoading) {
            return;
        }
        //错误提示，让自己实现，不在底层处理
        showDialogCount--;
        if (showDialogCount == 0) {
            if (isAdd()) {
                mLoadView.get().hideDialog();
            }
        }
    }

    public boolean isAdd() {
        return mLoadView != null && mLoadView.get() != null;
    }
}

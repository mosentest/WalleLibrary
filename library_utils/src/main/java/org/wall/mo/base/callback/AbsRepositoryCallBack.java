package org.wall.mo.base.callback;

import org.wall.mo.base.interfaces.ILoadView;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-26 20:37
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class AbsRepositoryCallBack<Body, Fail> {

    /**
     * 为了感知activity的生命周期
     */
    private ILoadView mBaseView;

    private int mFlag;

    protected boolean mLoading;

    public AbsRepositoryCallBack(ILoadView baseView, int flag, String tipMsg, boolean loading) {
        this.mBaseView = baseView;
        this.mFlag = flag;
        this.mLoading = loading;
        if (mBaseView != null) {
            mBaseView.onLoadStart(mLoading, mFlag, tipMsg);
        }
    }

    public AbsRepositoryCallBack(ILoadView baseView, int flag) {
        this(baseView, flag, "", false);
    }

    public void onRepositorySuccess(Body bean) {
        if (mBaseView != null) {
            mBaseView.onLoadSuccess(mLoading, mFlag, bean);
            onSuccess(mFlag, bean);
        }
    }

    public void onRepositoryFail(Fail bean) {
        if (mBaseView != null) {
            mBaseView.onLoadFail(mLoading, mFlag);
        }
        if (!showErrorMsg()) {
            return;
        }
        if (mBaseView != null
                && onInterceptLoadFail(mFlag, bean)) {
            return;
        }
        if (toast()) {
            if (mBaseView != null) {
                mBaseView.onLoadToastFail(mFlag, bean);
            }
        } else {
            if (mBaseView != null) {
                mBaseView.onLoadDialogFail(mFlag, bean);
            }
        }
    }


    /**
     * 成功的回调，在处理的时候，需要判断getView方法是否为null
     *
     * @param flag
     * @param bean
     */
    public abstract void onSuccess(int flag, Body bean);

    /**
     * 拦截错误信息处理，在处理的时候，需要判断getView方法是否为null
     *
     * @param flag
     * @param bean
     * @return
     */
    public boolean onInterceptLoadFail(int flag, Fail bean) {
        return false;
    }

    /**
     * 是否展示错误信息，默认为true
     *
     * @return
     */
    private boolean showErrorMsg() {
        return true;
    }

    /**
     * 默认toast
     *
     * @return
     */
    private boolean toast() {
        return true;
    }
}

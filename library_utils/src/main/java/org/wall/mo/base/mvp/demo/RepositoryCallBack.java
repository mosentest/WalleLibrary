package org.wall.mo.base.mvp.demo;

import org.wall.mo.base.mvp.BaseContract;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-26 20:37
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class RepositoryCallBack<T, F> {

    /**
     * 为了感知activity的生命周期
     */
    private BaseContract.BaseView mBaseView;

    private int mFlag;

    protected boolean mLoading;

    public RepositoryCallBack(BaseContract.BaseView baseView, int flag, String tipMsg, boolean loading) {
        this.mBaseView = baseView;
        this.mFlag = flag;
        this.mLoading = loading;
        if (mBaseView != null) {
            mBaseView.onRequestStart(mLoading, mFlag, tipMsg);
        }
    }

    public RepositoryCallBack(BaseContract.BaseView baseView, int flag) {
        this(baseView, flag, "", false);
    }

    public void onSuccess(T bean) {
        if (mBaseView != null) {
            mBaseView.onRequestSuccess(mLoading, mFlag, bean);
        }
    }

    public void onFail(F bean) {
        if (mBaseView != null) {
            mBaseView.onRequestFail(mLoading, mFlag);
        }
        if (interceptError()) {
            if (mBaseView != null) {
                mBaseView.onRequestInterceptFail(mFlag, bean);
            }
        } else {
            if (showError()) {
                if (toast()) {
                    if (mBaseView != null) {
                        mBaseView.onRequestToastFail(mFlag, bean);
                    }
                } else {
                    if (mBaseView != null) {
                        mBaseView.onRequestDialogFail(mFlag, bean);
                    }
                }
            }
        }

    }

    /***
     * 是否拦截错误信息
     * @return
     */
    public boolean interceptError() {
        return false;
    }

    /**
     * 是否展示错误信息
     *
     * @return
     */
    public boolean showError() {
        return true;
    }

    /**
     * 是否用toast展示错误信息
     *
     * @return
     */
    public boolean toast() {
        return true;
    }
}

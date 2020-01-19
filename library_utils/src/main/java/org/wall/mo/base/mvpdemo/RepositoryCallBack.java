package org.wall.mo.base.mvpdemo;

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
public abstract class RepositoryCallBack<Body, Fail> {

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
            mBaseView.onLoadStart(mLoading, mFlag, tipMsg);
        }
    }

    public RepositoryCallBack(BaseContract.BaseView baseView, int flag) {
        this(baseView, flag, "", false);
    }

    public void onSuccess(Body bean) {
        if (mBaseView != null) {
            mBaseView.onLoadSuccess(mLoading, mFlag, bean);
        }
    }

    public void onFail(Fail bean) {
        if (mBaseView != null) {
            mBaseView.onLoadFail(mLoading, mFlag);
        }
        if (interceptError()) {
            if (mBaseView != null) {
                mBaseView.onLoadInterceptFail(mFlag, bean);
            }
        } else {
            if (showError()) {
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

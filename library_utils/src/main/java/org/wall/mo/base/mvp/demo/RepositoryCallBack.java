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

    private BaseContract.BaseView mBaseView;

    private int mFlag;

    public RepositoryCallBack(BaseContract.BaseView baseView, int flag, String tipMsg) {
        this.mBaseView = baseView;
        this.mFlag = flag;
        if (mBaseView != null) {
            mBaseView.onRequestStart(mFlag, tipMsg);
        }
    }

    public RepositoryCallBack(BaseContract.BaseView baseView, int flag) {
        this(baseView, flag, "");
    }

    public void onSuccess(T bean) {
        if (mBaseView != null) {
            mBaseView.onRequestSuccess(mFlag, bean);
        }
    }

    public void onFail(F bean) {
        if (mBaseView != null) {
            mBaseView.onRequestFail(mFlag);
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

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
            mBaseView.onLoadStart(mLoading, tipMsg);
        }
    }

    public AbsRepositoryCallBack(ILoadView baseView, int flag, boolean loading) {
        this(baseView, flag, "", loading);
    }

    public AbsRepositoryCallBack(ILoadView baseView, boolean loading) {
        this(baseView, -1, "", loading);
    }

    public AbsRepositoryCallBack(ILoadView baseView, int flag) {
        this(baseView, flag, "", false);
    }

    public AbsRepositoryCallBack(ILoadView baseView) {
        this(baseView, -1, "", false);
    }

    public void repositorySuccess(Body bean) {
        if (mBaseView != null) {
            mBaseView.onLoadFinish(mLoading);
            onSuccess(mFlag, bean);
        }
    }

    public void repositoryFail(Fail bean) {
        if (mBaseView != null) {
            mBaseView.onLoadFinish(mLoading);
            onError(mFlag, bean);
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
     * @param flag
     * @param bean
     */
    public abstract void onError(int flag, Fail bean);

}

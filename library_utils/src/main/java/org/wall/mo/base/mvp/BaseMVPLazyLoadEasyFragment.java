package org.wall.mo.base.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import android.view.View;

import org.wall.mo.base.fragment.LazyLoadEasyFragment;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-11-28 15:09
 * Description:
 * History: 基础的LazyLoadEasyFragment mvp
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class BaseMVPLazyLoadEasyFragment<presenter extends BaseContract.BasePresenter,
        B extends ViewDataBinding>
        extends LazyLoadEasyFragment<B>
        implements BaseContract.BaseView {


    public presenter mPresenter;

    /**
     * 展示dialog次数
     */
    private volatile int showDialogCount = 0;


    protected abstract presenter createPresenter();

    @Override
    public void initView(View rootView, Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            //这里处理一次
            mPresenter.attachView(this);
            mPresenter.onCreate(savedInstanceState);
        }
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mPresenter.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            boolean viewNull = mPresenter.onResume();
            if (!viewNull) {
                mPresenter.detachView();
                //这里处理再一次
                mPresenter.attachView(this);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onCurDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.onDestroy();
        }
        mPresenter = null;
    }

    protected abstract void onCurDestroy();


    @Override
    public void onRequestFail(boolean showLoading, int flag) {
        if (!showLoading) {
            return;
        }
        //错误提示，让自己实现，不在底层处理
        showDialogCount--;
        if (showDialogCount < 0) {
            showDialogCount = 0;
            hideDialog();
        }
    }

    @Override
    public void onRequestStart(boolean showLoading, int flag, String tipMsg) {
        if (!showLoading) {
            return;
        }
        showDialogCount++;
        if (showDialogCount == 1) {
            showDialog(tipMsg);
        }
    }

    @Override
    public void onRequestSuccess(boolean showLoading, int flag, Object model) {
        if (!showLoading) {
            return;
        }
        showDialogCount--;
        if (showDialogCount < 0) {
            showDialogCount = 0;
            hideDialog();
        }
    }
}


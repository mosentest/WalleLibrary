package org.wall.mo.base.mvp;

import android.os.Bundle;
import androidx.annotation.Nullable;

import org.wall.mo.base.activity.AbsAppCompatActivity;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-08-18 15:07
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class BaseMVPAppCompatActivity<view extends BaseContract.BaseView,
        presenter extends BaseContract.BasePresenter> extends AbsAppCompatActivity
        implements BaseContract.BaseView {

    protected presenter mPresenter;

    /**
     * 展示dialog次数
     */
    protected volatile int showDialogCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            //这里处理一次
            mPresenter.attachView((view) this);
            mPresenter.onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            boolean viewNull = mPresenter.onResume();
            if (!viewNull) {
                mPresenter.detachView();
                //这里处理再一次
                mPresenter.attachView((view) this);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onCurDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.onDestroy();
        }
        mPresenter = null;
    }

    public abstract void onCurDestroy();


    public abstract presenter createPresenter();

    @Override
    public void onRequestFail(int flag, String msg) {
        //错误提示，让自己实现，不在底层处理
        showDialogCount--;
        if (showDialogCount < 0) {
            showDialogCount = 0;
            hideDialog();
        }
    }

    @Override
    public void onRequestStart(int flag, String tipMsg) {
        showDialogCount++;
        if (showDialogCount == 1) {
            showDialog(tipMsg);
        }
    }

    @Override
    public void onRequestSuccess(int flag, Object model) {
        showDialogCount--;
        if (showDialogCount < 0) {
            showDialogCount = 0;
            hideDialog();
        }
    }
}

package org.wall.mo.base.mvp;

import android.os.Bundle;

import org.wall.mo.base.activity.AbsDataBindingAppCompatActivity;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-08-18 15:07
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class BaseMVPAppCompatActivity
        <V extends BaseContract.BaseView,
                P extends BaseContract.BasePresenter<V>,
                B extends ViewDataBinding>
        extends AbsDataBindingAppCompatActivity<B> {

    @Nullable
    protected P mPresenter;

    public abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            //这里处理一次
            mPresenter.attachView((V) this);
        }
        super.onCreate(savedInstanceState);
        if (mPresenter != null) {
            //这里处理一次
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
            boolean viewNull = mPresenter.isAttach();
            if (!viewNull) {
                mPresenter.detachView();
                //这里处理再一次
                mPresenter.attachView((V) this);
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
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }
}

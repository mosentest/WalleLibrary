package org.wall.mo.base.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import android.os.Parcelable;
import android.view.View;

import org.wall.mo.base.cview.LoadDialogView;
import org.wall.mo.base.fragment.InterceptActBackFragment;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-11-28 15:09
 * Description:
 * History: 基础的mvp
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class BaseMVPInterceptActBackFragment
        <V extends BaseContract.BaseView,
                presenter extends BaseContract.BasePresenter,
                B extends ViewDataBinding,
                nextP extends Parcelable>
        extends InterceptActBackFragment<B, nextP> {


    protected presenter mPresenter;

    protected abstract presenter createPresenter();

    @Override
    public void initView(View rootView, Bundle savedInstanceState) {

        mPresenter = createPresenter();
        if (mPresenter != null) {
            //这里处理一次
            mPresenter.attachView((V) this);
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
            boolean viewNull = mPresenter.isAttach();
            if (!viewNull) {
                mPresenter.detachView();
                //这里处理再一次
                mPresenter.attachView((V) this);
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
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.onDestroy();
        }
        mPresenter = null;
    }
}


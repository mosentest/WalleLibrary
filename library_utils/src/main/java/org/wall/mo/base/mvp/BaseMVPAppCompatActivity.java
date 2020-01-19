package org.wall.mo.base.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import org.wall.mo.base.activity.AbsDataBindingAppCompatActivity;
import org.wall.mo.base.cview.LoadDialogView;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-08-18 15:07
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class BaseMVPAppCompatActivity<
        presenter extends BaseContract.BasePresenter,
        B extends ViewDataBinding>
        extends AbsDataBindingAppCompatActivity<B>
        implements BaseContract.BaseView {

    protected presenter mPresenter;

    protected LoadDialogView loadDialogView;


    public abstract presenter createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        loadDialogView = new LoadDialogView(this);

        mPresenter = createPresenter();
        if (mPresenter != null) {
            //这里处理一次
            mPresenter.attachView(this);
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
            boolean viewNull = mPresenter.onResume();
            if (!viewNull) {
                mPresenter.detachView();
                //这里处理再一次
                mPresenter.attachView(this);
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
        if (loadDialogView != null) {
            loadDialogView.onDetachView();
        }
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.onDestroy();
        }
        mPresenter = null;
    }

    public abstract void onCurDestroy();


    @Override
    public void onLoadFail(boolean showLoading, int flag) {
        if (loadDialogView != null) {
            loadDialogView.loadEnd(showLoading);
        }
    }

    @Override
    public void onLoadStart(boolean showLoading, int flag, String tipMsg) {
        if (loadDialogView != null) {
            loadDialogView.loadStart(showLoading, tipMsg);
        }
    }

    @Override
    public void onLoadSuccess(boolean showLoading, int flag, Object model) {
        if (loadDialogView != null) {
            loadDialogView.loadEnd(showLoading);
        }
    }
}

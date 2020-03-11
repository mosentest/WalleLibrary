package org.wall.mo.base.mvpdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;

import androidx.databinding.ViewDataBinding;

import org.wall.mo.base.mvp.BaseMVPMaxLifecycleFragment;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-11-28 15:26
 * Description:
 * History:
 * <p>
 * 都压在fragment里面编写代码
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class DemoFragment extends BaseMVPMaxLifecycleFragment<DemoContract.Presenter, ViewDataBinding, Parcelable>
        implements DemoContract.View {


    @Override
    protected DemoContract.Presenter createPresenter() {
        return new DemoPresenter();
    }


    @Override
    protected void onAbsV4Attach(Context context) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }


    @Override
    public int getTopBarTitleViewId() {
        return 0;
    }

    @Override
    public int getTopBarBackViewId() {
        return 0;
    }

    @Override
    public void onFragmentFirstVisible() {

    }

    /**
     * 会通过activity的onIntent传值过来
     *
     * @param bundle
     */
    @Override
    public void loadIntentData(Bundle bundle) {

    }

    @Override
    public void handleSubMessage(Message msg) {

    }

    @Override
    public void showShortToast(String msg) {

    }

    @Override
    public void showLongToast(String msg) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void onLoadDialogFail(int flag, Object failObj) {

    }

    @Override
    public void onLoadToastFail(int flag, Object failObj) {

    }


    @Override
    protected void onCurDestroy() {

    }

    @Override
    public void statusLoadingView() {

    }

    @Override
    public void statusNetWorkView() {

    }

    @Override
    public void statusErrorView(int type, String msg) {

    }

    @Override
    public void statusContentView() {

    }
}

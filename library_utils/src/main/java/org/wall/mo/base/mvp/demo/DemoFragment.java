package org.wall.mo.base.mvp.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Message;

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
public class DemoFragment extends BaseMVPMaxLifecycleFragment<DemoContract.Presenter, ViewDataBinding>
        implements DemoContract.View {


    @Override
    protected DemoContract.Presenter createPresenter() {
        return null;
    }


    @Override
    protected void onAbsV4Attach(Context context) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void handleSubMessage(Message msg) {

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
    protected void parseIntentData(Intent intent) {

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
    public void onRequestDialogFail(int flag, Object failObj) {

    }

    @Override
    public void onRequestToastFail(int flag, Object failObj) {

    }

    @Override
    public void onRequestInterceptFail(int flag, Object failObj) {

    }

    @Override
    public void onFragmentFirstVisible() {

    }

    @Override
    protected void onCurDestroy() {

    }

}

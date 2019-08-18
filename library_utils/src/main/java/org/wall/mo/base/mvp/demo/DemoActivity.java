package org.wall.mo.base.mvp.demo;

import android.content.Intent;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;

import org.wall.mo.base.mvp.BaseMVPActivity;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-08-18 15:35
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class DemoActivity extends BaseMVPActivity<DemoContract.View, DemoContract.Presenter>
        implements DemoContract.View {

    @Override
    public DemoContract.Presenter createPresenter() {
        return new DemoPresenter();
    }

    @Override
    public void parseIntentData(Intent intent) {

    }

    @Override
    public int getContainerViewId() {
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
    public Fragment createFragment() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {

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
    public void onClick(View v) {

    }

    @Override
    public void onRequestSuccess(int flag, Object model) {
        super.onRequestSuccess(flag, model);
    }

    @Override
    public void onRequestFail(int flag, String msg) {
        super.onRequestFail(flag, msg);
    }

    @Override
    public void onRequestStart(int flag, String tipMsg) {
        super.onRequestStart(flag, tipMsg);
    }
}

package org.wall.mo.base.mvp.demo;

import android.content.Intent;
import android.os.Message;
import android.view.View;

import androidx.fragment.app.Fragment;

import org.wall.mo.base.activity.AbsWithV4FragmentActivity;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-08-18 15:35
 * Description: ${DESCRIPTION}
 * History:
 * <p>
 * activity只是做一个架
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class DemoActivity extends AbsWithV4FragmentActivity {


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
        return DemoFragment.newInstance(getIntent().getExtras());
    }

    @Override
    public int getLayoutId() {
        return 0;
    }


    @Override
    public void handleSubMessage(Message msg) {

    }

    @Override
    public void onClick(View v) {

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
}

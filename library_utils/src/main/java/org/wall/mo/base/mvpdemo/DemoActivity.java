package org.wall.mo.base.mvpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;

import androidx.databinding.ViewDataBinding;
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
public class DemoActivity extends AbsWithV4FragmentActivity<ViewDataBinding, Parcelable> {


    @Override
    public void loadIntentData(Intent intent) {

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
        Intent intent = getIntent();
        Bundle extras = null;
        if (intent != null) {
            extras = intent.getExtras();
        }
        return DemoFragment.newInstance(new DemoFragment(), extras);
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

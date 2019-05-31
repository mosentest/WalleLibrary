package org.wall.mo.base.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import org.wall.mo.base.fragment.IAttachActivity;
import org.wall.mo.utils.StringUtils;
import org.wall.mo.utils.log.WLog;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/5/30 上午9:53
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class AbsAppCompatActivity extends AppCompatActivity implements IAttachActivity {

    private final static String TAG = AbsAppCompatActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WLog.i(TAG, getName() + ".onCreate savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
        setContentView(getLayoutId());
        parseIntentData();
        initView(savedInstanceState);
        initData();
        initClick();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        WLog.i(TAG, getName() + ".onRestart");
    }


    @Override
    protected void onStart() {
        super.onStart();
        WLog.i(TAG, getName() + ".onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        WLog.i(TAG, getName() + ".onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        WLog.i(TAG, getName() + ".onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        WLog.i(TAG, getName() + ".onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WLog.i(TAG, getName() + ".onDestroy");
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        WLog.i(TAG, getName() + ".onAttachFragment fragment is " + (fragment != null ? fragment.getClass().getSimpleName() : "--"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        WLog.i(TAG, getName() + ".onSaveInstanceState outState is " + StringUtils.isNULL(outState));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        WLog.i(TAG, getName() + ".onRestoreInstanceState savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        WLog.i(TAG, getName() + ".onActivityResult");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WLog.i(TAG, getName() + ".onRequestPermissionsResult");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        WLog.i(TAG, getName() + ".onConfigurationChanged");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WLog.i(TAG, getName() + ".onNewIntent");
        parseIntentData();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        WLog.i(TAG, getName() + ".onLowMemory");
    }

    public String getName() {
        return getClass().getSimpleName();
    }


    public abstract int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);

    public abstract void parseIntentData();

    public abstract void initData();

    public abstract void initClick();
}

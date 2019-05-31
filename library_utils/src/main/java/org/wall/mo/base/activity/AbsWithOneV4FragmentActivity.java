package org.wall.mo.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.wall.mo.base.fragment.IFragmentInterceptAct;
import org.wall.mo.utils.log.WLog;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/5/31 11:45 AM
 * Description: ${DESCRIPTION}
 * History: 一个fragment的Activity
 * * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class AbsWithOneV4FragmentActivity extends AbsAppCompatActivity {

    private static final String TAG = AbsWithOneV4FragmentActivity.class.getSimpleName();

    protected Fragment fragment;

    @Override
    public void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            if (fragment == null) {
                fragment = createFragment();
            }
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            //用getName作为tag
            fragmentTransaction.replace(getContainerViewId(), fragment, getName());
            fragmentTransaction.commit();
        } else {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            Fragment fragmentByTag = supportFragmentManager.findFragmentByTag(getName());
            fragmentTransaction.show(fragmentByTag);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void parseIntentData() {
        Intent intent = getIntent();
        if (fragment instanceof IFragmentInterceptAct
                && ((IFragmentInterceptAct) fragment).onFragmentInterceptGetIntent(intent)) {
            WLog.i(TAG, "Fragment getIntent");
        } else {
            parseIntentData(intent);
        }
    }

    public abstract void parseIntentData(Intent intent);


    /**
     * 替换 fragment 的 container View Id
     *
     * @return
     */
    public abstract int getContainerViewId();

    /**
     * 创建一个
     * fragment
     * * @return
     */
    public abstract Fragment createFragment();


    @Override
    public void onBackPressed() {
        if (fragment instanceof IFragmentInterceptAct
                && ((IFragmentInterceptAct) fragment).onFragmentInterceptOnBackPressed()) {
            WLog.i(TAG, "Fragment onBackPressed");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragment = null;
    }

}

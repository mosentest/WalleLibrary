package org.wall.mo.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import org.wall.mo.base.StartActivityCompat;
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

    protected static final String TAG = AbsWithOneV4FragmentActivity.class.getSimpleName();

    protected Fragment fragment;

    /**
     * 展示返回键按钮
     */
    protected boolean showTopBarBack;
    /**
     * 上个页面传递的参数集合对象
     */
    protected Parcelable parcelableNextExtra;

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
        //设置返回键
        findViewById(getTopBarBackViewId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void parseIntentData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        //解析参数
        String title = intent.getStringExtra(StartActivityCompat.NEXT_TITLE);
        boolean showBack = intent.getBooleanExtra(StartActivityCompat.NEXT_SHOW_BACK, true);
        parcelableNextExtra = intent.getParcelableExtra(StartActivityCompat.NEXT_PARCELABLE);
        //消费参数
        setTopBarTitle(title);
        setTopBarBack(showBack);
        this.showTopBarBack = showBack;
        if (fragment instanceof IFragmentInterceptAct) {
            ((IFragmentInterceptAct) fragment).onFragmentInterceptNextParcelableExtra(parcelableNextExtra);
        }
        //其他参数
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
     * 获取topBar的标题 id
     *
     * @return
     */
    public abstract int getTopBarTitleViewId();

    /**
     * 获取topBar的返回键 id
     *
     * @return
     */
    public abstract int getTopBarBackViewId();


    /**
     * 创建一个
     * fragment
     * * @return
     */
    public abstract Fragment createFragment();

    /**
     * 设置返回键是否显示
     *
     * @param show
     */
    @Override
    public void setTopBarBack(boolean show) {
        showTopBarBack = show;
        findViewById(getTopBarBackViewId()).setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    @Override
    public void setTopBarTitle(String title) {
        View topBarTitleView = findViewById(getTopBarTitleViewId());
        if (topBarTitleView instanceof TextView) {
            ((TextView) topBarTitleView).setText(title);
        }
    }

    @Override
    public void onBackPressed() {
        if (!showTopBarBack) {
            return;
        }
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

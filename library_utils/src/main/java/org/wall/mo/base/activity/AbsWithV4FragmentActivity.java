package org.wall.mo.base.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import org.wall.mo.base.StartActivityCompat;
import org.wall.mo.base.fragment.IFragmentInterceptAct;
import org.wall.mo.base.nextextra.BaseNextExtra;
import org.wall.mo.utils.BuildConfig;
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
public abstract class AbsWithV4FragmentActivity<B extends ViewDataBinding> extends AbsDataBindingAppCompatActivity<B> {

    protected static final String TAG = AbsWithV4FragmentActivity.class.getSimpleName();

    protected Fragment fragment;

    /**
     * 上个页面传递的参数集合对象
     */
    private BaseNextExtra mNextParcelable;

    private String mTitle;
    /**
     * 展示返回键按钮
     */
    private boolean mShowBack;


    @Override
    public void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            if (fragment == null) {
                fragment = createFragment();
            }
            int containerViewId = getContainerViewId();
            if (fragment == null || containerViewId == 0) {
                return;
            }
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            //用getName作为tag
            fragmentTransaction.replace(containerViewId, fragment, getName());
            fragmentTransaction.commit();
        } else {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            Fragment fragmentByTag = supportFragmentManager.findFragmentByTag(getName());
            if (fragmentByTag == null) {
                return;
            }
            fragmentTransaction.show(fragmentByTag);
            fragmentTransaction.commit();
        }
        //设置返回键
        int topBarBackViewId = getTopBarBackViewId();
        if (topBarBackViewId != 0) {
            findViewById(topBarBackViewId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void parseIntentData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        //其他参数
        if (fragment instanceof IFragmentInterceptAct
                && ((IFragmentInterceptAct) fragment).onFragmentInterceptGetIntent(intent)) {
            if (BuildConfig.DEBUG) {
                WLog.i(TAG, "Fragment getIntent");
            }
        } else {
            //解析参数
            Bundle extras = intent.getExtras();
            this.mTitle = extras.getString(StartActivityCompat.NEXT_TITLE);
            this.mShowBack = extras.getBoolean(StartActivityCompat.NEXT_SHOW_BACK, true);
            this.mNextParcelable = extras.getParcelable(StartActivityCompat.NEXT_PARCELABLE);
            //消费参数
            setTopBarTitle();
            setTopBarBack();
            //其他值
            parseIntentData(intent);
        }
    }

    public abstract void parseIntentData(Intent intent);


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putString(StartActivityCompat.NEXT_TITLE, mTitle);
            outState.putBoolean(StartActivityCompat.NEXT_SHOW_BACK, mShowBack);
            outState.putParcelable(StartActivityCompat.NEXT_PARCELABLE, mNextParcelable);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            this.mTitle = savedInstanceState.getString(StartActivityCompat.NEXT_TITLE);
            this.mShowBack = savedInstanceState.getBoolean(StartActivityCompat.NEXT_SHOW_BACK, true);
            this.mNextParcelable = savedInstanceState.getParcelable(StartActivityCompat.NEXT_PARCELABLE);
            setTopBarTitle();
            setTopBarBack();
        }
    }


    /**
     * 替换 fragment 的 container View Id
     * 默认值-1
     *
     * @return
     */
    public abstract int getContainerViewId();

    /**
     * 获取topBar的标题 id
     * 默认值-1
     *
     * @return
     */
    public abstract int getTopBarTitleViewId();

    /**
     * 获取topBar的返回键 id
     * 默认值-1
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
     */
    @Override
    public void setTopBarBack() {
        int topBarBackViewId = getTopBarBackViewId();
        if (topBarBackViewId != 0) {
            findViewById(topBarBackViewId).setVisibility(mShowBack ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置标题
     */
    @Override
    public void setTopBarTitle() {
        int topBarTitleViewId = getTopBarTitleViewId();
        if (topBarTitleViewId != 0) {
            View topBarTitleView = findViewById(topBarTitleViewId);
            if (topBarTitleView instanceof TextView) {
                ((TextView) topBarTitleView).setText(mTitle);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!mShowBack) {
            return;
        }
        if (fragment instanceof IFragmentInterceptAct
                && ((IFragmentInterceptAct) fragment).onFragmentInterceptOnBackPressed()) {
            if (BuildConfig.DEBUG) {
                WLog.i(TAG, "Fragment onBackPressed");
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public Parcelable getNextExtra() {
        return mNextParcelable;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNextParcelable = null;
        fragment = null;
    }


}

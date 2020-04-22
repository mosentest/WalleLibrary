package org.wall.mo.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.wall.mo.base.helper.StartActivityCompat;
import org.wall.mo.base.fragment.InterceptActBackFragment;
import org.wall.mo.base.interfaces.IFragmentInterceptAct;
import org.wall.mo.utils.BuildConfig;
import org.wall.mo.utils.log.WLog;

import java.util.List;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/5/31 11:45 AM
 * Description: ${DESCRIPTION}
 * History: 一个fragment的Activity
 * * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class AbsWithV4FragmentActivity<B extends ViewDataBinding, acceptT extends Parcelable>
        extends AbsDataBindingAppCompatActivity<B> {

    protected static final String TAG = AbsWithV4FragmentActivity.class.getSimpleName();

    @Nullable
    protected Fragment mFragment;

    /**
     * 上个页面传递的参数集合对象
     */
    @Nullable
    private acceptT mNextParcelable;

    @Nullable
    private String mTitle;
    /**
     * 展示返回键按钮
     */
    @Nullable
    private boolean mShowBack;


    @Override
    public void initView(Bundle savedInstanceState) {

        /**
         *        if (savedInstanceState == null) {
         *             if (mFragment == null) {
         *                 mFragment = createFragment();
         *             }
         *             int containerViewId = getContainerViewId();
         *             if (mFragment == null || containerViewId == 0) {
         *                 return;
         *             }
         *             FragmentManager supportFragmentManager = getSupportFragmentManager();
         *             FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
         *             //用getName作为tag
         *             fragmentTransaction.replace(containerViewId, mFragment, getName());
         *             fragmentTransaction.commit();
         *         } else {
         *             FragmentManager supportFragmentManager = getSupportFragmentManager();
         *             FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
         *             Fragment fragmentByTag = supportFragmentManager.findFragmentByTag(getName());
         *             if (fragmentByTag == null) {
         *                 return;
         *             }
         *             mFragment = fragmentByTag;
         *             fragmentTransaction.replace(containerViewId, fragmentByTag);
         *             try {
         *                  fragmentTransaction.commit();
         *              } catch (Exception e) {
         *                  fragmentTransaction.commitAllowingStateLoss();
         *              }
         *         }
         */
        if (savedInstanceState != null) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            }
        }

        if (mFragment == null) {
            mFragment = createFragment();
        }
        int containerViewId = getContainerViewId();
        if (mFragment == null || containerViewId == 0) {
            return;
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        //用getName作为tag
        fragmentTransaction.replace(containerViewId, mFragment, getName());
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
            fragmentTransaction.commitAllowingStateLoss();
        }

        //设置返回键
        int topBarBackViewId = getTopBarBackViewId();
        if (topBarBackViewId != 0) {
            findViewById(topBarBackViewId).setOnClickListener(v -> onBackPressed());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFragment == null) {
            onBackPressed();
        }
    }

    @Override
    public void parseIntentData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        if (mFragment instanceof IFragmentInterceptAct
                && ((IFragmentInterceptAct) mFragment).onFragmentInterceptGetIntent(intent)) {
            if (BuildConfig.DEBUG) {
                WLog.i(TAG, "Fragment getIntent");
            }
        } else {
            //解析参数
            Bundle extras = intent.getExtras();
            if (extras != null) {
                this.mTitle = extras.getString(StartActivityCompat.NEXT_TITLE);
                this.mShowBack = extras.getBoolean(StartActivityCompat.NEXT_SHOW_BACK, true);
                this.mNextParcelable = extras.getParcelable(StartActivityCompat.NEXT_PARCELABLE);
                //消费参数
                setTopBarTitle();
                setTopBarBack();
                //其他值
                loadIntentData(intent);
                if (mFragment instanceof InterceptActBackFragment) {
                    //调用这个方法，为了在newIntent更新数据
                    ((InterceptActBackFragment) mFragment).loadIntentData(extras);
                }
            }
        }
    }

    public abstract void loadIntentData(Intent intent);


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
    @IdRes
    public abstract int getContainerViewId();

    /**
     * 获取topBar的标题 id
     * 默认值-1
     *
     * @return
     */
    @IdRes
    public abstract int getTopBarTitleViewId();

    /**
     * 获取topBar的返回键 id
     * 默认值-1
     *
     * @return
     */
    @IdRes
    public abstract int getTopBarBackViewId();


    /**
     * 创建一个
     * fragment
     * * @return
     */
    @Nullable
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
        if (mFragment instanceof IFragmentInterceptAct
                && ((IFragmentInterceptAct) mFragment).onFragmentInterceptOnBackPressed()) {
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
        mFragment = null;
    }


}

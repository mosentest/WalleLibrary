package org.wall.mo.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wall.mo.base.StartActivityCompat;
import org.wall.mo.base.nextextra.BaseNextExtra;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/5/31 1:11 PM
 * Description: ${DESCRIPTION}
 * History: 继承拦截回调的fragment
 * 只有普通的fragment才要拦截，懒加载的不需要
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public abstract class InterceptActBackFragment extends AbsDataBindingV4Fragment implements IFragmentInterceptAct {
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
    public boolean onFragmentInterceptGetIntent(Intent intent) {
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
        return false;
    }


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
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            this.mTitle = savedInstanceState.getString(StartActivityCompat.NEXT_TITLE);
            this.mShowBack = savedInstanceState.getBoolean(StartActivityCompat.NEXT_SHOW_BACK, true);
            this.mNextParcelable = savedInstanceState.getParcelable(StartActivityCompat.NEXT_PARCELABLE);
            setTopBarTitle();
            setTopBarBack();
        }
    }

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
    public boolean onFragmentInterceptOnBackPressed() {
        if (!mShowBack) {
            return true;
        }
        return false;
    }

    /**
     * 获取topBar的标题 id
     * 默认值0
     *
     * @return
     */
    public abstract int getTopBarTitleViewId();

    /**
     * 获取topBar的返回键 id
     * 默认值0
     *
     * @return
     */
    public abstract int getTopBarBackViewId();


    protected abstract void parseIntentData(Intent intent);


    @Override
    public void onDestroy() {
        super.onDestroy();
        mNextParcelable = null;
    }
}

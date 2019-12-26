package org.wall.mo.base.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import org.wall.mo.base.StartActivityCompat;
import org.wall.mo.base.nextextra.AbsNextExtra;

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

    private AbsNextExtra parcelableNextExtra;

    private boolean showTopBarBack;

    @Override
    public boolean onFragmentInterceptGetIntent(Intent intent) {
        //解析参数
        String title = intent.getStringExtra(StartActivityCompat.NEXT_TITLE);
        boolean showBack = intent.getBooleanExtra(StartActivityCompat.NEXT_SHOW_BACK, true);
        parcelableNextExtra = intent.getParcelableExtra(StartActivityCompat.NEXT_PARCELABLE);
        //消费参数
        setTopBarTitle(title);
        setTopBarBack(showBack);
        this.showTopBarBack = showBack;
        //其他值
        parseIntentData(intent);
        return false;
    }


    /**
     * 设置返回键是否显示
     *
     * @param show
     */
    @Override
    public void setTopBarBack(boolean show) {
        showTopBarBack = show;
        int topBarBackViewId = getTopBarBackViewId();
        if (topBarBackViewId != -1) {
            findViewById(topBarBackViewId).setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    @Override
    public void setTopBarTitle(String title) {
        int topBarTitleViewId = getTopBarTitleViewId();
        if (topBarTitleViewId != -1) {
            View topBarTitleView = findViewById(topBarTitleViewId);
            if (topBarTitleView instanceof TextView) {
                ((TextView) topBarTitleView).setText(title);
            }
        }
    }

    @Override
    public boolean onFragmentInterceptOnBackPressed() {
        if (!showTopBarBack) {
            return true;
        }
        return false;
    }

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


    protected abstract void parseIntentData(Intent intent);


}

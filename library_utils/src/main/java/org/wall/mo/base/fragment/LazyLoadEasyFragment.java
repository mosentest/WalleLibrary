package org.wall.mo.base.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;

import org.wall.mo.utils.log.WLog;

/**
 * https://www.jianshu.com/p/0e2d746e3a3d
 */
public abstract class LazyLoadEasyFragment extends AbsDataBindingV4Fragment {

    public final static String TAG = LazyLoadEasyFragment.class.getSimpleName();


    private boolean isViewCreated; // 界面是否已创建完成
    private boolean isVisibleToUser; // 是否对用户可见
    private boolean isDataLoaded; // 数据是否已请求

    // 实现具体的数据请求逻辑
    protected abstract void loadData();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        tryLoadData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        tryLoadData();
    }

    public void tryLoadData() {
        if (isViewCreated && isVisibleToUser && !isDataLoaded) {
            WLog.i(TAG, getName() + ".tryLoadData");
            loadData();
            isDataLoaded = true;
        }
    }
}
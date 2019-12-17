package org.wall.mo.base.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.wall.mo.utils.log.WLog;

import java.util.List;

/**
 * https://github.com/SheHuan/LazyLoadFragment/blob/master/app/src/main/java/com/shh/fragmentdemo/base/LazyLoadFragment.java
 */
public abstract class LazyLoadEasyProFragment extends AbsV4Fragment {

    public final static String TAG = LazyLoadEasyProFragment.class.getSimpleName();

    private boolean isViewCreated; // 界面是否已创建完成
    private boolean isVisibleToUser; // 是否对用户可见
    private boolean isDataLoaded; // 数据是否已请求, isNeedReload()返回false的时起作用
    private boolean isHidden = true; // 记录当前fragment的是否隐藏

    // 实现具体的数据请求逻辑
    protected abstract void loadData();

    /**
     * 使用ViewPager嵌套fragment时，切换ViewPager回调该方法
     *
     * @param isVisibleToUser
     */
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

    /**
     * 使用show()、hide()控制fragment显示、隐藏时回调该方法
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden = hidden;
        if (!hidden) {
            tryLoadData1();
        }
    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     *
     * @return
     */
    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return fragment == null || (fragment instanceof LazyLoadEasyProFragment && ((LazyLoadEasyProFragment) fragment).isVisibleToUser);
    }

    /**
     * ViewPager场景下，当前fragment可见，如果其子fragment也可见，则尝试让子fragment加载请求
     */
    private void dispatchParentVisibleState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof LazyLoadEasyProFragment && ((LazyLoadEasyProFragment) child).isVisibleToUser) {
                ((LazyLoadEasyProFragment) child).tryLoadData();
            }
        }
    }

    /**
     * fragment再次可见时，是否重新请求数据，默认为flase则只请求一次数据
     *
     * @return
     */
    protected boolean isNeedReload() {
        return false;
    }

    /**
     * ViewPager场景下，尝试请求数据
     */
    public void tryLoadData() {
        if (isViewCreated && isVisibleToUser && isParentVisible() && (isNeedReload() || !isDataLoaded)) {
            WLog.i(TAG, getName() + ".tryLoadData");
            loadData();
            isDataLoaded = true;
            dispatchParentVisibleState();
        }
    }

    /**
     * show()、hide()场景下，当前fragment没隐藏，如果其子fragment也没隐藏，则尝试让子fragment请求数据
     */
    private void dispatchParentHiddenState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof LazyLoadEasyProFragment && !((LazyLoadEasyProFragment) child).isHidden) {
                ((LazyLoadEasyProFragment) child).tryLoadData1();
            }
        }
    }

    /**
     * show()、hide()场景下，父fragment是否隐藏
     *
     * @return
     */
    private boolean isParentHidden() {
        Fragment fragment = getParentFragment();
        if (fragment == null) {
            return false;
        } else if (fragment instanceof LazyLoadEasyProFragment && !((LazyLoadEasyProFragment) fragment).isHidden) {
            return false;
        }
        return true;
    }

    /**
     * show()、hide()场景下，尝试请求数据
     */
    public void tryLoadData1() {
        if (!isParentHidden() && (isNeedReload() || !isDataLoaded)) {
            WLog.i(TAG, getName() + ".tryLoadData1");
            loadData();
            isDataLoaded = true;
            dispatchParentHiddenState();
        }
    }

    @Override
    public void onDestroy() {
        isViewCreated = false;
        isVisibleToUser = false;
        isDataLoaded = false;
        isHidden = true;
        super.onDestroy();
    }
}
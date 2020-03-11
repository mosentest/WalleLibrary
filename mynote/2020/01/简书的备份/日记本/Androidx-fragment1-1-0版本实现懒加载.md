升级为Androidx后，fragment在1.1.0发生一些变化
setUserVisibleHint方法给标注为Deprecated提示用setMaxLifecycle实现懒加载
```
@Deprecated
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!mUserVisibleHint && isVisibleToUser && mState < STARTED
                && mFragmentManager != null && isAdded() && mIsCreated) {
            mFragmentManager.performPendingDeferredStart(this);
        }
        mUserVisibleHint = isVisibleToUser;
        mDeferStart = mState < STARTED && !isVisibleToUser;
        if (mSavedFragmentState != null) {
            // Ensure that if the user visible hint is set before the Fragment has
            // restored its state that we don't lose the new value
            mSavedUserVisibleHint = isVisibleToUser;
        }
    }
```
在以前我们实现viewpager的懒加载，都通过setUserVisibleHint和onActivityCreated结合一起判断当前fragment是否展示出来
```

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
```
现在在新版本的fragment不需要判断那么，直接在onResume判断是否展示
```
    private boolean isLoad;

    @Override
    public void onResume() {
        super.onResume();
        tryLoad();
    }

    private void tryLoad() {
        if (!isLoad) {
            WLog.i(TAG, getName() + ".tryLoad");
            loadData();
            isLoad = true;
        }
    }

    public abstract void loadData();
```
仅仅这样还不够，因为刚才提到setMaxLifecycle方法来实现的，我们还需要调整viewpager的adapter来控制setMaxLifecycle
```
public class MaxLifecyclePagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public MaxLifecyclePagerAdapter(FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public void setData(List<Fragment> fragments, List<String> titles) {
        this.fragments = fragments;
        this.titles = titles;
    }
}
```
就是在super方法传进FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT方法就能实现在onResume实现懒加载方式，比之前代码简单多了

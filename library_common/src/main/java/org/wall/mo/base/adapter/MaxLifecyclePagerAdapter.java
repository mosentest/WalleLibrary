package org.wall.mo.base.adapter;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

/**
 * https://blog.csdn.net/c6E5UlI1N/article/details/90307961
 * https://www.jianshu.com/p/116e5749bb3e
 * <p>
 * <p>
 * hongyang 博客
 * https://mp.weixin.qq.com/s/MOWdbI5IREjQP1Px-WJY1Q
 * <p>
 * #每日一问 | Fragment 是如何被存储与恢复的？
 * https://www.wanandroid.com/wenda/show/12574
 * #每日一问 ViewPager 这个流传广泛的写法，其实是有问题的！
 */
public abstract class MaxLifecyclePagerAdapter extends FragmentPagerAdapter {

    private SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public MaxLifecyclePagerAdapter(FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    /**
     * https://mp.weixin.qq.com/s/MOWdbI5IREjQP1Px-WJY1Q
     *
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    /**
     * https://www.jianshu.com/p/ad810a0bef6b
     * @param activity
     * @param viewPager
     * @param position
     * @param defaultResult
     * @return
     */
    public static Fragment instantiateFragment(FragmentActivity activity, ViewPager viewPager, int position, Fragment defaultResult) {
        String tag = "android:switcher:" + viewPager.getId() + ":" + position;
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        return fragment == null ? defaultResult : fragment;
    }
}
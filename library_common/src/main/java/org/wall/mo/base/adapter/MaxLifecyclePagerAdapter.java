package org.wall.mo.base.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

/**
 * https://blog.csdn.net/c6E5UlI1N/article/details/90307961
 * https://www.jianshu.com/p/116e5749bb3e
 */
public abstract class MaxLifecyclePagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public MaxLifecyclePagerAdapter(FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public void setData(List<Fragment> fragments, List<String> titles) {
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull View container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
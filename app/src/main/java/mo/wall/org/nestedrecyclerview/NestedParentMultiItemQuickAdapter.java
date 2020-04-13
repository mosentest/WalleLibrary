package mo.wall.org.nestedrecyclerview;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;

import org.wall.mo.base.activity.AbsDataBindingAppCompatActivity;
import org.wall.mo.base.adapter.MaxLifecyclePagerAdapter;
import org.wall.mo.utils.log.WLog;

import java.util.ArrayList;
import java.util.List;

import mo.wall.org.R;
import mo.wall.org.databinding.ActivityNestedRecyclerviewBinding;
import mo.wall.org.nestedrecyclerview.fragment.NestedRecyclerViewFragment;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-06 22:11
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class NestedParentMultiItemQuickAdapter
        extends BaseMultiItemQuickAdapter<NestedParentMultiItemEntity, BaseViewHolder>
        implements LifecycleObserver {

//    private final ArrayList<Fragment> viewFragments;

    private final ArrayList<String> titles;

    private AbsDataBindingAppCompatActivity mActivity;

//    private LinearLayout mBottomFl;

    MaxLifecyclePagerAdapter lifecyclePagerAdapter;

    private Lifecycle mLifecycle;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NestedParentMultiItemQuickAdapter(AbsDataBindingAppCompatActivity activity, Lifecycle lifecycle, List<NestedParentMultiItemEntity> data) {
        super(data);
        this.mLifecycle = lifecycle;
        mLifecycle.addObserver(this);
        mActivity = activity;

        addItemType(1, R.layout.activity_nested_recyclerview_header);
        addItemType(2, R.layout.activity_nested_recyclerview_header_item);
        addItemType(3, R.layout.activity_nested_recyclerview_title);
        addItemType(4, R.layout.activity_nested_recyclerview_mid_item);
        addItemType(5, R.layout.activity_nested_recyclerview_bottom);


        titles = new ArrayList<>();

//        viewFragments = new ArrayList<>();

        titles.add("推荐");
        titles.add("微资讯");
        titles.add("疾病百科");
        titles.add("饮食");
        titles.add("养生");
    }

    @Override
    protected void convert(BaseViewHolder helper, NestedParentMultiItemEntity item) {
        switch (item.getItemType()) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                TabLayout tabLayout = helper.getView(R.id.tabLayout);
                ViewPager viewPager = helper.getView(R.id.viewPager);

                if (lifecyclePagerAdapter == null) {
//                    for (int i = 0; i < titles.size(); i++) {
//                        String s = titles.get(i);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("title", s);
//                        viewFragments.add(MaxLifecyclePagerAdapter.instantiateFragment(mActivity, viewPager, i, NestedRecyclerViewFragment.newInstance(bundle)));
//                    }
                    lifecyclePagerAdapter = new MaxLifecyclePagerAdapter(mActivity.getSupportFragmentManager()) {

                        @NonNull
                        @Override
                        public Fragment getItem(int position) {
                            String s = titles.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putString("title", s);
                            return NestedRecyclerViewFragment.newInstance(bundle);
                            //return viewFragments.get(position);
                        }

                        @Override
                        public int getCount() {
                            return titles.size();
                        }

                        @Nullable
                        @Override
                        public CharSequence getPageTitle(int position) {
                            return titles.get(position);
                        }
                    };
                    viewPager.setAdapter(lifecyclePagerAdapter);
                    lifecyclePagerAdapter.notifyDataSetChanged();
                }
                //int size = titles.size();
                //viewPager.setOffscreenPageLimit(size);
                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        setChildRV(position);
                    }
                });
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setTabIndicatorFullWidth(false);
                viewPager.post(() -> {
                    int currentItem = viewPager.getCurrentItem();
                    setChildRV(currentItem);
                });
                break;
        }
    }

    private void setChildRV(int currentItem) {
        NestedRecyclerViewFragment fragment = (NestedRecyclerViewFragment) lifecyclePagerAdapter.getRegisteredFragment(currentItem);
        ActivityNestedRecyclerviewBinding mViewDataBinding = ((NestedRecyclerViewActivity) mActivity).mViewDataBinding;
        if (mViewDataBinding != null && fragment != null) {
            mViewDataBinding.parentView.setChildRecyclerView(fragment.getChildRecyclerView());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
//        if (viewFragments != null) {
//            viewFragments.clear();
//        }
        if (titles != null) {
            titles.clear();
        }
        lifecyclePagerAdapter = null;
        if (mLifecycle != null) {
            mLifecycle.removeObserver(this);
        }
        mActivity = null;
        WLog.i("mo", "onDestroy");
    }
}

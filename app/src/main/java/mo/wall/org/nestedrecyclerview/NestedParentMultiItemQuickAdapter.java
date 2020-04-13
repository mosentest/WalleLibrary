package mo.wall.org.nestedrecyclerview;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

    private final ArrayList<Fragment> viewFragments;
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

        viewFragments = new ArrayList<>();

        titles.add("推荐");
        titles.add("微资讯");
        titles.add("疾病百科");
        titles.add("饮食");
        titles.add("养生");

        for (int i = 0; i < titles.size(); i++) {
            String s = titles.get(i);
            Bundle bundle = new Bundle();
            bundle.putString("title", s);
            viewFragments.add(NestedRecyclerViewFragment.newInstance(bundle));
        }

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
                    lifecyclePagerAdapter = new MaxLifecyclePagerAdapter(mActivity.getSupportFragmentManager()) {
                    };
                    lifecyclePagerAdapter.setData(viewFragments, titles);
                    lifecyclePagerAdapter.notifyDataSetChanged();
                    viewPager.setAdapter(lifecyclePagerAdapter);
                }


                //int size = titles.size();
                //viewPager.setOffscreenPageLimit(size);
                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        NestedRecyclerViewFragment fragment = (NestedRecyclerViewFragment) viewFragments.get(position);
                        if (mActivity instanceof NestedRecyclerViewActivity) {
                            ActivityNestedRecyclerviewBinding mViewDataBinding = ((NestedRecyclerViewActivity) mActivity).mViewDataBinding;
                            if (mViewDataBinding != null && mViewDataBinding.parentView != null) {
                                mViewDataBinding.parentView.setChildRecyclerView(fragment.getChildRecyclerView());
                            }

                        }

                    }
                });
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setTabIndicatorFullWidth(false);

                viewPager.post(() -> {
                    int currentItem = viewPager.getCurrentItem();
                    NestedRecyclerViewFragment fragment = (NestedRecyclerViewFragment) viewFragments.get(currentItem);
                    ActivityNestedRecyclerviewBinding mViewDataBinding = ((NestedRecyclerViewActivity) mActivity).mViewDataBinding;
                    if (mViewDataBinding != null && mViewDataBinding.parentView != null) {
                        mViewDataBinding.parentView.setChildRecyclerView(fragment.getChildRecyclerView());
                    }
                });
                break;
        }
    }

    private LoadView mLoadView;

    public interface LoadView {
        public void loadBottom(ViewGroup viewGroup, int position);
    }

    public void setLoadView(LoadView loadView) {
        this.mLoadView = loadView;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        if (viewFragments != null) {
            viewFragments.clear();
        }
        if (titles != null) {
            titles.clear();
        }
        if (mLifecycle != null) {
            mLifecycle.removeObserver(this);
        }
        mActivity = null;
        WLog.i("mo", "onDestroy");
    }
}

package mo.wall.org.nestedrecyclerview;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
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
import mo.wall.org.nestedrecyclerview.fragment.NestedRecyclerViewFragment;
import mo.wall.org.nestedrecyclerview.view.ChildRecyclerView;
import mo.wall.org.nestedrecyclerview.view.ParentRecyclerView;

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

    private AbsDataBindingAppCompatActivity mActivity;

    protected List<String> titles = new ArrayList<>();
    protected List<Fragment> viewFragments = new ArrayList<>();

    protected MaxLifecyclePagerAdapter lifecyclePagerAdapter;


    protected ChildRecyclerView childRecyclerView;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NestedParentMultiItemQuickAdapter(AbsDataBindingAppCompatActivity activity, List<NestedParentMultiItemEntity> data) {
        super(data);
        mActivity = activity;
        addItemType(1, R.layout.activity_nested_recyclerview_header);
        addItemType(2, R.layout.activity_nested_recyclerview_header_item);
        addItemType(3, R.layout.activity_nested_recyclerview_title);
        addItemType(4, R.layout.activity_nested_recyclerview_mid_item);
        addItemType(5, R.layout.activity_nested_recyclerview_bottom);


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
                    lifecyclePagerAdapter = new MaxLifecyclePagerAdapter(mActivity.getSupportFragmentManager());
                    //通过post之后在设置
                    viewPager.post(() -> {
                        lifecyclePagerAdapter.setData(viewFragments, titles);
                        viewPager.setAdapter(lifecyclePagerAdapter);
                        int size = titles.size();
                        viewPager.setOffscreenPageLimit(size);
                        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                            @Override
                            public void onPageSelected(int position) {
                                super.onPageSelected(position);
                                NestedRecyclerViewFragment fragment = (NestedRecyclerViewFragment) viewFragments.get(position);
                                childRecyclerView = fragment.getChildRecyclerView();
                            }
                        });
                        tabLayout.setupWithViewPager(viewPager);
                        tabLayout.setTabIndicatorFullWidth(false);
                        int currentItem = viewPager.getCurrentItem();
                        NestedRecyclerViewFragment fragment = (NestedRecyclerViewFragment) viewFragments.get(currentItem);
                        //传递ParentRecyclerView
                        fragment.setParentRecyclerView((ParentRecyclerView) getRecyclerView());
                        childRecyclerView = fragment.getChildRecyclerView();
                    });
                }
                break;
        }
    }


    public ChildRecyclerView getChildRecyclerView() {
        return childRecyclerView;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        WLog.i("mo", "onDestroy");
        mActivity = null;
        childRecyclerView = null;
        titles.clear();
        viewFragments.clear();
    }
}

package mo.wall.org.nestedrecyclerview;

import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.wall.mo.base.adapter.MaxLifecyclePagerAdapter;
import org.wall.mo.base.mvp.BaseMVPAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import mo.wall.org.R;
import mo.wall.org.databinding.ActivityNestedRecyclerviewBinding;
import mo.wall.org.nestedrecyclerview.fragment.NestedRecyclerViewFragment;
import mo.wall.org.nestedrecyclerview.view.ParentRecyclerView;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-06 20:34
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class NestedRecyclerViewActivity extends
        BaseMVPAppCompatActivity<NestedRecyclerViewPresenter, ActivityNestedRecyclerviewBinding>
        implements NestedRecyclerViewContract.View {

    NestedParentMultiItemQuickAdapter multiItemQuickAdapter;


    protected List<String> titles = new ArrayList<>();
    protected List<Fragment> viewFragments = new ArrayList<>();

    protected MaxLifecyclePagerAdapter lifecyclePagerAdapter;

    private View bottomView;


    @Override
    public NestedRecyclerViewPresenter createPresenter() {
        return new NestedRecyclerViewPresenter();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_nested_recyclerview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewDataBinding.topView.tvTopBarLeftBack.setVisibility(View.VISIBLE);
        mViewDataBinding.topView.tvTopBarLeftBack.setOnClickListener(v -> {
            onBackPressed();
        });
        mViewDataBinding.topView.tvTopBarTitle.setText("NestedRecyclerView");


        multiItemQuickAdapter = new NestedParentMultiItemQuickAdapter(this, null);

        multiItemQuickAdapter.bindToRecyclerView(mViewDataBinding.parentView);

        GridLayoutManager gridLayoutManager = mViewDataBinding.parentView.initLayoutManager(12);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                NestedParentMultiItemEntity nestedMultiItemEntity = multiItemQuickAdapter.getData().get(position);
                int itemType = nestedMultiItemEntity.getItemType();
                switch (itemType) {
                    case 1:
                    case 3:
                    case 5:
                        return 12;

                    case 4:
                        return 4;
                }
                return 3;
            }
        });

        //监听生命周期
        if (multiItemQuickAdapter != null) {
            getLifecycle().addObserver(multiItemQuickAdapter);
        }

        multiItemQuickAdapter.setLoadView(new NestedParentMultiItemQuickAdapter.LoadView() {
            @Override
            public void loadBottom(ViewGroup viewGroup, int position) {

                viewGroup.removeAllViews();
                viewGroup.addView(bottomView);
            }
        });

        mPresenter.load();
    }

    private void bottomView() {

        List<String> titles = new ArrayList<>();
        List<Fragment> viewFragments = new ArrayList<>();

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

        if (bottomView != null) {
            bottomView = null;
        }
        bottomView = LayoutInflater.from(this).inflate(R.layout.activity_nested_recyclerview_bottom_sub, null);

        TabLayout tabLayout = bottomView.findViewById(R.id.tabLayout);
        ViewPager viewPager = bottomView.findViewById(R.id.viewPager);
        viewPager.setId(viewPager.getId());

//        viewPager.removeAllViews();

        MaxLifecyclePagerAdapter lifecyclePagerAdapter = new MaxLifecyclePagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                Bundle bundle = new Bundle();
//                bundle.putString("title", titles.get(position));
//                return NestedRecyclerViewFragment.newInstance(bundle);
//            }
        };
        lifecyclePagerAdapter.setData(viewFragments, titles);
        viewPager.setAdapter(lifecyclePagerAdapter);


        viewPager.post(new Runnable() {
            @Override
            public void run() {
                int size = titles.size();
                viewPager.setOffscreenPageLimit(size);
                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        NestedRecyclerViewFragment fragment = (NestedRecyclerViewFragment) viewFragments.get(position);
                        if (mViewDataBinding != null) {
                            mViewDataBinding.parentView.setChildRecyclerView(fragment.getChildRecyclerView());
                        }
                    }
                });
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setTabIndicatorFullWidth(false);
                int currentItem = viewPager.getCurrentItem();
                NestedRecyclerViewFragment fragment = (NestedRecyclerViewFragment) viewFragments.get(currentItem);
                if (mViewDataBinding != null) {
                    mViewDataBinding.parentView.setChildRecyclerView(fragment.getChildRecyclerView());
                }
            }
        });
    }

    @Override
    public void parseIntentData() {

    }

    @Override
    public void handleSubMessage(Message msg) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setTopBarTitle() {

    }

    @Override
    public void setTopBarBack() {

    }

    @Override
    public void showShortToast(String msg) {

    }

    @Override
    public void showLongToast(String msg) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public Parcelable getNextExtra() {
        return null;
    }


    @Override
    public void onLoadDialogFail(int flag, Object failObj) {

    }

    @Override
    public void onLoadToastFail(int flag, Object failObj) {

    }


    @Override
    public void onCurDestroy() {
        if (multiItemQuickAdapter != null) {
            getLifecycle().removeObserver(multiItemQuickAdapter);
        }
    }

    @Override
    public void showData(List<NestedParentMultiItemEntity> itemEntityList) {
        multiItemQuickAdapter.setNewData(itemEntityList);
        bottomView();
    }

    @Override
    public void statusLoadingView() {

    }

    @Override
    public void statusNetWorkView() {

    }

    @Override
    public void statusErrorView(int type, String msg) {

    }

    @Override
    public void statusContentView() {

    }
}

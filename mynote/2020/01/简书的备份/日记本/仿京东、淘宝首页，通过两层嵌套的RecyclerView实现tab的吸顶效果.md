原文地址
[仿京东、淘宝首页，通过两层嵌套的RecyclerView实现tab的吸顶效果]([https://www.jianshu.com/p/034955ab236f?utm_campaign=hugo&utm_medium=reader_share&utm_content=note&utm_source=qq](https://www.jianshu.com/p/034955ab236f?utm_campaign=hugo&utm_medium=reader_share&utm_content=note&utm_source=qq)
)
前短时间看到这个大佬写了这个文章，由于大佬用kt写的，我项目还是用java代码编写的，只能参考大佬写的思路自己写了一遍,由于我的砖石不够，没办法放视频，我只能贴个图
![示例](https://upload-images.jianshu.io/upload_images/12139254-feabde8d863d4492.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
下面是我部分代码的逻辑

ParentRecyclerView.java
```
public ParentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mFlingHelper = new FlingHelper(context);

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //如果父RecyclerView fling过程中已经到底部，需要让子RecyclerView滑动神域的fling
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    dispatchChildFling();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isStartFling) {
                    totalDy = 0;
                    isStartFling = false;
                }
                //在RecyclerView fling情况下，记录当前RecyclerView在y轴的便宜
                totalDy += dy;
            }
        });
    }
```
另外我的viewpager里面是fragment不是直接view
```
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
```
因为我把activtiy传到adapter里面了，所以实现了LifecycleObserver接口类
```

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        WLog.i("mo", "onDestroy");
        mActivity = null;
        childRecyclerView = null;
        titles.clear();
        viewFragments.clear();
    }
```
在activity处理
```   
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
        getLifecycle().addObserver(multiItemQuickAdapter);
```

别的就不多了，反正就是抄人代码
最后我的代码[WalleLibrary](https://github.com/moz1q1/WalleLibrary)



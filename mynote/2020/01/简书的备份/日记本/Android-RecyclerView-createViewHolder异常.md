项目用的是这个人的框架
[https://github.com/CymChad/BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)

测试发现rv底层抛出了一个异常，不断滑动加载更多出现的

ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)

看了下大佬的框架好像没人解决过这个问题
![image.png](https://upload-images.jianshu.io/upload_images/12139254-9a089ebf95260c49.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
错误异常如下代码

```
java.lang.IllegalStateException: ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)
	at android.support.v7.widget.RecyclerView$Adapter.createViewHolder(RecyclerView.java:6687)
	at android.support.v7.widget.RecyclerView$Recycler.tryGetViewHolderForPositionByDeadline(RecyclerView.java:5869)
	at android.support.v7.widget.RecyclerView$Recycler.getViewForPosition(RecyclerView.java:5752)
	at android.support.v7.widget.RecyclerView$Recycler.getViewForPosition(RecyclerView.java:5748)
	at android.support.v7.widget.LinearLayoutManager$LayoutState.next(LinearLayoutManager.java:2232)
	at android.support.v7.widget.LinearLayoutManager.layoutChunk(LinearLayoutManager.java:1559)
	at android.support.v7.widget.LinearLayoutManager.fill(LinearLayoutManager.java:1519)
	at android.support.v7.widget.LinearLayoutManager.scrollBy(LinearLayoutManager.java:1333)
	at android.support.v7.widget.LinearLayoutManager.scrollVerticallyBy(LinearLayoutManager.java:1077)
	at android.support.v7.widget.RecyclerView.scrollByInternal(RecyclerView.java:1815)
	at android.support.v7.widget.RecyclerView.onTouchEvent(RecyclerView.java:3076)
	at android.view.View.dispatchTouchEvent(View.java:9939)
	at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2663)
	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2344)
	at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2669)
	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2358)
	at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2669)
	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2358)
	at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2669)
	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2358)
	at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2669)
	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2358)
	at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2669)
	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2358)
	at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2669)
	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2358)
	at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2669)
	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2358)
	at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2669)
	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2358)
	at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2669)
	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2358)
	at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2669)
	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2358)
	at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2669)
	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2358)
	at com.android.internal.policy.DecorView.superDispatchTouchEvent(DecorView.java:411)
	at com.android.internal.policy.PhoneWindow.superDispatchTouchEvent(PhoneWindow.java:1810)
```

百度搜了几篇文章都说

![image.png](https://upload-images.jianshu.io/upload_images/12139254-2ce060a4b5754d4f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

可是我看了作者的代码本来就这样写

![image.png](https://upload-images.jianshu.io/upload_images/12139254-ae3bbeca1d76833e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

怀着好奇的跟踪了下源码，源码路径
/frameworks/support/v7/recyclerview/src/main/java/androidx/recyclerview/widget/RecyclerView.java

```
/**
6739         * This method calls {@link #onCreateViewHolder(ViewGroup, int)} to create a new
6740         * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
6741         *
6742         * @see #onCreateViewHolder(ViewGroup, int)
6743         */
6744        @NonNull
6745        public final VH createViewHolder(@NonNull ViewGroup parent, int viewType) {
6746            try {
6747                TraceCompat.beginSection(TRACE_CREATE_VIEW_TAG);
6748                final VH holder = onCreateViewHolder(parent, viewType);
6749                if (holder.itemView.getParent() != null) {
6750                    throw new IllegalStateException("ViewHolder views must not be attached when"
6751                            + " created. Ensure that you are not passing 'true' to the attachToRoot"
6752                            + " parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
6753                }
6754                holder.mItemViewType = viewType;
6755                return holder;
6756            } finally {
6757                TraceCompat.endSection();
6758            }
6759        }
```

从代码来看holder.itemView.getParent() 不为null就会抛出这个异常，然后我再看了看final VH holder = onCreateViewHolder(parent, viewType);
可能我很久没写代码，发现onCreateViewHolder是要自己去实现逻辑的

```
 /**
6659         * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
6660         * an item.
6661         * <p>
6662         * This new ViewHolder should be constructed with a new View that can represent the items
6663         * of the given type. You can either create a new View manually or inflate it from an XML
6664         * layout file.
6665         * <p>
6666         * The new ViewHolder will be used to display items of the adapter using
6667         * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
6668         * different items in the data set, it is a good idea to cache references to sub views of
6669         * the View to avoid unnecessary {@link View#findViewById(int)} calls.
6670         *
6671         * @param parent The ViewGroup into which the new View will be added after it is bound to
6672         *               an adapter position.
6673         * @param viewType The view type of the new View.
6674         *
6675         * @return A new ViewHolder that holds a View of the given view type.
6676         * @see #getItemViewType(int)
6677         * @see #onBindViewHolder(ViewHolder, int)
6678         */
6679        @NonNull
6680        public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);
```

然后我又看了下作者封装的这个方法

![image.png](https://upload-images.jianshu.io/upload_images/12139254-0eb956d5ee42bb69.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

好奇按百度出来的博客跟了下源码LayoutInflater源码，的确如此

![image.png](https://upload-images.jianshu.io/upload_images/12139254-83d9f0033cc581aa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

又看了下RecyclerView的ViewHolder，创建ViewHolder的时候会传递itemView

![image.png](https://upload-images.jianshu.io/upload_images/12139254-b07839e9f4f5b9c6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

问题可能是复用的时候调用了onCreateViewHolder，onCreateViewHolder出现神经病，认为itemView有parent，或者是rv缓存问题，或者我分析方向不对

因为我就复现过2次，后面就没复现了就按大佬说缓存下，原本项目中没设置setOffscreenPageLimit，后面有时间再研究，可能我比较菜
```
fragment在viewpager容器里来回切换，如果没有设置，会导致重复实例化，重复加载数据，
建议在设置一个缓存，
解决方式：给page



mViewPager.setOffscreenPageLimit(3);
为什么重复实例化会出现这个问题，现在我的需求就是需要重新刷新这个fragment，怎么处理？
```


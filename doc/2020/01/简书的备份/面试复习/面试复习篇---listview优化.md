现在都潮流用kotlin，我也用kotlin练手加复习以前的概念

今天好端端，我的studio kotlin plugin出bug了，搞的我没意思
![image.png](https://upload-images.jianshu.io/upload_images/12139254-f37595cb25132a9f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

Plugin Error: Kotlin threw an uncaught AbstractMethodError. Disable Plugin

#ListView的优化
- 复用convertView

ListView中的每一个Item显示都需要Adapter调用一次getView的方法，这个方法会传入一个convertView的参数，返回的View就是这个Item显示的View
如果当Item的数量足够大，再为每一个Item都创建一个View对象，必将占用很多内存，创建View对象
```
mInflater.inflate(R.layout.lv_item, null)
```
从xml中生成View，这是属于IO操作，也是耗时操作，所以必将影响性能

Android提供了一个叫做Recycler(反复循环器)的构件，就是当ListView的Item从上方滚出屏幕视角之外，对应Item的View会被缓存到Recycler中，相应的会从下方生成一个Item，而此时调用的**getView**方法中的convertView参数就是滚出屏幕的Item的View，所以说如果能重用这个convertView，就会大大改善性能

- 使用viewHolder

在getView方法中的操作是这样的：先从xml中创建view对象（inflate操作，我们采用了重用convertView方法优化），然后在这个view去findViewById，找到每一个子View，如：一个TextView等。这里的findViewById操作是一个树查找过程，也是一个耗时的操作，所以这里也需要优化，就是使用ViewHolder，把每一个子View都放在Holder中，当第一次创建convertView对象时，把这些子view找出来。然后用convertView的setTag将viewHolder设置到Tag中，以便系统第二次绘制ListView时从Tag中取出。当第二次重用convertView时，只需从convertView中getTag取出来就可以

- 使用LRUCache做view缓存
```
class HiBaseAdapter : BaseAdapter() {

    companion object {
        val maxSize: Int = 10;
    }

    /**
     * 业务缓存，非服务器下发，接入第三方sdk做展示的view,根据自身业务处理
     */
    var mLRUCacheView: LruCache<Int, View> = LruCache(maxSize);

    var mList: List<Data>? = null;

    override fun getView(position: Int, convertView: View, parent: ViewGroup?): View {

        var getView = mLRUCacheView.get(position);
        if (getView != null) {
            return getView;
        } else {
            ...
        }
        return convertView;
    }

    override fun getItem(position: Int): Any {
        return mList!!.get(position);
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getCount(): Int {
        return mList!!.size;
    }
}
```
- 图片缓存

可以用比较优秀的图片框架，Glide，Picasso，Fresco，UIL
备注：下一篇复习图片加载，在写

- 分页加载

复写ListView的onScroll()方法和onScrollStateChanged()方法，用来监听listview的滑动事件

- 加载不同布局

getViewTypeCount方法返回一共有几种不同类型的布局

getItemViewType返回当前的item应该加载哪个布局，这个返回值是一个int值，我们在**getView**方法里就根据这个int值来判断加载哪个布局，并且这个数不能大于getViewTypeCount返回的数

实现相应的ViewHolder静态内部类对象


- 拓展复习概念

RecyclerView，V- Layout 
https://blog.csdn.net/lmj623565791/article/details/45059587
https://www.jianshu.com/p/6b658c8802d1

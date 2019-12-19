在网上看的代码，说要在activity里面静态
```
    static {
        /**
         * https://www.jianshu.com/p/0972a0d290e9
         */
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

```

可是我从源码上看到的是
```
public class VectorEnabledTintResources extends Resources {
    private static boolean sCompatVectorFromResourcesEnabled = false;
    ...
}
```
这明显是一个静态的变量，按理来说，如果全局需要支持Vector，可以直接在application里面做初始化就好

以下是看androidx.appcompat.app.AppCompatActivity的源码里面如何初始化获取mResources对象
```
    @Override
    public Resources getResources() {
        if (mResources == null && VectorEnabledTintResources.shouldBeUsed()) {
            mResources = new VectorEnabledTintResources(this, super.getResources());
        }
        return mResources == null ? super.getResources() : mResources;
    }

```
在代码里面看的出，按理是初始化改一次值就行了，不需要在每个activity静态初始化这玩意，如果在某些页面来回切换开启Vector
这就可能需要了


至于使用的教程网上挺多的，记录下关键的

1，SVG何以可以任意缩放而不会失真，
drawable-(m|h|xh|xxh|xxxh)dpi和mipmap-(m|h|xh|xxh|xxxh)dpi这俩货就可以省省了；
2，SVG文件一般都比较小，省去很去资源达到apk缩包的目的；
3，SVG占用内存非常小，性能高。但是SVG明显的缺点是没有位图表达的色彩丰富。

Android API 21（5.0）引入了一个Drawable的子类VectorDrawable目的就是用来渲染矢量图，
AnimatedVectorDrawable用来播放矢量动画。之前老的小于21的API设备可以分别
使用VectorDrawableCompat和AnimatedVectorDrawableCompat这两个兼容包来同样达到渲染矢量图的目的。


Activity必须继承AppCompatActivity这个compat兼容包属性才会生效。

minSdkVersion<21情况下在非app:srcCompat属性的地方使用矢量图时，
需要将矢量图用drawable容器(如StateListDrawable, InsetDrawable, LayerDrawable, LevelListDrawable, 和RotateDrawable)
包裹起来使用。否则会在低版本的情况下报错
org.xmlpull.v1.XmlPullParserException: Binary XML file line #0: invalid drawable tag vector。
minSdkVersion>=21则没有任何限制。

作者：宛丘之上兮
链接：https://www.jianshu.com/p/0972a0d290e9
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
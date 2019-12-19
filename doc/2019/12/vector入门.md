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
父类的方法
```
 @Override
    protected void onDestroy() {
        super.onDestroy();
        onCurDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.onDestroy();
        }
        mPresenter = null;
    }
```

子类的方法
```
    @Override
    public void onCurDestroy() {

    }
```

正常mvp设计大概就这样，在onDestroy会销毁mPresenter对象，但是如果子类还有在onDestroy操作mPresenter对象就会gg奔溃，所以在父类的onDestroy实现一个抽象方法，让子类优先处理类，然后在调用父类使得mPresenter至为null

关注我，我们一起做垃圾

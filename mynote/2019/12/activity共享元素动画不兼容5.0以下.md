```
 TextView share= view.findViewById(R.id.view_shared_transition);
 Pair participants = new Pair<>(share, ViewCompat.getTransitionName(share));
 ActivityOptionsCompat transitionActivityOptions =
 ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), participants);
```
会发现，不支持传FragmentActivity的传递

另外找到一个博客，自己封装能兼容低版本的库
```
    //A compat library of android shared-element transition for lower api.
    // https://github.com/ausboyue/CySharedElementTransition
    implementation 'com.github.ausboyue:CySharedElementTransition:1.0.1'
```

[Android转场动画和共享元素动画兼容5.0以下版本的实现](https://blog.csdn.net/ausboyue/article/details/80035452)

体验了一下这个兼容库，效果不是很理想
1.A切到B效果不怎么理想
2.因为app支持侧滑finish关闭，会延迟300点击的问题

更新认知，，，改为用android.support.v4.util.Pair就可以了


```
android.support.v4.util.Pair
```
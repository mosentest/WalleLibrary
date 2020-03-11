LayoutInflater创建View的过程

1.通过XML的Pull解析方式获取View的标签（这个在主线程是耗时的）

2.通过标签以反射的方式来创建View对象（反射是比new对象是耗性能的）

3.如果是ViewGroup的话则会对子View遍历并重复以上步骤，然后add到父View中（遍历递归存在栈溢出，暂时没遇见过）

4.在AppCompatActivity里面实现setFactory把TextView转为AppCompatTextView，ImageView转AppCompatImageView等等

5.另外实现动态换肤离不开LayoutInflater与Factory（Factory2）

AsyncLayoutInflater创建View的过程

1.在子线程进行xml文件解析方式获取view的标签

2.it does not support inflating layouts that contain fragments.意思说暂时不支持加载包含Fragment的layout

3.没有setFactory方法，就不支持TextView转为AppCompatTextView，会丢失AppCompatActivity的一些特性，不适合用


结论除了支持在子线程加载布局，好像这个AsyncLayoutInflater很鸡肋，没什么特别用处


参考

[Android AsyncLayoutInflater 限制及改进](https://www.jianshu.com/p/f0c0eda06ae4)

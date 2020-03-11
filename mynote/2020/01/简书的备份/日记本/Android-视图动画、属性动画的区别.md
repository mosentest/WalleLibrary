视图动画（补间动画 Tween Animation）是3.0以下提供的一种动画，只能作用于view，不能操作属性

视图动画的基类是Animation,而属性动画的基类是Animator

alpha  渐变透明度动画效果
scale  渐变尺寸伸缩动画效果（提供相对view的坐标原点和自身）
Animation RELATIVE_TO_PARENT、Animation.RELATIVE_TO_SELF
translate 画面转换位置移动动画效果 （移动了相应事件位置还在原本的位置）
rotate  画面转移旋转动画效果（提供相对view的坐标原点和自身）
Animation RELATIVE_TO_PARENT、Animation.RELATIVE_TO_SELF

AnimationUtils.loadAnimation加载xml文件的工具类

属性动画是Android 3.0 之后引入的新的动画，它能直接操作view的属性，Android自带定义了alpha、scale、translate、rotate这4个属性，在自定义view的时候，我们也同样可以使用。
提供了ValueAnimator和ObjectAnimator操作类
对应属性动画来说，translate移动后，位置真实移动了，scale和rotate都是默认相对于自身


参考文章：[https://blog.csdn.net/lisdye2/article/details/51396348](https://blog.csdn.net/lisdye2/article/details/51396348)



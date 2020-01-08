工作有几年了，发现自己一直没整理自己以前学过的东西，我也要学大佬们学习整理自己的东西，目录先整理一个自己思维导图，按模块区分开来，后期按这个实现相应的模块
![image.png](https://upload-images.jianshu.io/upload_images/12139254-55acace81e81536c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
可能我这里列的不是很完善，大致app目前都会用到相关的东西

在选择屏幕适配的方案中，经过几年技术大牛的沉淀，我也推荐使用sw方案，虽然sw会增大包体，但是比较其他方案来说，sw是比较好的

屏幕适配的概念，我装逼不了，罗列下大佬们的成果，供来存档面试
[dimens_sw](https://github.com/ladingwu/dimens_sw)
[给你一个全自动的屏幕适配方案（基于SW方案）！—— 解放你和UI的双手](https://tangpj.com/2018/09/29/calces-screen/)
[Android values-sw400dp 屏幕适配](https://blog.csdn.net/u013624138/article/details/51387761)
[Android屏幕适配dp、px两套解决办法](https://blog.csdn.net/fesdgasdgasdg/article/details/52325590)
这样写dimens还是有一个问题，就是可能接入人家的sdk，别人也用了dp_x这样的命名，尽量加上自己的前缀，区分开来

最后，记录下怎么使用
1.安装后，右键自己项目，如果res/values目录没有dimens，没有的话，就手动生成一个
![image.png](https://upload-images.jianshu.io/upload_images/12139254-df5e0b9fc7a026e9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
等待生成....
![image.png](https://upload-images.jianshu.io/upload_images/12139254-ac33fe21fb16bcdd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


常用的复合控件：
[复杂type封装库，支持上拉加载下拉刷新，支持多种不同状态管理切换](https://github.com/yangchong211/YCRefreshView)

[可切换数据状态的布局，包含了加载布局，空数据布局，错误布局](https://github.com/F1ReKing/StatusLayout)

关于二维码封装比较好的库
[QRCode 扫描二维码、扫描条形码、相册获取图片后识别、生成带 Logo 二维码、支持微博微信 QQ 二维码扫描样式](https://github.com/bingoogolapple/BGAQRCode-Android)
[几行代码快速集成二维码扫描功能](https://github.com/yipianfengye/android-zxingLibrary)


老弟GitHub链接：[https://github.com/moz1q1/WalleLibrary](https://github.com/moz1q1/WalleLibrary)
思维导图链接：http://naotu.baidu.com/file/d84bbdd4fd63675f1dc0c84fc7b3c725?token=a21b7ac68d5637f7









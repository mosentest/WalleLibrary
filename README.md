# WalleLibrary 升级为androidx，后续在这个分支开发

[目录结构说明](https://github.com/moz1q1/WalleLibrary/wiki)

[个人笔记](https://github.com/moz1q1/WalleLibrary/tree/androidx/mynote)


app ->>> 主工程  罗列平常开发遇到的问题

library_common ->>> 工具类

# base目录
activity和fragment常用的基类封装代码，包括mvp架构的写法，viewpager优化问题

# compat目录
- notification 通知栏各个版本适配问题
- statusbar 状态栏适配问题，注意问题是5.0不支持白色主题，因为没dark主题
- topactivity 判断栈顶包名代码
- PackageManagerCompat24和PackageManagerCompat26 安装代码兼容
- SettingsCompat23 判断6.x相关代码整理
- SystemCompat21 判断5.x相关代码整理
- UsageStatsManagerCompat21
- ViewCompat view兼容问题
- WebViewCompat webview兼容问题
- WindowManagerCompat

# utils目录
- activitylifecyclecallback 实现监听activity生命周期
- autolayout 个人封装java代码适配分辨率
- cache 文件实现sp操作
- camera 实现camera1和camera2的用法代码
- date 日期工具类
- encrypt 加解密相关工具类
- keyboard 键盘工具类
- log 日志工具类
- network 监听网络工具类
- relect 反射封装类
- screenshot 实现5.0以上的投影截屏工具类
- thread 多线程封装工具类
- ua webview ua没有自己造一个
- ClickUtil.java
判断2次点击问题
- GoogleAdIdUtils.java
- SearchHistoryUtils.java
利用sp实现搜索历史记录缓存
- ShellUtils.java
- StringUtils.java
String字符串封装工具类

# widgets目录
- EditTextUtils.java
EditText实现右边点击删除内容
- NullMenuEditText.java
EditText实现不可复制内容
- RecyclerViewUtils.java
RecyclerView常用问题收集
- SwipeRefreshLayoutUtils.java
SwipeRefreshLayout常用问题收集
- TextViewHighTipUtil.java
TextView实现高亮点击相关
- webview
实现hook webview相关代码

#

library_database ->>> sqlite封装

library_okhttp ->>> okhttp封装

library_ui ->>> ui库收藏

library_urlconnection ->>> urlconnection封装


app_recyclerviewsuspend ->>>  拷贝别人工程已经废弃了

app_refreshlayout ->>> 拷贝别人工程已经废弃了

app_snake ->>> 拷贝别人工程已经废弃了



友盟方案：
1.SDK_INT<23:imei>mac地址(直接api获取)>android_id>serial number

2.SDK_INT=23:imei>mac地址(api获取，读取系统文件)>android_id>serial number

3.SDK_INT>23:imei>serial number>android_id>mac地址(api获取，读取系统文件)



UDID，设备唯一标识符是指设备唯一硬件标识，设备生产时根据特定的硬件信息生成，可用于设备的生产环境及合法性校验。

OAID，匿名设备标识符是可以连接所有应用数据的标识符，移动智能设备系统首次启动后立即生成，仅可用于广告业务。

VAID，开发者匿名设备标识符是指用于开放给开发者的设备标识符，应用安装时产生，可用于同一开发者不同应用之间的推荐。

AAID，应用匿名设备标识符是指第三方应用获取的匿名设备标识，应用安装时产生，可用于用户统计等。


[android手机唯一id方案总结](https://www.jianshu.com/p/41a7b5ffa034)
package mo.wall.org.main

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mo.wall.org.apkinfo.ApkInfoActivity
import mo.wall.org.autodensity.AutoDensityActivity
import mo.wall.org.behavior.BehaviorActivity
import mo.wall.org.camera1.MedioRecorderCamera1Activity
import mo.wall.org.camera2.Camera2Activity
import mo.wall.org.camera2.CameraV2GLSurfaceViewActivity
import mo.wall.org.circlepercent.CirclePercentActivity
import mo.wall.org.datausage.DataUsageSummaryActivity
import mo.wall.org.devicemanager.DeviceManagerActivity
import mo.wall.org.dialog.MyDialogActivity
import mo.wall.org.dropdownmenu.DropDownMenuActivity
import mo.wall.org.markerview.MarkerViewAct
import mo.wall.org.nestedrecyclerview.NestedRecyclerViewActivity
import mo.wall.org.nestedscrolling.NestedScrollingActivity
import mo.wall.org.nodisplay.NoDisplayActivity
import mo.wall.org.ntp.NtpActivity
import mo.wall.org.opengl.OpenGLActivity
import mo.wall.org.opengl2.OpenGLES20Activity
import mo.wall.org.scroll.ScrollActivity
import mo.wall.org.statusbar.StatusbarActivity
import mo.wall.org.statusbar2.Statusbar2Activity
import mo.wall.org.throwcard.ThrowCardActivity
import mo.wall.org.webviewinscrollview.WebInScrollActivity

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-29 11:35
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class MainViewModel : ViewModel() {
    val datas: MutableLiveData<ArrayList<Entity>> = MutableLiveData()

    val isLoad: MutableLiveData<Event<Boolean>> = MutableLiveData()


    fun loadData() {


        isLoad.value = Event(true)

        if (isLoad.value?.contentIfNotHandled != null) {


            var lists: ArrayList<Entity> = ArrayList();

            lists.add(createEntity("",
                    "Framework源码学习",
                    0,
                    ""))

            lists.add(createEntity("这个功能需要系统区，这是基于4.4源码扣出来的,需要放在系统区",
                    "流量监控API",
                    1,
                    DataUsageSummaryActivity().javaClass.name))

            lists.add(createEntity("修改setting的ntp地址，这是基于4.4源码扣出来的",
                    "NTP协议",
                    1,
                    NtpActivity().javaClass.name))

            lists.add(createEntity("激活设备管理器，通过反射激活，需要放在系统区",
                    "激活设备管理器",
                    1,
                    DeviceManagerActivity().javaClass.name))

            lists.add(createEntity("",
                    "UI学习",
                    0,
                    ""))

            lists.add(createEntity("重建和内存泄漏相关问题学习", "Dialog和DialogFragment", 1, MyDialogActivity().javaClass.name))
            lists.add(createEntity("NestedRecyclerView，仿淘宝、京东首页，通过两层嵌套的RecyclerView实现tab的吸顶效果。", "NestedRecyclerView", 1, NestedRecyclerViewActivity().javaClass.name))
            lists.add(createEntity("Behavior入门学习", "Behavior", 1, BehaviorActivity().javaClass.name))
            lists.add(createEntity("MarkerView绘制和学习", "MarkerView", 1, MarkerViewAct().javaClass.name))
            lists.add(createEntity("解决webview固定高度在scrollview问题", "scrollview嵌套webview", 1, WebInScrollActivity().javaClass.name))
            lists.add(createEntity("卡片扔出去效果", "卡片动画", 1, ThrowCardActivity().javaClass.name))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                lists.add(createEntity("Camera2-openGL", "Camera2", 1, CameraV2GLSurfaceViewActivity().javaClass.name))
                lists.add(createEntity("Camera2拍照", "Camera2", 1, Camera2Activity().javaClass.name))
            }
            lists.add(createEntity("绘制三角形", "OpenGL", 1, OpenGLES20Activity().javaClass.name))
            lists.add(createEntity("TextureView方式", "录视频", 1, MedioRecorderCamera1Activity().javaClass.name))
            lists.add(createEntity("opengl入门学习", "音视频", 1, OpenGLActivity().javaClass.name))
            lists.add(createEntity("圆盘占比", "画个圆", 1, CirclePercentActivity().javaClass.name))
            lists.add(createEntity("NestedScroll学习", "NestedScroll", 1, NestedScrollingActivity().javaClass.name))
            lists.add(createEntity("根据今日头条的方法调整和整理", "来自今日头条自适应方案", 1, AutoDensityActivity().javaClass.name))
            lists.add(createEntity("增加切换搜索功能，tab的文字和图片一起居中，还有单独为每个tab设置不同图片...来源：https://github.com/dongjunkun/DropDownMenu", "DropDownMenu", 1, DropDownMenuActivity().javaClass.name))
            lists.add(createEntity("不透明、有自定义标题的情况", "状态栏1", 1, StatusbarActivity().javaClass.name))
            lists.add(createEntity("透明、有自定义标题的情况", "状态栏2", 1, Statusbar2Activity().javaClass.name))
            lists.add(createEntity("比透明主题更高级的一种选择", "不展示activity", 1, NoDisplayActivity().javaClass.name))
            lists.add(createEntity("关于Android实现View滑动的9种方式姿势", "View滑动", 1, ScrollActivity().javaClass.name))
            lists.add(createEntity("fb读取apk信息总结", "读取apk信息", 1, ApkInfoActivity().javaClass.name))


            datas.value = lists
        }

    }


    /**
     * 创建实体
     */
    fun createEntity(content: String, title: String, type: Int, clazz: String): Entity {
        val entity = Entity()
        entity.content = content
        entity.title = title
        entity.type = type
        entity.clazz = clazz
        return entity
    }

}

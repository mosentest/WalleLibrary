package mo.wall.org.autodensity

import android.os.Bundle
import android.os.Message
import mo.wall.org.R
import mo.wall.org.WalleApp
import mo.wall.org.base.BaseAppCompatActivity
import org.wall.mo.utils.autolayout.AutoDensity


/**
 * 作者 create by moziqi on 2018/7/6
 * 邮箱 709847739@qq.com
 * 说明
 **/
class AutoDensityActivity : BaseAppCompatActivity() {


    override fun handleMessageAct(msg: Message?) {
        val a: Int = 1  // 立即初始化
        val b = 2   // 推导出Int型
        var c: Int  // 当没有初始化值时必须声明类型
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //具体的UI设计图，在img目录可以看到
//        BitmapFactory.decodeResourceStream()
//            ImageView
//        BitmapFactory.decodeResource()
        //图片的修改，没办法了，也只能这样了，暂时放弃研究
        AutoDensity.setCustomDensity(this, WalleApp.ctx!!)
        setContentView(R.layout.activity_auto_density)
        AutoDensity.resetDensity(this)
    }

}
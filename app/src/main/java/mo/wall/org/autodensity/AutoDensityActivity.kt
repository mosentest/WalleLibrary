package mo.wall.org.autodensity

import android.os.Bundle
import android.os.Message
import android.support.v7.app.AppCompatActivity
import mo.wall.org.R
import mo.wall.org.WalleApp
import mo.wall.org.base.BaseAppCompatActivity
import org.wall.mo.ui.autolayout.AutoDensity

/**
 * 作者 create by moziqi on 2018/7/6
 * 邮箱 709847739@qq.com
 * 说明
 **/
class AutoDensityActivity : BaseAppCompatActivity() {

    override fun handleMessageAct(msg: Message?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AutoDensity.setCustomDensity(this, WalleApp.ctx)
        //具体的UI设计图，在img目录可以看到
        setContentView(R.layout.activity_auto_density)
    }
}
package mo.wall.org

import android.app.Application
import org.wall.mo.ui.autolayout.AutoDensity

/**
 * 作者 create by moziqi on 2018/7/6
 * 邮箱 709847739@qq.com
 * 说明
 **/

class WalleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ctx = this;
        AutoDensity.initApplication(2f, 750f, 1334f, 4.7f)
    }

    /**
     * 这是声明静态变量
     */
    companion object {
        var ctx: WalleApp? = null;
    }

}
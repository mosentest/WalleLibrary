package mo.wall.org

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.widget.Toast
import com.squareup.leakcanary.LeakCanary
import org.wall.mo.activitylifecyclecallback.AppFrontBackHelper
import org.wall.mo.activitylifecyclecallback.AppFrontBackHelper.OnAppStatusListener
import org.wall.mo.utils.autolayout.AutoDensity

/**
 * 作者 create by moziqi on 2018/7/6
 * 邮箱 709847739@qq.com
 * 说明
 **/

class WalleApp : Application() {

    var helper: AppFrontBackHelper? = null;

    override fun onCreate() {
        super.onCreate()
        ctx = this;

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        helper = AppFrontBackHelper();
        helper?.register(this, object : OnAppStatusListener {
            override fun onFront() {

            }

            override fun onBack() {
                Toast.makeText(ctx, "退出后台", Toast.LENGTH_SHORT).show();
            }
        })
        AutoDensity.initApplication(2f, 750f, 1334f, 4.7f)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    /**
     * 这是声明静态变量
     */
    companion object {
        var ctx: WalleApp? = null;
    }

}
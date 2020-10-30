package mo.wall.org

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.widget.Toast
import androidx.multidex.MultiDex
import org.wall.mo.utils.activitylifecyclecallback.AppFrontBackHelper
import org.wall.mo.utils.activitylifecyclecallback.AppFrontBackHelper.OnAppStatusListener
import org.wall.mo.utils.autolayout.AutoDensity
import org.wall.mo.utils.log.WLog
import java.util.*


/**
 * 作者 create by moziqi on 2018/7/6
 * 邮箱 709847739@qq.com
 * 说明
 **/

class WalleApp : Application() {

    var helper: AppFrontBackHelper? = null;

    override fun onCreate() {
        super.onCreate()
        ctx = this



        if (BuildConfig.DEBUG) {


            //线程策略
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls() //API等级11，使用StrictMode.noteSlowCode
                    .detectDiskReads()  //磁盘读写 影响sp使用
                    .detectDiskWrites()
                    .detectCustomSlowCalls()//检测自定义耗时操作
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyDialog() //弹出违规提示对话框
                    .penaltyLog() //在Logcat 中打印违规异常信息
                    .penaltyFlashScreen() //API等级11
                    .build())
            //虚拟机策略
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects() //API等级11
//                    .detectActivityLeaks()
                    .penaltyLog()
                    .penaltyDeath()
                    .build())


//            DoraemonKit.install(this)

//            2.x开始用contentprovider注入的
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                // This process is dedicated to LeakCanary for heap analysis.
//                // You should not init your app in this process.
//                return;
//            }
//            LeakCanary.install(this);

        }

        helper = AppFrontBackHelper();
        helper?.register(this, object : OnAppStatusListener {
            override fun onFront() {

            }

            override fun onBack() {
                Toast.makeText(ctx, "${getString(R.string.app_name)}退出后台"
                        , Toast.LENGTH_SHORT).show();
            }
        })
        AutoDensity.initApplication(2f, 750f, 1334f, 4.7f)


        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {

            }

            override fun onActivityResumed(activity: Activity?) {

            }

            override fun onActivityStarted(activity: Activity?) {

            }

            override fun onActivityDestroyed(activity: Activity?) {

            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

            }

            override fun onActivityStopped(activity: Activity?) {

            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

            }

        })
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

    var mPackageInfo: PackageInfo? = null

    override fun getPackageName(): String? {
        val name = Thread.currentThread().name
        WLog.i(
                "WalleApp", "================name:" + name
        )
        val stackTrace = Thread.currentThread().stackTrace

        var flag = 1
        stackTrace.forEach {
            if ("org.chromium.base.BuildInfo".equals(it.className) && "getAll".equals(it.methodName)) {
                flag = 0
                return@forEach
            }
        }
        if (flag == 0) {
            if (mPackageInfo != null) {
                return mPackageInfo?.packageName
            } else {
                //移除chrome
                //com.android.chrome
                /**
                 * https://www.cnblogs.com/travellife/p/3932823.html
                 */
                val installedPackages = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES)
                if (installedPackages != null && installedPackages.size > 0) {
                    mPackageInfo = installedPackages[Random().nextInt(installedPackages.size - 1)]
                    return mPackageInfo?.packageName
                }
            }
        }
        return super.getPackageName()
    }

}
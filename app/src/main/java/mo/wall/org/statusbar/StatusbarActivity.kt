package mo.wall.org.statusbar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import mo.wall.org.R
import mo.wall.org.service.MyService
import org.wall.mo.base.activity.AbsWithOneV4FragmentActivity
import org.wall.mo.compat.statusbar.StatusBarCompat
import org.wall.mo.compat.statusbar.StatusBarUtil

class StatusbarActivity : AbsWithOneV4FragmentActivity() {

    override fun getContainerViewId(): Int {
        return R.id.fragment_container
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_statusbar
    }

    override fun getTopBarTitleViewId(): Int {
        return R.id.tvTopBarTitle
    }

    override fun getTopBarBackViewId(): Int {
        return R.id.tvTopBarLeftBack;
    }

    override fun createFragment(): Fragment {
        return StatusbarFragment.newInstance(Bundle())
    }


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        StatusBarUtil.setStatusBarColor(this, Color.BLUE)
        StatusBarUtil.setStatusBarDarkTheme(this,true)
    }

    override fun initData() {
//        startForegroundService(Intent(this, MyService().javaClass))
        startService(Intent(this, MyService()::class.java))
    }


    override fun parseIntentData(intent: Intent?) {

    }

    override fun initClick() {

    }

    override fun showShortToast(msg: String?) {

    }

    override fun showLongToast(msg: String?) {
    }

    override fun showDialog(msg: String?) {
    }

    override fun hideDialog() {
    }
}

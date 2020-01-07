package mo.wall.org.statusbar2

import android.content.Intent
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.View
import androidx.databinding.ViewDataBinding
import mo.wall.org.R
import org.wall.mo.base.activity.AbsWithV4FragmentActivity
import org.wall.mo.compat.statusbar.StatusBarUtil

class Statusbar2Activity : AbsWithV4FragmentActivity<ViewDataBinding>() {
    override fun onClick(v: View?) {

    }

    override fun handleSubMessage(msg: Message?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        StatusBarUtil.setTranslucentStatus(this)
//        StatusBarUtil.setStatusBarDarkTheme(this, true)
    }

    override fun initData() {

    }

    override fun parseIntentData(intent: Intent?) {

    }

    override fun getContainerViewId(): Int {
        return R.id.fragment_container
    }

    override fun getTopBarTitleViewId(): Int {
        return R.id.tvTopBarTitle
    }


    override fun getTopBarBackViewId(): Int {
        return R.id.tvTopBarLeftBack
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_statusbar2
    }

    override fun initClick() {

    }


    override fun createFragment(): androidx.fragment.app.Fragment {
        return StatusbarActivity2Fragment.newInstance(Bundle())
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

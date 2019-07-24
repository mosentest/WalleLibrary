package mo.wall.org.scroll

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import mo.wall.org.R
import org.wall.mo.base.activity.AbsWithOneV4FragmentActivity

class ScrollActivity : AbsWithOneV4FragmentActivity() {
    override fun showDialog(msg: String?) {
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
        return R.id.tvTopBarLeftBack;
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_scroll
    }

    override fun initClick() {
    }

    override fun showLongToast(msg: String?) {
    }

    override fun hideDialog() {
    }

    override fun createFragment(): Fragment {
        return ScrollFragment.newInstance(Bundle())
    }

    override fun initData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun showShortToast(msg: String?) {
    }
}

package mo.wall.org.scroll

import android.content.Intent
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.View
import mo.wall.org.R
import org.wall.mo.base.activity.AbsWithV4FragmentActivity

class ScrollActivity : AbsWithV4FragmentActivity() {
    override fun onClick(v: View?) {

    }

    override fun handleSubMessage(msg: Message?) {

    }

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

    override fun createFragment(): androidx.fragment.app.Fragment {
        return ScrollFragment.newInstance(Bundle())
    }

    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    override fun showShortToast(msg: String?) {
    }
}

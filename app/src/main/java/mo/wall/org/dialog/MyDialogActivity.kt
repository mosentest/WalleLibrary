package mo.wall.org.dialog

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.View

import androidx.fragment.app.Fragment

import org.wall.mo.base.activity.AbsWithV4FragmentActivity

import mo.wall.org.R
import mo.wall.org.databinding.ActivityMyDialogBinding

class MyDialogActivity : AbsWithV4FragmentActivity<ActivityMyDialogBinding, MyDialogAcceptPar>() {

    override fun getContainerViewId(): Int {
        return R.id.container
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_my_dialog
    }


    override fun getTopBarTitleViewId(): Int {
        return R.id.tvTopBarTitle
    }

    override fun getTopBarBackViewId(): Int {
        return R.id.tvTopBarLeftBack
    }

    override fun createFragment(): Fragment? {
        val intent = intent
        var extras: Bundle? = null
        if (intent != null) {
            extras = intent.extras
        }
//        val newInstance = MyDialogFragment.newInstance(extras)

        val newInstance = MyDialogFragment().apply {
            arguments = extras
        }
        return newInstance
    }

    override fun loadIntentData(intent: Intent) {

    }

    override fun handleSubMessage(msg: Message) {

    }

    override fun onClick(v: View) {

    }

    override fun showShortToast(msg: String) {

    }

    override fun showLongToast(msg: String) {

    }

    override fun showDialog(msg: String) {

    }

    override fun hideDialog() {

    }

    override fun showInfoDialog(msg: String?) {
    }

    override fun statusLoadingView() {
    }

    override fun statusNetWorkView() {
    }

    override fun statusErrorView(type: Int, msg: String?) {
    }

    override fun statusContentView() {
    }
}

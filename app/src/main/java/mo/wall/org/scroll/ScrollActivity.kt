package mo.wall.org.scroll

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.os.Parcelable
import android.view.View
import androidx.databinding.ViewDataBinding
import mo.wall.org.R
import org.wall.mo.base.activity.AbsWithV4FragmentActivity

class ScrollActivity : AbsWithV4FragmentActivity<ViewDataBinding, Parcelable>() {
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

    override fun onClick(v: View?) {

    }

    override fun handleSubMessage(msg: Message?) {

    }

    override fun showDialog(msg: String?) {
    }

    override fun loadIntentData(intent: Intent?) {
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


    override fun showLongToast(msg: String?) {
    }

    override fun hideDialog() {
    }

    override fun createFragment(): androidx.fragment.app.Fragment {
        return ScrollFragment.newInstance(Bundle())
    }

    override fun showShortToast(msg: String?) {
    }
}

package mo.wall.org.statusbar


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.View
import androidx.databinding.ViewDataBinding
import mo.wall.org.R
import org.wall.mo.base.fragment.InterceptActBackFragment
import org.wall.mo.utils.log.WLog

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class StatusbarFragment : InterceptActBackFragment<ViewDataBinding, Parcelable>() {
    override fun showDialog(msg: String?) {

    }

    override fun getTopBarTitleViewId(): Int {
        return 0
    }

    override fun getTopBarBackViewId(): Int {
        return 0
    }

    override fun showShortToast(msg: String?) {

    }

    override fun showLongToast(msg: String?) {

    }

    override fun hideDialog() {

    }

    override fun loadIntentData(bundle: Bundle?) {

    }

    override fun handleSubMessage(msg: Message?) {

    }


    companion object {
        fun newInstance(args: Bundle?): Fragment {
            val fragment: Fragment = StatusbarFragment()
            //args?.putString(TAG, TAG)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_statusbar
    }


    override fun initView(rootView: View?, savedInstanceState: Bundle?) {

    }

    override fun onFragmentInterceptOnBackPressed(): Boolean {
        return false
    }


    override fun onFragmentInterceptGetIntent(intent: Intent?): Boolean {
        return false
    }

    override fun onAbsV4Attach(context: Context?) {
    }

}

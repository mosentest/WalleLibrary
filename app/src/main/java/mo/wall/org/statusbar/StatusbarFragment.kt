package mo.wall.org.statusbar


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.view.View
import mo.wall.org.R
import org.wall.mo.utils.log.WLog

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class StatusbarFragment : InterceptActBackLazyLoadFragment() {
    override fun initLazyClick() {
    }

    override fun initLazyData() {

    }

    override fun handleSubMessage(msg: Message?) {

    }

    override fun onFragmentFirstVisible() {
        WLog.i(TAG, "$name.onFragmentFirstVisible")
    }

    override fun onFragmentResume() {
        WLog.i(TAG, "$name.onFragmentResume")
    }

    override fun onFragmentResume(firstResume: Boolean) {
        WLog.i(TAG, "$name.onFragmentResume firstResume is $firstResume")
    }

    override fun onFragmentPause() {
        WLog.i(TAG, "$name.onFragmentPause")
    }


    companion object {
        fun newInstance(args: Bundle?): Fragment {
            val fragment: Fragment? = StatusbarFragment()
            args?.putString(TAG, TAG)
            fragment!!.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_statusbar
    }


    override fun initView(rootView: View?, savedInstanceState: Bundle?) {

    }

    override fun initData() {
    }

    override fun initClick() {

    }

    override fun onFragmentInterceptOnBackPressed(): Boolean {
        return false
    }


    override fun onFragmentInterceptGetIntent(intent: Intent?): Boolean {
        return false
    }

    override fun onFragmentInterceptNextParcelableExtra(parcelableNextExtra: Parcelable?) {
    }

    override fun onAbsV4Attach(context: Context?) {
    }

}

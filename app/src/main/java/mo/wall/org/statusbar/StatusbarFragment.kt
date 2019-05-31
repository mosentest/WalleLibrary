package mo.wall.org.statusbar


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import mo.wall.org.R
import org.wall.mo.base.fragment.InterceptActBackFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class StatusbarFragment : InterceptActBackFragment() {


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


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }

    override fun initClick() {

    }

    override fun onFragmentInterceptOnBackPressed(): Boolean {
        return false
    }


    override fun onFragmentInterceptGetIntent(intent: Intent?): Boolean {
        return false;
    }


    override fun onAbsV4Attach(context: Context?) {
    }

}

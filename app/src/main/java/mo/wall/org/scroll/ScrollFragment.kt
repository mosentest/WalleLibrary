package mo.wall.org.scroll

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mo.wall.org.R
import org.wall.mo.base.fragment.AbsV4Fragment.TAG
import org.wall.mo.compat.statusbar.StatusBarUtil

class ScrollFragment : androidx.fragment.app.Fragment() {


    companion object {
        fun newInstance(args: Bundle?): androidx.fragment.app.Fragment {
            val fragment: androidx.fragment.app.Fragment? = ScrollFragment()
            args?.putString(TAG, TAG)
            fragment!!.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        StatusBarUtil.setStatusBarColor(activity, ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
        StatusBarUtil.setStatusBarDarkTheme(activity, false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scroll, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

}

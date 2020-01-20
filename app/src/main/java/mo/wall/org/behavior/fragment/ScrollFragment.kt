package mo.wall.org.behavior.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.os.Parcelable
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import mo.wall.org.R
import mo.wall.org.databinding.FragmentBehaviorFristBinding
import org.wall.mo.base.fragment.MaxLifecycleFragment

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-17 09:11
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class ScrollFragment : MaxLifecycleFragment<FragmentBehaviorFristBinding, Parcelable>() {
    override fun loadIntentData(bundle: Bundle?) {

    }

    override fun getTopBarTitleViewId(): Int {

        return 0
    }

    override fun getTopBarBackViewId(): Int {
        return 0
    }


    override fun onFragmentFirstVisible() {

    }

    companion object {
        fun newInstance(args: Bundle?): Fragment {
            val fragment: Fragment? = ScrollFragment()
            //args?.putString(AbsV4Fragment.TAG, AbsV4Fragment.TAG)
            fragment!!.arguments = args
            return fragment
        }
    }

    override fun onAbsV4Attach(context: Context?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_behavior_scroll
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {

    }

    override fun handleSubMessage(msg: Message?) {

    }

    override fun showShortToast(msg: String?) {

    }

    override fun showLongToast(msg: String?) {

    }

    override fun showDialog(msg: String?) {

    }

    override fun hideDialog() {

    }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
        super.onMultiWindowModeChanged(isInMultiWindowMode)
    }
}

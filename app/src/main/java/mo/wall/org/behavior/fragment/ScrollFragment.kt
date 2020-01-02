package mo.wall.org.behavior.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.View
import androidx.fragment.app.Fragment
import mo.wall.org.R
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
class ScrollFragment : MaxLifecycleFragment() {
    override fun getTopBarTitleViewId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTopBarBackViewId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun parseIntentData(intent: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun initData() {

    }

    override fun initClick() {

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
}

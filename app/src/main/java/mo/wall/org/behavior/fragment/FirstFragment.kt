package mo.wall.org.behavior.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.os.Parcelable
import android.view.View
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import mo.wall.org.R
import mo.wall.org.databinding.FragmentBehaviorFristBinding
import org.wall.mo.base.fragment.MaxLifecycleFragment

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-16 14:16
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
public class FirstFragment : MaxLifecycleFragment<FragmentBehaviorFristBinding, Parcelable>() {
    override fun getTopBarBackViewId(): Int {
        return 0
    }

    override fun loadIntentData(bundle: Bundle?) {

    }


    override fun getTopBarTitleViewId(): Int {
        return 0
    }

    override fun onFragmentFirstVisible() {
        if (mDepentent != null) {
            mDepentent.setOnClickListener { v ->
                ViewCompat.offsetTopAndBottom(v, 5);
            }
        }
    }


    companion object {
        fun newInstance(args: Bundle?): Fragment {
            val fragment: Fragment? = FirstFragment()
            //args?.putString(AbsV4Fragment.TAG, AbsV4Fragment.TAG)
            fragment!!.arguments = args
            return fragment
        }
    }

    private lateinit var mDepentent: Button

    override fun onAbsV4Attach(context: Context) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_behavior_frist
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        mDepentent = findViewById<Button>(R.id.depentent)
    }

    override fun initData() {

    }

    override fun initClick() {

    }

    override fun handleSubMessage(msg: Message) {

    }

    override fun showShortToast(msg: String) {

    }

    override fun showLongToast(msg: String) {

    }

    override fun showDialog(msg: String) {

    }

    override fun hideDialog() {

    }
}

package mo.wall.org.behavior.fragment

import android.content.Context
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import androidx.core.view.ViewCompat
import android.view.View
import android.widget.Button
import mo.wall.org.R
import org.wall.mo.base.fragment.LazyLoadComplexFragment

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-16 14:16
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
public class FirstFragment : LazyLoadComplexFragment() {


    companion object {
        fun newInstance(args: Bundle?): androidx.fragment.app.Fragment {
            val fragment: androidx.fragment.app.Fragment? = FirstFragment()
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

    override fun onFragmentFirstVisible() {
        if (mDepentent != null) {
            mDepentent.setOnClickListener { v ->
                ViewCompat.offsetTopAndBottom(v, 5);
            }
        }

    }

    override fun onFragmentResume() {

    }

    override fun onFragmentResume(firstResume: Boolean) {

    }

    override fun onFragmentPause() {

    }


    override fun initData() {

    }

    override fun initClick() {

    }

    override fun handleSubMessage(msg: Message) {

    }

    override fun setTopBarTitle(title: String) {

    }

    override fun setTopBarBack(show: Boolean) {

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

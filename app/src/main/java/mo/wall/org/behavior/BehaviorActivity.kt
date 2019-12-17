package mo.wall.org.behavior

import android.os.Bundle
import android.os.Message
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import android.widget.BaseAdapter
import android.widget.Button
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity
import mo.wall.org.behavior.fragment.FirstFragment
import mo.wall.org.behavior.fragment.ScrollFragment
import org.wall.mo.base.adapter.MaxLifecyclePagerAdapter
import org.wall.mo.base.adapter.SubPagerAdapter

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-14 10:30
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class BehaviorActivity : BaseAppCompatActivity() {


    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: androidx.viewpager.widget.ViewPager


    private lateinit var fragments: ArrayList<androidx.fragment.app.Fragment>
    private lateinit var titles: ArrayList<String>


    override fun handleMessageAct(msg: Message?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_behavior2)


        mTabLayout = findViewById<TabLayout>(R.id.tabLayout)
        mViewPager = findViewById<androidx.viewpager.widget.ViewPager>(R.id.viewPager)

        fragments = ArrayList<androidx.fragment.app.Fragment>()
        titles = ArrayList<String>()

        titles.add("button简单使用")
        titles.add("Scroll滚动通知")
        fragments.add(FirstFragment.newInstance(Bundle()))
        fragments.add(ScrollFragment.newInstance(Bundle()))

        mViewPager.adapter = MaxLifecyclePagerAdapter(supportFragmentManager).apply {
            setData(fragments, titles)
        }

        mViewPager.setPageTransformer(false, AlphaTransformer())

        /**
         * https://blog.csdn.net/yuemitengfeng/article/details/80109655
         *
         * 由于viewpager的预刷新机制，他会预先缓存一个页面，然后如果你只有两个页面，那切换的时候两个页面的onresume都不会走。
         * 为了解决这个问题，我建立了一个BaseFragment，并让两个fragment继承，实现方法onRefresh；
         * 在activity中实现mViewPager.addOnPageChangeListener，在
         * public void onPageSelected中调用 ((BaseFragment)mFragments.get(position)).onRefresh();
         * 完成切换页面刷新
         */
        mTabLayout.setupWithViewPager(mViewPager)
    }
}
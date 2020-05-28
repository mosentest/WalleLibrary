package mo.wall.org.behavior

import android.content.res.Configuration
import android.os.Bundle
import android.os.Message
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity
import mo.wall.org.behavior.fragment.FirstFragment
import mo.wall.org.behavior.fragment.ScrollFragment
import mo.wall.org.behavior.view.MyHeaderView
import org.wall.mo.base.adapter.MaxLifecyclePagerAdapter

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
    private lateinit var mViewPager: ViewPager

    private lateinit var mTopIcon: LinearLayout
    private lateinit var mTopName: TextView
    private lateinit var mTopIcon2: ImageView
    private lateinit var mTopName2: LinearLayout

    private lateinit var mHeader: MyHeaderView


//    private lateinit var fragments: ArrayList<androidx.fragment.app.Fragment>
    private lateinit var titles: ArrayList<String>


    override fun handleMessageAct(msg: Message?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_behavior2)




        mHeader = findViewById<MyHeaderView>(R.id.header)


        mTabLayout = findViewById(R.id.stopView)
        mViewPager = findViewById(R.id.viewPager)
        //mViewPager.setMyHeaderView(mHeader)


        mTopIcon = findViewById<LinearLayout>(R.id.topIcon)
        mTopName = findViewById<TextView>(R.id.topName)
        mTopIcon2 = findViewById<ImageView>(R.id.topIcon2)
        mTopName2 = findViewById<LinearLayout>(R.id.topName2)

        mTopIcon.setOnClickListener {
            Toast.makeText(this, "icon", Toast.LENGTH_SHORT).show()
        }
        mTopName.setOnClickListener {
            Toast.makeText(this, "name", Toast.LENGTH_SHORT).show()
        }
        mTopIcon2.setOnClickListener {
            Toast.makeText(this, "icon2", Toast.LENGTH_SHORT).show()
        }
        mTopName2.setOnClickListener {
            Toast.makeText(this, "name2", Toast.LENGTH_SHORT).show()
        }
//        fragments = ArrayList<androidx.fragment.app.Fragment>()
        titles = ArrayList<String>()

        titles.add("button简单使用")
        titles.add("Scroll滚动通知")
//        fragments.add(FirstFragment.newInstance(Bundle()))
//        fragments.add(ScrollFragment.newInstance(Bundle()))

        mViewPager.adapter = object : MaxLifecyclePagerAdapter(supportFragmentManager, lifecycle) {

            override fun getItem(position: Int): Fragment {
                return when (position) {
                    1 -> FirstFragment.newInstance(Bundle())
                    2 -> ScrollFragment.newInstance(Bundle())
                    else -> FirstFragment.newInstance(Bundle())
                }
            }

            override fun getCount(): Int {
                return titles.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titles.get(position)
            }
        }.apply {
            notifyDataSetChanged()
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

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean, newConfig: Configuration?) {
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig)
        mHeader.let {
            it.alpha = 1f
            it.requestLayout()
        }
    }
}
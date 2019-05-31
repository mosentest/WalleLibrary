package mo.wall.org.statusbar

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.TextView
import mo.wall.org.R
import org.wall.mo.base.activity.AbsWithOneV4FragmentActivity

class StatusbarActivity : AbsWithOneV4FragmentActivity() {

    override fun getContainerViewId(): Int {
        return R.id.fragment_container
    }

    override fun createFragment(): Fragment {
        return StatusbarFragment.newInstance(Bundle())
    }

    override fun doAttach(): Boolean {
        return false
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_statusbar
    }


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    override fun initData() {

    }


    override fun parseIntentData(intent: Intent?) {

    }

    override fun initClick() {
        findViewById<TextView>(R.id.tvLeftBack).setOnClickListener({
            onBackPressed()
        })
    }
}

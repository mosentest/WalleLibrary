package mo.wall.org.statusbar

import android.os.Bundle
import mo.wall.org.R
import org.wall.mo.base.activity.AbsAppCompatActivity

class StatusbarActivity : AbsAppCompatActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_statusbar;
    }

    override fun initView() {
        supportFragmentManager.beginTransaction()
                .addToBackStack("") //回退栈
                .replace(R.id.fragment_container, StatusbarFragment())
                .commit();
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initClick() {

    }
}

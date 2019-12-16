package mo.wall.org.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import mo.wall.org.R
import org.wall.mo.compat.statusbar.StatusBarUtil

/**
 * 作者 create by moziqi on 2018/7/6
 * 邮箱 709847739@qq.com
 * 说明
 **/
open abstract class BaseAppCompatActivity : AppCompatActivity() {

    var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            handleMessageAct(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
//        StatusBarUtil.setStatusBarDarkTheme(this, false)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        val titleExtra = intent.getStringExtra("title")
        supportActionBar!!.setTitle(titleExtra)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        //在这里设置
        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
        StatusBarUtil.setStatusBarDarkTheme(this, false)
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        //在这里设置
        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
        StatusBarUtil.setStatusBarDarkTheme(this, false)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    abstract fun handleMessageAct(msg: Message?)
}
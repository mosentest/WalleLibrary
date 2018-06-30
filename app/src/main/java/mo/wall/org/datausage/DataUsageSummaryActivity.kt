package mo.wall.org.datausage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import mo.wall.org.R
import org.ziqi.librarydatausagesummary.DataUsageSummaryHelper

/**
 * 作者 create by moziqi on 2018/6/30
 * 邮箱 709847739@qq.com
 * 说明
 **/
class DataUsageSummaryActivity : AppCompatActivity() {

    var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_usage_summary)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        val titleExtra = intent.getStringExtra("title")
        supportActionBar!!.setTitle(titleExtra)

        //在这里，应该判断是不是在系统区
        //system/priv-app
        //system/app
        //因为我是从用户区root进去，代码直接判断了是否有root权限
        //我已经没做系统内置应用了 - -..
        //如果不是在这里区，不执行以下代码保证程序不奔溃

        var thread = object : Thread() {
            override fun run() {
                super.run()
                //test
                DataUsageSummaryHelper.getSimTotalData(applicationContext, 1, 1, 1);
            }
        }
        thread.start()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
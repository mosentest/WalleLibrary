package mo.wall.org.ntp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import mo.wall.org.R
import org.wall.mo.utils.ntp.LowNtpTrustedTime
import org.wall.mo.utils.ntp.NtpHelper
import org.ziqi.librarydatausagesummary.DataUsageSummaryHelper

/**
 * 作者 create by moziqi on 2018/6/30
 * 邮箱 709847739@qq.com
 * 说明
 **/
class NtpActivity : AppCompatActivity() {

    var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ntp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        val titleExtra = intent.getStringExtra("title")
        supportActionBar!!.setTitle(titleExtra)

        //普通应用用不上
        var thread = object : Thread() {
            override fun run() {
                super.run()
                val ntpTrustedTime = LowNtpTrustedTime.forceRefresh(applicationContext);
                val message = handler.obtainMessage()


                val cachedNtpTime = LowNtpTrustedTime.getCachedNtpTime();
                NtpHelper.setCurrentTimeMillis(cachedNtpTime);

                message.obj = ntpTrustedTime;
                handler.sendMessage(message)
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
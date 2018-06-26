package mo.wall.org

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            Toast.makeText(applicationContext, "==" + msg!!.obj, Toast.LENGTH_SHORT).show();
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //测试是否正常
        //ACache.get(this).put("aaa", "aaa");
        //val asString = ACache.get(this).getAsString("aaa");

        var thread = object : Thread() {
            override fun run() {
                super.run()
                val ntpTrustedTime = NtpUtils.forceRefresh(applicationContext);
                val message = handler.obtainMessage()
                message.obj = ntpTrustedTime;
                handler.sendMessage(message)
            }
        }
        thread.start()
    }
}
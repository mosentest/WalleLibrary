package mo.wall.org.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import mo.wall.org.R
import mo.wall.org.datausage.DataUsageSummaryActivity
import mo.wall.org.dropdownmenu.DropDownMenuActivity
import mo.wall.org.statusbar.StatusbarActivity
import org.wall.mo.compat.NotificationCompat

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //创建NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("channelId", "channelId", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = getNotification()
        notificationManager.notify(11, notification)
//        startForeground(1, getNotification())
    }

    fun getNotification(): Notification {
        var builder = Notification.Builder(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("channelId")
        }
        val intent = Intent(this, DataUsageSummaryActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pIntent = PendingIntent.getActivity(this, 1, intent, 0)
        //点击跳转的intent
        builder.setContentIntent(pIntent);
        builder.setContentText("内容")
        builder.setContentTitle("标题")
        //可以点击通知栏的删除按钮删除
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
        return builder.build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "启动成功:" + MyService().javaClass.name, Toast.LENGTH_LONG).show()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

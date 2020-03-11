package org.wall.mo.compat.notification;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

@TargetApi(26)
@SuppressLint("NewApi")
public class NotificationCompat26 {

	public static void sendNormal(Context context, NotificationManager notificationManager, PendingIntent pendingIntent,
			int notifyId, int smallIcon, Bitmap largeIcon, String ticker, String contentTitle, String contentText,
			boolean autoCannel) {
		// ChannelId为"1",ChannelName为"Channel1"
		String packageName = context.getPackageName();
		NotificationChannel channel = new NotificationChannel(packageName, packageName,
				NotificationManager.IMPORTANCE_DEFAULT);
		channel.enableLights(true); // 是否在桌面icon右上角展示小红点
		channel.setLightColor(Color.GREEN); // 小红点颜色
		channel.setShowBadge(true); // 是否在久按桌面图标时显示此渠道的通知
		notificationManager.createNotificationChannel(channel);

		Notification.Builder builder = new Notification.Builder(context, packageName); // 与channelId对应
		// icon title text必须包含，不然影响桌面图标小红点的展示
		builder.setSmallIcon(smallIcon)//
				.setContentTitle(contentTitle)//
				.setContentText(contentText)//
				.setWhen(System.currentTimeMillis())//
				.setContentIntent(pendingIntent)//
				.setTicker(ticker)// 设置状态栏的显示的信息
				.setAutoCancel(autoCannel)// 设置可以清除
				.setNumber(5); // 久按桌面图标时允许的此条通知的数量
		Notification build = builder.build();//
		notificationManager.notify(notifyId, build);
	}

}

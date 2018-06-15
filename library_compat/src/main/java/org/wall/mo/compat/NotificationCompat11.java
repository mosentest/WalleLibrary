package org.wall.mo.compat;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

public class NotificationCompat11 {
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public static void sendNormal(Context context, NotificationManager notificationManager, PendingIntent pendingIntent,
			int notifyId, int smallIcon, Bitmap largeIcon, String ticker, String contentTitle, String contentText,
			boolean autoCannel) {
		// 3构建通知
		Notification.Builder builder = new Notification.Builder(context)
				// API 11添加的方法
				.setSmallIcon(smallIcon)//
				.setContentIntent(pendingIntent)
				// 设置状态栏的小标题
				.setLargeIcon(largeIcon)// 设置下拉列表里的图标
				.setWhen(System.currentTimeMillis())//
				.setTicker(ticker)// 设置状态栏的显示的信息
				.setAutoCancel(autoCannel)// 设置可以清除
				.setContentTitle(contentTitle) // 设置下拉列表里的标题
				.setContentText(contentText); // 设置可以清除
		Notification notification = builder.getNotification();// API
		// 通知
		notificationManager.notify(notifyId, notification);
	}
}

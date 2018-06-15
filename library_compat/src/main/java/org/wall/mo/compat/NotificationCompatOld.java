package org.wall.mo.compat;

import java.lang.reflect.Method;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;

public class NotificationCompatOld {
	
	public static void sendNormal(Context context, NotificationManager notificationManager, PendingIntent pendingIntent,
			int notifyId, int smallIcon, Bitmap largeIcon, String ticker, String contentTitle, String contentText,
			boolean autoCannel) {
		Notification notification = new Notification(smallIcon, ticker, System.currentTimeMillis());
		//new API remove method
		// notification.setLatestEventInfo(context, contentTitle, contentText, pendingIntent);
		try {
			Method method = notification.getClass().getMethod("setLatestEventInfo", Context.class, CharSequence.class,
					CharSequence.class, PendingIntent.class);
			method.invoke(notification, context, contentTitle, contentText, pendingIntent);
			notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击通知之后自动消失
			notificationManager.notify(notifyId, notification);
		} catch (Exception e) {

		}

	}
}

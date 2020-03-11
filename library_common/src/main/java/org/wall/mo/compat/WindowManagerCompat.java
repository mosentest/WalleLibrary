package org.wall.mo.compat;

import android.os.Build;
import android.view.WindowManager;

/**
 * 悬浮窗适配的，参考这里，不想写<br/>
 * https://www.jianshu.com/p/d9f5b0801c6b
 * 
 * @author Administrator
 *
 */
public class WindowManagerCompat {

	/**
	 * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
	 * <uses-permission android:name=
	 * "android.permission.SYSTEM_OVERLAY_WINDOW"/>
	 * 
	 * @param mWindowParams
	 */
	public static void setWindowType(WindowManager.LayoutParams mWindowParams) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			mWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
		} else {
			mWindowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		}
	}
}

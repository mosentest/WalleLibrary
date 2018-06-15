package org.wall.mo.compat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

public class PackageManagerCompat26 {

	@SuppressLint("NewApi")
	public static boolean canRequestPackageInstalls(PackageManager pm) {
		return pm.canRequestPackageInstalls();
	}

	/**
	 * 跳转授权页面
	 * 
	 * @param context
	 */
	public static void goInstallAuthorized(Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && context.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.O) {
			Uri parse = Uri.parse("package:" + context.getPackageName());
			Intent intent = new Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES", parse);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}
}

package org.wall.mo.compat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

/**
 * 这里作者的更完善 <br/>
 * https://github.com/czy1121/settingscompat
 * 
 * @author moziqi
 *
 */
public class SettingsCompat23 {

	@SuppressLint("NewApi")
	public static boolean canDrawOverlays(Context context) {
		return Settings.canDrawOverlays(context);
	}
}

package org.wall.mo.compat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

/**
 * 这里作者的更完善 <br/>
 * https://github.com/czy1121/settingscompat
 * <p>
 * 正常产品不需要悬浮窗，就不整理这个
 *
 * @author moziqi
 */
public class SettingsCompat23 {

    @SuppressLint("NewApi")
    public static boolean canDrawOverlays(Context context) {
        return Settings.canDrawOverlays(context);
    }
}

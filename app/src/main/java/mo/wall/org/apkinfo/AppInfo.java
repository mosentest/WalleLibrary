package mo.wall.org.apkinfo;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-13 14:49
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class AppInfo {

    static PackageManager pm;

    public static String getVersionCode(Activity activity, String pkgName) {
        if (pm == null) {
            pm = activity.getPackageManager();
        }
        try {
            PackageInfo packageInfo = pm.getPackageInfo(pkgName, 0);

            return String.valueOf(packageInfo.versionCode);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getversionName(Activity activity, String pkgName) {
        if (pm == null) {
            pm = activity.getPackageManager();
        }
        try {
            PackageInfo packageInfo = pm.getPackageInfo(pkgName, 0);
            return String.valueOf(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTargetSdkVersion(Activity activity) {
        return "" + activity.getApplicationInfo().targetSdkVersion;
    }

    public static String getMinSdkVersion(Activity activity, String pkgName) {
        try {
            if (pm == null) {
                pm = activity.getPackageManager();
            }
            PackageInfo packageInfo = pm.getPackageInfo(pkgName, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return "" + packageInfo.applicationInfo.minSdkVersion;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Activity activity, String pkgName) {

        PackageInfo packageInfo = null;
        try {
            if (pm == null) {
                pm = activity.getPackageManager();
            }
            //https://blog.csdn.net/yichu0719/article/details/39670193
            packageInfo = pm.getPackageInfo(pkgName, 0);
            return packageInfo.applicationInfo.loadLabel(pm).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

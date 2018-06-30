package org.wall.mo.utils.ntp;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import org.wall.mo.utils.ShellUtils;

import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 作者 create by moziqi on 2018/6/26
 * 邮箱 709847739@qq.com
 * 说明
 * adb shell su
 * settings put global ntp_server ntp1.aliyun.com
 **/
public class NtpHelper {

    public static final String NTP_SERVER = "ntp_server";
    public static final String NTP_TIMEOUT = "ntp_timeout";

    /**
     * 参考设置亮度
     * 参考链接
     * https://www.cnblogs.com/lwbqqyumidi/p/4127012.html
     * 需要的权限
     * <uses-permission android:name="android.permission.READ_SETTINGS"/>
     * <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
     * <uses-permission android:name="android.permission.WRITE_SECURE_SETTINGS"/>
     *
     * @param context
     * @param newNtpServer
     */
    public static void setNtpServer(Context context, String newNtpServer) {
        try {
            ContentResolver contentResolver = context.getContentResolver();
            Settings.System.putString(contentResolver, NTP_SERVER, newNtpServer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置当前的系统时间
     *
     * @param time
     * @return true表示设置成功, false表示设置失败
     */
    public static boolean setCurrentTimeMillis(long time) {
        try {
            if (ShellUtils.checkRootPermission()) {
                Process process2 = Runtime.getRuntime().exec("su");
                DataOutputStream os2 = new DataOutputStream(process2.getOutputStream());
                os2.writeBytes("settings put global ntp_server ntp1.aliyun.com");
                os2.flush();
            }
            if (ShellUtils.checkRootPermission()) {
                TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
                Date current = new Date(time);
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd.HHmmss");
                String datetime = df.format(current);
                Process process = Runtime.getRuntime().exec("su");
                DataOutputStream os = new DataOutputStream(process.getOutputStream());
                //os.writeBytes("setprop persist.sys.timezone GMT\n");
                os.writeBytes("/system/bin/date -s " + datetime + "\n");
                os.writeBytes("clock -w\n");
                os.writeBytes("exit\n");
                os.flush();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

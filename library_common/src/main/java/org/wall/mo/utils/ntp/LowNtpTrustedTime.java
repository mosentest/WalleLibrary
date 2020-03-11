package org.wall.mo.utils.ntp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * 作者 create by moziqi on 2018/6/26
 * 邮箱 709847739@qq.com
 * 说明 源码来自这里扣出来android.util.NtpTrustedTime
 * 参考了
 * https://blog.csdn.net/aaa1050070637/article/details/68061127
 * https://blog.csdn.net/mapdigit/article/details/7669325
 **/
public class LowNtpTrustedTime {

    private static String TAG = "LowNtpTrustedTime";

    /**
     * 反射这单例没多大意义，垮进程单例是失效的
     *
     * @param context
     * @return
     */
    public static long getNtpTrustedTime(final Context context) {
        try {
            final Class<?> NtpClass = Class.forName("android.util.NtpTrustedTime");
            Object obj = new Object();
            Method method = NtpClass.getMethod("getInstance", Context.class);
            Object gi = method.invoke(obj, context);
            method = NtpClass.getMethod("forceRefresh");
            Object receiverce = method.invoke(gi);
            if ((Boolean) receiverce) {
                method = NtpClass.getMethod("getCachedNtpTime");
                Object cachedNtpTime = method.invoke(gi);
                return (Long) cachedNtpTime;
            }
        } catch (Exception e) {
            Log.wtf(TAG, e);
        }
        return -1;
    }


    private static boolean mHasCache;
    private static long mCachedNtpTime;
    private static long mCachedNtpElapsedRealtime;
    private static long mCachedNtpCertainty;

    public static final String NTP_SERVER = "ntp_server";
    public static final String NTP_TIMEOUT = "ntp_timeout";


    public static boolean forceRefresh(final Context context) {

        final Resources res = context.getResources();
        final ContentResolver resolver = context.getContentResolver();

//        final String defaultServer = res.getString(com.android.internal.R.string.config_ntpServer);
        //http://androidxref.com/4.4.2_r1/xref/frameworks/base/core/res/res/values/config.xml#1045
        //final String defaultServer = "2.android.pool.ntp.org";
        final String defaultServer = "ntp1.aliyun.com";
//        final long defaultTimeout = res.getInteger(com.android.internal.R.integer.config_ntpTimeout);
        final long defaultTimeout = 20000;

        final String secureServer = Settings.System.getString(resolver, NTP_SERVER);
        final long timeout = Settings.System.getLong(resolver, NTP_TIMEOUT, defaultTimeout);

        final String server = secureServer != null ? secureServer : defaultServer;

        boolean result = false;
        SntpClient client = new SntpClient();
        //pool.ntp.org
        if (client.requestTime(server, (int) timeout)) {
            mHasCache = true;
            result = true;
            mCachedNtpTime = client.getNtpTime();
            mCachedNtpElapsedRealtime = client.getNtpTimeReference();
            mCachedNtpCertainty = client.getRoundTripTime() / 2;
        }
//        NtpHelper.setNtpServer(mContext,defaultServer);
        // + ":" + mCachedNtpTime
        return result;
    }

    public static long getCachedNtpTime() {
        return mCachedNtpTime;
    }

    public static long currentTimeMillis() {
        if (!mHasCache) {
            throw new IllegalStateException("Missing authoritative time source");
        }

        // current time is age after the last ntp cache; callers who
        // want fresh values will hit makeAuthoritative() first.
        return mCachedNtpTime + getCacheAge();
    }

    public static long getCacheAge() {
        if (mHasCache) {
            return SystemClock.elapsedRealtime() - mCachedNtpElapsedRealtime;
        } else {
            return Long.MAX_VALUE;
        }
    }

    public static long getCachedNtpTimeReference() {
        return mCachedNtpElapsedRealtime;
    }
}

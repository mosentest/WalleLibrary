package org.ziqi.librarydatausagesummary;

import android.content.Context;
import android.net.INetworkStatsService;
import android.net.INetworkStatsSession;
import android.net.NetworkStatsHistory;
import android.net.NetworkTemplate;
import android.net.TrafficStats;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import org.wall.mo.utils.relect.MethodUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author 莫梓奇
 * <p>
 * 参考过：https://blog.csdn.net/wdyshowtime/article/details/78532182
 */
public class DataUsageSummaryHelper {

    private final static String TAG = "DataUsageSummaryHelper";

    /**
     *
     * @param context
     * @param simNum
     * @param startTime
     * @param endTime
     * @return
     */
    public static long get_simTotalData(final Context context, final int simNum, final long startTime, final long endTime) {
        long value = 0;
        try {
            INetworkStatsService mStatsService = null;
            // wait a few seconds before kicking off
            try {
                //ServiceManager.getService("netstats")
                Class clazzServiceManager = Class.forName("android.os.ServiceManager");
                Object getService = MethodUtils.invokeStaticMethod(clazzServiceManager, "getService", new Object[]{"netstats"});
                mStatsService = INetworkStatsService.Stub.asInterface((IBinder) getService);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(2 * 1000);
            //强制更新
            mStatsService.forceUpdate();
            INetworkStatsSession mStatsSession = mStatsService.openSession();
            try {
                Class clazzNetworkTemplate = Class.forName("android.net.NetworkTemplate");
                Class clazzNetworkStatsHistory = Class.forName("android.net.NetworkStatsHistory");
                Object buildTemplateMobileAll = MethodUtils.invokeStaticMethod(clazzNetworkTemplate, "buildTemplateMobileAll", new Object[]{getActiveSubscriberId(context)});
                NetworkTemplate mTemplate = NetworkTemplate.buildTemplateMobileAll(getActiveSubscriberId(context));
                //NetworkStatsHistory.FIELD_ALL
                NetworkStatsHistory networkStatsHistory = mStatsSession.getHistoryForNetwork(mTemplate, /*FIELD_RX_BYTES | FIELD_TX_BYTES*/0xFFFFFFFF);

                NetworkStatsHistory.Entry entry = null;
                long bucketDuration = networkStatsHistory.getBucketDuration();
                entry = networkStatsHistory.getValues(startTime, endTime, System.currentTimeMillis(), entry);
                value = entry != null ? entry.rxBytes + entry.txBytes : 0;
                final String totalPhrase = Formatter.formatFileSize(context, value);
                long totalBytes = networkStatsHistory.getTotalBytes();
                int afterBucketCount2 = networkStatsHistory.getIndexAfter(startTime);
                int beforeBucketCount2 = networkStatsHistory.getIndexBefore(startTime);
                android.util.Log.i(TAG, "afterBucketCount2:" + afterBucketCount2 + ",beforeBucketCount2:" + beforeBucketCount2);
                android.util.Log.i(TAG, "bucketDuration =" + bucketDuration + "totalPhrase:" + totalPhrase + ",totalBytes:" + totalBytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                //TrafficStats.closeQuietly(mStatsSession);
                MethodUtils.invokeStaticMethod(TrafficStats.class, "closeQuietly", new Object[]{mStatsSession});
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            android.util.Log.i(TAG, "getMsimTotalData:finally");
        }
        android.util.Log.i(TAG, "total_value1:" + value);
        return value;
    }

    private static final String TEST_SUBSCRIBER_PROP = "test.subscriberid";

    /**
     * @param context
     * @return
     */
    private static String getActiveSubscriberId(Context context) {
        //final TelephonyManager tele = TelephonyManager.from(context);
        final TelephonyManager tele = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String actualSubscriberId = tele.getSubscriberId();
        String result = "";
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            //SystemProperties.get(TEST_SUBSCRIBER_PROP, actualSubscriberId)
            result = (String) MethodUtils.invokeStaticMethod(clazz, "get", new Object[]{TEST_SUBSCRIBER_PROP, actualSubscriberId});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

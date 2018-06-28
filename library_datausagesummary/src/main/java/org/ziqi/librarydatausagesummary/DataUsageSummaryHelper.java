package org.wall.mo.utils.data_usage_summary;

import android.content.Context;
import android.net.TrafficStats;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

/**
 * @author 莫梓奇
 * <p>
 * 参考过：https://blog.csdn.net/wdyshowtime/article/details/78532182
 */
public class DataUsageSummaryHelper {

    private final static String TAG = "DataUsageSummaryHelper";

    public static long get_simTotalData(final Context context, final int simNum, final long startTime, final long endTime) {
        long value = 0;
        try {
            // wait a few seconds before kicking off
            INetworkStatsService mStatsService = INetworkStatsService.Stub.asInterface(ServiceManager.getService(Context.NETWORK_STATS_SERVICE));
            Thread.sleep(2 * 1000);
            //强制更新
            mStatsService.forceUpdate();
            INetworkStatsSession mStatsSession = mStatsService.openSession();
            NetworkTemplate mTemplate = NetworkTemplate.buildTemplateMobileAll(getMsimActiveSubscriberId(context, simNum));
            NetworkStatsHistory networkStatsHistory = mStatsSession.getHistoryForNetwork(mTemplate, /*FIELD_RX_BYTES | FIELD_TX_BYTES*/NetworkStatsHistory.FIELD_ALL);
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
            TrafficStats.closeQuietly(mStatsSession);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
        } finally {
            android.util.Log.i(TAG, "getMsimTotalData:finally");
        }
        android.util.Log.i(TAG, "total_value1:" + value);
        return value;
    }

    private static String getActiveSubscriberId(Context context) {
        final TelephonyManager tele = TelephonyManager.from(context);
        final String actualSubscriberId = tele.getSubscriberId();
        return SystemProperties.get(TEST_SUBSCRIBER_PROP, actualSubscriberId);
    }

}

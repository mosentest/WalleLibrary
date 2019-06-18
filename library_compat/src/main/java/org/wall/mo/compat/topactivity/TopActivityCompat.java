package org.wall.mo.compat.topactivity;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import org.wall.mo.compat.topactivity.models.AndroidAppProcess;
import org.wall.mo.compat.topactivity.models.Stat;
import org.wall.mo.compat.topactivity.models.Statm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/6/9 9:03 AM
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class TopActivityCompat {

    /**
     * 获取栈顶包名
     *
     * @param context
     * @return
     */
    public static String printForegroundTask(Context context) {
        String currentApp = "";
        final int uid = android.os.Process.myUid();
        if (uid == 0 || uid == android.os.Process.SYSTEM_UID) {
            currentApp = printForegroundTaskLow(context);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                /**
                 * <uses-permission
                 *   android:name="android.permission.PACKAGE_USAGE_STATS"
                 *   tools:ignore="ProtectedPermissions" />
                 */
                UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
                long time = System.currentTimeMillis();
                List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
                if (appList != null && appList.size() > 0) {
                    SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                    for (UsageStats usageStats : appList) {
                        mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                    }
                    if (mySortedMap != null && !mySortedMap.isEmpty()) {
                        currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                    }
                } else {
                    if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.LOLLIPOP) {
                        //在 5.0 系统可正常使用，但到 5.1 系统上就不行，调用该方法只能返回应用本身的相关信息
                        //https://stackoverflow.com/questions/28066231/how-to-gettopactivity-name-or-get-currently-running-application-package-name-i
                        currentApp = activityManager(context).getRunningAppProcesses().get(0).processName;
                    } else {
                        String foregroundApp = getForegroundApp();
                        if (TextUtils.isEmpty(foregroundApp)) {
                            currentApp = printForegroundTaskLollipop(context);
                        } else {
                            currentApp = foregroundApp;
                        }
                    }
                }
            } else {
                currentApp = printForegroundTaskLow(context);
            }
        }
        return currentApp;
    }

    /**
     * 低版本获取栈顶包名
     * <uses-permission android:name="android.permission.GET_TASKS"/>
     *
     * @param context
     * @return
     */
    private static String printForegroundTaskLow(Context context) {
        String currentApp;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(Integer.MAX_VALUE);
        currentApp = runningTasks.get(0).topActivity.getPackageName();
        return currentApp;
    }

    /**
     * https://stackoverflow.com/questions/3873659/android-how-can-i-get-the-current-foreground-activity-from-a-service
     *
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String printForegroundTaskLollipop(Context context) {
        final int PROCESS_STATE_TOP = 2;
        try {
            Field processStateField = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
            List<ActivityManager.RunningAppProcessInfo> processes = activityManager(context).getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo process : processes) {
                if (
                    // Filters out most non-activity processes
                        process.importance <= ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                                &&
                                // Filters out processes that are just being
                                // _used_ by the process with the activity
                                process.importanceReasonCode == 0
                ) {
                    int state = processStateField.getInt(process);

                    if (state == PROCESS_STATE_TOP) {
                        String[] processNameParts = process.processName.split(":");
                        String packageName = processNameParts[0];

                        /*
                         If multiple candidate processes can get here,
                         it's most likely that apps are being switched.
                         The first one provided by the OS seems to be
                         the one being switched to, so we stop here.
                         */
                        return packageName;
                    }
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static ActivityManager activityManager(Context context) {
        return (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }


    /**
     * first app user
     */
    private static final int AID_APP = 10000;
    /**
     * offset for uid ranges for each user
     */
    private static final int AID_USER = 100000;

    /**
     * https://blog.csdn.net/lixpjita39/article/details/52734628
     *
     * @return
     */
    private static String getForegroundApp() {
        File[] files = new File("/proc").listFiles();
        int lowestOomScore = Integer.MAX_VALUE;
        String foregroundProcess = null;
        for (File file : files) {
            if (!file.isDirectory()) {
                continue;
            }
            int pid;
            try {
                pid = Integer.parseInt(file.getName());
            } catch (NumberFormatException e) {
                continue;
            }
            try {
                String cgroup = read(String.format("/proc/%d/cgroup", pid));
                String[] lines = cgroup.split("\n");
                String cpuSubsystem;
                String cpuaccctSubsystem;

                if (lines.length == 2) {//有的手机里cgroup包含2行或者3行，我们取cpu和cpuacct两行数据
                    cpuSubsystem = lines[0];
                    cpuaccctSubsystem = lines[1];
                } else if (lines.length == 3) {
                    cpuSubsystem = lines[0];
                    cpuaccctSubsystem = lines[2];
                } else {
                    continue;
                }
                if (!cpuaccctSubsystem.endsWith(Integer.toString(pid))) {
                    // not an application process
                    continue;
                }
                if (cpuSubsystem.endsWith("bg_non_interactive")) {
                    // background policy
                    continue;
                }
                String cmdline = read(String.format("/proc/%d/cmdline", pid));
                if (cmdline.contains("com.android.systemui")) {
                    continue;
                }
                int uid = Integer.parseInt(
                        cpuaccctSubsystem.split(":")[2].split("/")[1].replace("uid_", ""));
                if (uid >= 1000 && uid <= 1038) {
                    // system process
                    continue;
                }
                int appId = uid - AID_APP;
                int userId = 0;
                // loop until we get the correct user id.
                // 100000 is the offset for each user.
                while (appId > AID_USER) {
                    appId -= AID_USER;
                    userId++;
                }
                if (appId < 0) {
                    continue;
                }
                // u{user_id}_a{app_id} is used on API 17+ for multiple user account support.
                // String uidName = String.format("u%d_a%d", userId, appId);
                File oomScoreAdj = new File(String.format("/proc/%d/oom_score_adj", pid));
                if (oomScoreAdj.canRead()) {
                    int oomAdj = Integer.parseInt(read(oomScoreAdj.getAbsolutePath()));
                    if (oomAdj != 0) {
                        continue;
                    }
                }
                int oomscore = Integer.parseInt(read(String.format("/proc/%d/oom_score", pid)));
                if (oomscore < lowestOomScore) {
                    lowestOomScore = oomscore;
                    foregroundProcess = cmdline;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return foregroundProcess;
    }

    private static String read(String path) throws IOException {
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        output.append(reader.readLine());
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            output.append('\n').append(line);
        }
        reader.close();
        return output.toString().trim();//不调用trim()，包名后面会带有乱码
    }

    /*
     *这是获取列表的
     */
    private static String getRunningForegroundApps(Context context) throws IOException, PackageManager.NameNotFoundException {
        // Get a list of running apps
        List<AndroidAppProcess> processes = AndroidProcesses.getRunningForegroundApps(context);

        for (AndroidAppProcess process : processes) {
            // Get some information about the process
            String processName = process.name;

            Stat stat = process.stat();
            int pid = stat.getPid();
            int parentProcessId = stat.ppid();
            long startTime = stat.stime();
            int policy = stat.policy();
            char state = stat.state();

            Statm statm = process.statm();
            long totalSizeOfProcess = statm.getSize();
            long residentSetSize = statm.getResidentSetSize();

            PackageInfo packageInfo = process.getPackageInfo(context, 0);
            String appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
        }
        return "";
    }
}

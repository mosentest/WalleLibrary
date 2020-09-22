package org.wall.mo.compat;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Build;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class InstallP {

    private static final String TAG = InstallP.class.getSimpleName();

    // 适配android9的安装方法。
    public static void installP(String packageName, String apkFilePath) {
        File apkFile = new File(apkFilePath);
        PackageInstaller packageInstaller = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            packageInstaller = getContext().getPackageManager().getPackageInstaller();

            PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL);
            sessionParams.setSize(apkFile.length());

            int sessionId = createSession(packageInstaller, sessionParams);
            if (sessionId != -1) {
                boolean copySuccess = copyInstallFile(packageInstaller, sessionId, apkFilePath, packageName);
                if (copySuccess) {
                    execInstallCommand(getContext(), packageInstaller, sessionId);
                }
            }
        }

    }

    private static int createSession(PackageInstaller packageInstaller,
                                     PackageInstaller.SessionParams sessionParams) {
        int sessionId = -1;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                sessionId = packageInstaller.createSession(sessionParams);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionId;
    }

    private static boolean copyInstallFile(PackageInstaller packageInstaller,
                                           int sessionId, String apkFilePath, String packageName) {
        InputStream in = null;
        OutputStream out = null;
        PackageInstaller.Session session = null;
        boolean success = false;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                File apkFile = new File(apkFilePath);
                session = packageInstaller.openSession(sessionId);
                out = session.openWrite(packageName + "_base.apk", 0, apkFile.length());
                in = new FileInputStream(apkFile);
                int total = 0, c;
                byte[] buffer = new byte[65536];
                while ((c = in.read(buffer)) != -1) {
                    total += c;
                    out.write(buffer, 0, c);
                }
                session.fsync(out);
                Log.i(TAG, "streamed " + total + " bytes");
                success = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(out);
            closeQuietly(in);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                closeQuietly(session);
            }
        }
        return success;
    }

    private static void execInstallCommand(Context context, PackageInstaller packageInstaller, int sessionId) {
        PackageInstaller.Session session = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                session = packageInstaller.openSession(sessionId);
                Intent intent = new Intent(context, InstallResultReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                session.commit(pendingIntent.getIntentSender());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                closeQuietly(session);
            }
        }
    }

    static class InstallResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                final int status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS,
                        PackageInstaller.STATUS_FAILURE);
                if (status == PackageInstaller.STATUS_SUCCESS) {
                    // success
                } else {
                    Log.e(TAG, intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE));
                }
            }
        }
    }


    private static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }
    }


    /**
     * Context对象
     */
    private static Context CONTEXT_INSTANCE;

    static {
        getContext();
    }


    /**
     * 取得Context对象
     * PS:必须在主线程调用
     *
     * @return Context
     */
    private static Context getContext() {
        if (CONTEXT_INSTANCE == null) {
            try {
                Class<?> ActivityThread = Class.forName("android.app.ActivityThread");
                Method activityThread = ActivityThread.getMethod("currentActivityThread");
                Object currentActivityThread = activityThread.invoke(ActivityThread);//获取currentActivityThread 对象
                Method getApplication = currentActivityThread.getClass().getMethod("getApplication");
                CONTEXT_INSTANCE = (Context) getApplication.invoke(currentActivityThread);//获取 Context对象
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return CONTEXT_INSTANCE;
    }
}

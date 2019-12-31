package com.mo.github.invokeinstallpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * PackageManagerTests.java
 *
 *
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2019/2/21-9:21
 * desc   :
 * version: 1.0
 */
public class PackageManagerCompatP {

    private final static String TAG = PackageManagerCompatP.class.getSimpleName();


    public static final long MAX_WAIT_TIME = 25 * 1000;

    public static final long WAIT_TIME_INCR = 5 * 1000;

    private static final String SECURE_CONTAINERS_PREFIX = "/mnt/asec";

    private Context mContext;

    public PackageManagerCompatP(Context context) {
        this.mContext = context;
    }


    private static class LocalIntentReceiver {
        private final SynchronousQueue<Intent> mResult = new SynchronousQueue<>();

        private IIntentSender.Stub mLocalSender = new IIntentSender.Stub() {
            @Override
            public void send(int code, Intent intent, String resolvedType, IBinder whitelistToken,
                             IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) {
                try {
                    mResult.offer(intent, 5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        public IntentSender getIntentSender() {
            Class<?> aClass = null;
            try {
                aClass = Class.forName("android.content.IntentSender");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (aClass == null) {
                return null;
            }
            try {
                Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
                for (Constructor<?> declaredConstructor : declaredConstructors) {
                    Log.i(TAG, "declaredConstructor.toString():" + declaredConstructor.toString());
                    Log.i(TAG, "declaredConstructor.getName():" + declaredConstructor.getName());
                    Class<?>[] parameterTypes = declaredConstructor.getParameterTypes();
                    for (Class<?> parameterType : parameterTypes) {
                        Class aClass1 = parameterType.getClass();
                        Log.i(TAG, "parameterTypes...aClass1:" + aClass1.getName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Constructor constructor = null;
            try {
                constructor = aClass.getDeclaredConstructor(IIntentSender.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            if (constructor == null) {
                return null;
            }
            Object o = null;
            try {
                o = constructor.newInstance((IIntentSender) mLocalSender);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return (IntentSender) o;
//                 new IntentSender((IIntentSender) mLocalSender)
        }

        public Intent getResult() {
            try {
                return mResult.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private PackageManager getPm() {
        return mContext.getPackageManager();
    }

    private PackageInstaller getPi() {
        return getPm().getPackageInstaller();
    }

    private void writeSplitToInstallSession(PackageInstaller.Session session, String inPath,
                                            String splitName) throws RemoteException {
        long sizeBytes = 0;
        final File file = new File(inPath);
        if (file.isFile()) {
            sizeBytes = file.length();
        } else {
            return;
        }

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(inPath);
            out = session.openWrite(splitName, 0, sizeBytes);

            int total = 0;
            byte[] buffer = new byte[65536];
            int c;
            while ((c = in.read(buffer)) != -1) {
                total += c;
                out.write(buffer, 0, c);
            }
            session.fsync(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.closeQuietly(out);
            IoUtils.closeQuietly(in);
            IoUtils.closeQuietly(session);
        }
    }

    /**
     * 入口方法
     * String apkPackageName = ""; //填写安装的包名
     * String apkPath = "";//填写安装的路径
     **/
    public void testReplaceFlagSdcardInternal(String apkPackageName, String apkPath) throws Exception {
        // Do not run on devices with emulated external storage.
        if (Environment.isExternalStorageEmulated()) {
            return;
        }

        int iFlags = 0x00000008;// PackageManager.INSTALL_EXTERNAL 0x00000008
        int rFlags = 0;
        //这个暂时用不上
        //InstallParams ip = sampleInstallFromRawResource(iFlags, false);
        Uri uri = Uri.fromFile(new File(apkPath));
        GenericReceiver receiver = new ReplaceReceiver(apkPackageName);
        int replaceFlags = rFlags | 0x00000002;//PackageManager.INSTALL_REPLACE_EXISTING 0x00000002
        try {
            invokeInstallPackage(uri, replaceFlags, receiver, true);
            //assertInstall(ip.pkg, iFlags, ip.pkg.installLocation);
        } catch (Exception e) {
            Log.e(TAG, "Failed with exception : " + e);
        } finally {
//            cleanUpInstall(ip);
        }
    }

//    class InstallParams {
//        Uri packageURI;
//
//        PackageParser.Package pkg;
//
//        InstallParams(String outFileName, int rawResId) throws PackageParserException {
//            this.pkg = getParsedPackage(outFileName, rawResId);
//            this.packageURI = Uri.fromFile(new File(pkg.codePath));
//        }
//
//        InstallParams(PackageParser.Package pkg) {
//            this.packageURI = Uri.fromFile(new File(pkg.codePath));
//            this.pkg = pkg;
//        }
//
//        long getApkSize() {
//            File file = new File(pkg.codePath);
//            return file.length();
//        }
//    }
//
//    private InstallParams sampleInstallFromRawResource(int flags, boolean cleanUp)
//            throws Exception {
//        return installFromRawResource("install.apk", android.R.raw.install, flags, cleanUp, false, -1,
//                PackageInfo.INSTALL_LOCATION_UNSPECIFIED);
//    }

//    private void cleanUpInstall(InstallParams ip) {
//
//    }


    private void cleanUpInstall(String pkgName) throws Exception {
        if (pkgName == null) {
            return;
        }
        Log.i(TAG, "Deleting package : " + pkgName);
        try {
            final ApplicationInfo info = getPm().getApplicationInfo(pkgName,
                    PackageManager.MATCH_UNINSTALLED_PACKAGES);
            if (info != null) {
                //PackageManager.DELETE_ALL_USERS
                final LocalIntentReceiver localReceiver = new LocalIntentReceiver();
                //这是卸载，不调用
//                getPi().uninstall(pkgName,
//                        0x00000002,
//                        localReceiver.getIntentSender());
                localReceiver.getResult();
                assertUninstalled(info);
            }
        } catch (IllegalArgumentException | PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void assertUninstalled(ApplicationInfo info) throws Exception {
        File nativeLibraryFile = new File(info.nativeLibraryDir);
        Log.e(TAG, "Native library directory " + info.nativeLibraryDir
                + " should be erased" + nativeLibraryFile.exists());
    }

    private void invokeInstallPackage(Uri packageUri, int flags, GenericReceiver receiver,
                                      boolean shouldSucceed) {
        mContext.registerReceiver(receiver, receiver.filter);
        synchronized (receiver) {
            final String inPath = packageUri.getPath();
            PackageInstaller.Session session = null;
            try {
                final PackageInstaller.SessionParams sessionParams =
                        new PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL);
                try {
                    //sessionParams.installFlags = flags;
                    Field installFlags = sessionParams.getClass().getDeclaredField("installFlags");
                    installFlags.set(sessionParams, flags);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                final int sessionId = getPi().createSession(sessionParams);
                session = getPi().openSession(sessionId);
                writeSplitToInstallSession(session, inPath, "base.apk");
                final LocalIntentReceiver localReceiver = new LocalIntentReceiver();
                session.commit(localReceiver.getIntentSender());
                final Intent result = localReceiver.getResult();
                final int status = result.getIntExtra(PackageInstaller.EXTRA_STATUS,
                        PackageInstaller.STATUS_FAILURE);
                if (shouldSucceed) {
                    if (status != PackageInstaller.STATUS_SUCCESS) {
                        Log.e(TAG, "Installation should have succeeded, but got code " + status);
                    }
                } else {
                    if (status == PackageInstaller.STATUS_SUCCESS) {
                        Log.e(TAG, "Installation should have failed");
                    }
                    // We'll never get a broadcast since the package failed to install
                    return;
                }
                // Verify we received the broadcast
                long waitTime = 0;
                while ((!receiver.isDone()) && (waitTime < MAX_WAIT_TIME)) {
                    try {
                        receiver.wait(WAIT_TIME_INCR);
                        waitTime += WAIT_TIME_INCR;
                    } catch (InterruptedException e) {
                        Log.i(TAG, "Interrupted during sleep", e);
                    }
                }
                if (!receiver.isDone()) {
                    Log.e(TAG, "Timed out waiting for PACKAGE_ADDED notification");
                }
            } catch (IllegalArgumentException | IOException | RemoteException e) {
                Log.e(TAG, "Failed to install package; path=" + inPath, e);
            } finally {
                IoUtils.closeQuietly(session);
                mContext.unregisterReceiver(receiver);
            }
        }
    }


    private abstract static class GenericReceiver extends BroadcastReceiver {
        private boolean doneFlag = false;

        boolean received = false;

        Intent intent;

        IntentFilter filter;

        abstract boolean notifyNow(Intent intent);

        @Override
        public void onReceive(Context context, Intent intent) {
            if (notifyNow(intent)) {
                synchronized (this) {
                    received = true;
                    doneFlag = true;
                    this.intent = intent;
                    notifyAll();
                }
            }
        }

        public boolean isDone() {
            return doneFlag;
        }

        public void setFilter(IntentFilter filter) {
            this.filter = filter;
        }
    }

    class ReplaceReceiver extends GenericReceiver {
        String pkgName;

        final static int INVALID = -1;

        final static int REMOVED = 1;

        final static int ADDED = 2;

        final static int REPLACED = 3;

        int removed = INVALID;

        // for updated system apps only
        boolean update = false;

        ReplaceReceiver(String pkgName) {
            this.pkgName = pkgName;
            filter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
            filter.addAction(Intent.ACTION_PACKAGE_ADDED);
            if (update) {
                filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
            }
            filter.addDataScheme("package");
            super.setFilter(filter);
        }

        public boolean notifyNow(Intent intent) {
            String action = intent.getAction();
            Uri data = intent.getData();
            String installedPkg = data.getEncodedSchemeSpecificPart();
            if (pkgName == null || !pkgName.equals(installedPkg)) {
                return false;
            }
            if (Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
                removed = REMOVED;
            } else if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
                if (removed != REMOVED) {
                    return false;
                }
                boolean replacing = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false);
                if (!replacing) {
                    return false;
                }
                removed = ADDED;
                if (!update) {
                    return true;
                }
            } else if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
                if (removed != ADDED) {
                    return false;
                }
                removed = REPLACED;
                return true;
            }
            return false;
        }
    }

}

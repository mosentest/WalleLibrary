package mo.wall.org.webviewinscrollview;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

/**
 * Copyright (C), 2018-2020
 * Author: 我是地球人
 * Date: 2020/10/29 4:29 PM
 * Description:
 * History:
 */
public class ContextX extends ContextWrapper {

    private Context mContext;

    public ContextX(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public AssetManager getAssets() {
        return mContext.getAssets();
    }

    @Override
    public Resources getResources() {
        return mContext.getResources();
    }

    @Override
    public PackageManager getPackageManager() {
        return mContext.getPackageManager();
    }

    @Override
    public ContentResolver getContentResolver() {
        return mContext.getContentResolver();
    }

    @Override
    public Looper getMainLooper() {
        return mContext.getMainLooper();
    }

    @Override
    public Context getApplicationContext() {
        return mContext.getApplicationContext();
    }

    @Override
    public void setTheme(int resid) {
        mContext.setTheme(resid);
    }

    @Override
    public Resources.Theme getTheme() {
        return mContext.getTheme();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mContext.getClassLoader();
    }

    @Override
    public String getPackageName() {
        List<ApplicationInfo> installedApplications = getPackageManager().getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        if (installedApplications != null && !installedApplications.isEmpty()) {
            ApplicationInfo applicationInfo = installedApplications.get(new Random().nextInt(installedApplications.size() - 1));
            String packageName = applicationInfo.packageName;
            return packageName;
        }
        return mContext.getPackageName();
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return mContext.getApplicationInfo();
    }

    @Override
    public String getPackageResourcePath() {
        return mContext.getPackageResourcePath();
    }

    @Override
    public String getPackageCodePath() {
        return mContext.getPackageCodePath();
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return mContext.getSharedPreferences(name, mode);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean moveSharedPreferencesFrom(Context sourceContext, String name) {
        return mContext.moveSharedPreferencesFrom(sourceContext, name);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean deleteSharedPreferences(String name) {
        return mContext.deleteSharedPreferences(name);
    }

    @Override
    public FileInputStream openFileInput(String name) throws FileNotFoundException {
        return mContext.openFileInput(name);
    }

    @Override
    public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
        return mContext.openFileOutput(name, mode);
    }

    @Override
    public boolean deleteFile(String name) {
        return mContext.deleteFile(name);
    }

    @Override
    public File getFileStreamPath(String name) {
        return mContext.getFileStreamPath(name);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public File getDataDir() {
        return mContext.getDataDir();
    }

    @Override
    public File getFilesDir() {
        return mContext.getFilesDir();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public File getNoBackupFilesDir() {
        return mContext.getNoBackupFilesDir();
    }

    @Nullable
    @Override
    public File getExternalFilesDir(@Nullable String type) {
        return mContext.getExternalFilesDir(type);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public File[] getExternalFilesDirs(String type) {
        return mContext.getExternalFilesDirs(type);
    }

    @Override
    public File getObbDir() {
        return mContext.getObbDir();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public File[] getObbDirs() {
        return mContext.getObbDirs();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public File getCacheDir() {
        return mContext.getCacheDir();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public File getCodeCacheDir() {
        return mContext.getCodeCacheDir();
    }

    @Nullable
    @Override
    public File getExternalCacheDir() {
        return mContext.getExternalCacheDir();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public File[] getExternalCacheDirs() {
        return mContext.getExternalCacheDirs();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public File[] getExternalMediaDirs() {
        return mContext.getExternalMediaDirs();
    }

    @Override
    public String[] fileList() {
        return mContext.fileList();
    }

    @Override
    public File getDir(String name, int mode) {
        return mContext.getDir(name, mode);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return mContext.openOrCreateDatabase(name, mode, factory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, @Nullable DatabaseErrorHandler errorHandler) {
        return mContext.openOrCreateDatabase(name, mode, factory, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean moveDatabaseFrom(Context sourceContext, String name) {
        return mContext.moveDatabaseFrom(sourceContext, name);
    }

    @Override
    public boolean deleteDatabase(String name) {
        return mContext.deleteDatabase(name);
    }

    @Override
    public File getDatabasePath(String name) {
        return mContext.getDatabasePath(name);
    }

    @Override
    public String[] databaseList() {
        return mContext.databaseList();
    }

    @Override
    public Drawable getWallpaper() {
        return mContext.getWallpaper();
    }

    @Override
    public Drawable peekWallpaper() {
        return mContext.peekWallpaper();
    }

    @Override
    public int getWallpaperDesiredMinimumWidth() {
        return mContext.getWallpaperDesiredMinimumWidth();
    }

    @Override
    public int getWallpaperDesiredMinimumHeight() {
        return mContext.getWallpaperDesiredMinimumHeight();
    }

    @Override
    public void setWallpaper(Bitmap bitmap) throws IOException {
        mContext.setWallpaper(bitmap);
    }

    @Override
    public void setWallpaper(InputStream data) throws IOException {
        mContext.setWallpaper(data);
    }

    @Override
    public void clearWallpaper() throws IOException {
        mContext.clearWallpaper();
    }

    @Override
    public void startActivity(Intent intent) {
        mContext.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        mContext.startActivity(intent, options);
    }

    @Override
    public void startActivities(Intent[] intents) {
        mContext.startActivities(intents);
    }

    @Override
    public void startActivities(Intent[] intents, Bundle options) {
        mContext.startActivities(intents, options);
    }

    @Override
    public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        mContext.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    @Override
    public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) throws IntentSender.SendIntentException {
        mContext.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        mContext.sendBroadcast(intent);
    }

    @Override
    public void sendBroadcast(Intent intent, @Nullable String receiverPermission) {
        mContext.sendBroadcast(intent, receiverPermission);
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, @Nullable String receiverPermission) {
        mContext.sendOrderedBroadcast(intent, receiverPermission);
    }

    @Override
    public void sendOrderedBroadcast(@NonNull Intent intent, @Nullable String receiverPermission, @Nullable BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {
        mContext.sendOrderedBroadcast(intent, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle user) {
        mContext.sendBroadcastAsUser(intent, user);
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission) {
        mContext.sendBroadcastAsUser(intent, user, receiverPermission);
    }

    @Override
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {
        mContext.sendOrderedBroadcastAsUser(intent, user, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Override
    public void sendStickyBroadcast(Intent intent) {
        mContext.sendStickyBroadcast(intent);
    }

    @Override
    public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {
        mContext.sendStickyOrderedBroadcast(intent, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Override
    public void removeStickyBroadcast(Intent intent) {
        mContext.removeStickyBroadcast(intent);
    }

    @Override
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {
        mContext.sendStickyBroadcastAsUser(intent, user);
    }

    @Override
    public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {
        mContext.sendStickyOrderedBroadcastAsUser(intent, user, resultReceiver, scheduler, initialCode, initialData, initialExtras);

    }

    @Override
    public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {
        mContext.removeStickyBroadcastAsUser(intent, user);
    }

    @Nullable
    @Override
    public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter) {
        return mContext.registerReceiver(receiver, filter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter, int flags) {
        return mContext.registerReceiver(receiver, filter, flags);
    }

    @Nullable
    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, @Nullable String broadcastPermission, @Nullable Handler scheduler) {
        return mContext.registerReceiver(receiver, filter, broadcastPermission, scheduler);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, @Nullable String broadcastPermission, @Nullable Handler scheduler, int flags) {
        return mContext.registerReceiver(receiver, filter, broadcastPermission, scheduler, flags);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        mContext.unregisterReceiver(receiver);
    }

    @Nullable
    @Override
    public ComponentName startService(Intent service) {
        return mContext.startService(service);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public ComponentName startForegroundService(Intent service) {
        return mContext.startForegroundService(service);
    }

    @Override
    public boolean stopService(Intent service) {
        return mContext.stopService(service);
    }

    @Override
    public boolean bindService(Intent service, @NonNull ServiceConnection conn, int flags) {
        return mContext.bindService(service, conn, flags);
    }

    @Override
    public void unbindService(@NonNull ServiceConnection conn) {
        mContext.unbindService(conn);
    }

    @Override
    public boolean startInstrumentation(@NonNull ComponentName className, @Nullable String profileFile, @Nullable Bundle arguments) {
        return mContext.startInstrumentation(className, profileFile, arguments);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        return mContext.getSystemService(name);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public String getSystemServiceName(@NonNull Class<?> serviceClass) {
        return mContext.getSystemServiceName(serviceClass);
    }

    @Override
    public int checkPermission(@NonNull String permission, int pid, int uid) {
        return mContext.checkPermission(permission, pid, uid);
    }

    @Override
    public int checkCallingPermission(@NonNull String permission) {
        return mContext.checkCallingPermission(permission);
    }

    @Override
    public int checkCallingOrSelfPermission(@NonNull String permission) {
        return mContext.checkCallingOrSelfPermission(permission);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int checkSelfPermission(@NonNull String permission) {
        return mContext.checkSelfPermission(permission);
    }

    @Override
    public void enforcePermission(@NonNull String permission, int pid, int uid, @Nullable String message) {
        mContext.enforcePermission(permission, pid, uid, message);
    }

    @Override
    public void enforceCallingPermission(@NonNull String permission, @Nullable String message) {
        mContext.enforceCallingPermission(permission, message);
    }

    @Override
    public void enforceCallingOrSelfPermission(@NonNull String permission, @Nullable String message) {
        mContext.enforceCallingOrSelfPermission(permission, message);
    }

    @Override
    public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
        mContext.grantUriPermission(toPackage, uri, modeFlags);
    }

    @Override
    public void revokeUriPermission(Uri uri, int modeFlags) {
        mContext.revokeUriPermission(uri, modeFlags);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void revokeUriPermission(String toPackage, Uri uri, int modeFlags) {
        mContext.revokeUriPermission(toPackage, uri, modeFlags);
    }

    @Override
    public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
        return mContext.checkUriPermission(uri, pid, uid, modeFlags);
    }

    @Override
    public int checkCallingUriPermission(Uri uri, int modeFlags) {
        return mContext.checkCallingUriPermission(uri, modeFlags);
    }

    @Override
    public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
        return mContext.checkCallingOrSelfUriPermission(uri, modeFlags);
    }

    @Override
    public int checkUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags) {
        return mContext.checkUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags);
    }

    @Override
    public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {
        mContext.enforceUriPermission(uri, pid, uid, modeFlags, message);
    }

    @Override
    public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {
        mContext.enforceCallingUriPermission(uri, modeFlags, message);
    }

    @Override
    public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {
        mContext.enforceCallingOrSelfUriPermission(uri, modeFlags, message);
    }

    @Override
    public void enforceUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags, @Nullable String message) {
        mContext.enforceUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags, message);
    }

    @Override
    public Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
        return mContext.createPackageContext(packageName, flags);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Context createContextForSplit(String splitName) throws PackageManager.NameNotFoundException {
        return mContext.createContextForSplit(splitName);
    }

    @Override
    public Context createConfigurationContext(@NonNull Configuration overrideConfiguration) {
        return mContext.createConfigurationContext(overrideConfiguration);
    }

    @Override
    public Context createDisplayContext(@NonNull Display display) {
        return mContext.createDisplayContext(display);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Context createDeviceProtectedStorageContext() {
        return mContext.createDeviceProtectedStorageContext();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean isDeviceProtectedStorage() {
        return mContext.isDeviceProtectedStorage();
    }
}

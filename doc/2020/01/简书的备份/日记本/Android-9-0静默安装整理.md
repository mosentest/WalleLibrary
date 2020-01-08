在之前这个抽源码实现9.0静默曾经是可以的[https://www.jianshu.com/p/3013a64ce4b5](https://www.jianshu.com/p/3013a64ce4b5)
但是后面不知道为什么hide方法反射失败了，改为这次整理的9.0静默代码
```
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.mo.github.invokeinstallpackage.IoUtils.closeQuietly;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-31 14:55
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class AndroidP {


    private static final String TAG = AndroidP.class.getSimpleName();

    // 适配android9的安装方法。
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void install28(Context context, String apkFilePath, PackageManager packageManager) {
        File apkFile = new File(apkFilePath);
        PackageInstaller packageInstaller = packageManager.getPackageInstaller();
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL);
        sessionParams.setSize(apkFile.length());

        int sessionId = createSession(packageInstaller, sessionParams);
        if (sessionId != -1) {
            boolean copySuccess = copyInstallFile(packageInstaller, sessionId, apkFilePath);
            if (copySuccess) {
                execInstallCommand(context, packageInstaller, sessionId);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int createSession(PackageInstaller packageInstaller,
                              PackageInstaller.SessionParams sessionParams) {
        int sessionId = -1;
        try {
            sessionId = packageInstaller.createSession(sessionParams);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionId;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean copyInstallFile(PackageInstaller packageInstaller,
                                    int sessionId, String apkFilePath) {
        InputStream in = null;
        OutputStream out = null;
        PackageInstaller.Session session = null;
        boolean success = false;
        try {
            File apkFile = new File(apkFilePath);
            session = packageInstaller.openSession(sessionId);
            out = session.openWrite("base.apk", 0, apkFile.length());
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(out);
            closeQuietly(in);
            closeQuietly(session);
        }
        return success;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void execInstallCommand(Context context, PackageInstaller packageInstaller, int sessionId) {
        PackageInstaller.Session session = null;
        try {
            session = packageInstaller.openSession(sessionId);
            Intent intent = new Intent(context, InstallResultReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            session.commit(pendingIntent.getIntentSender());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(session);
        }
    }
}
```



```
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;

public class InstallResultReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final int status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS,
                    PackageInstaller.STATUS_FAILURE);
            if (status == PackageInstaller.STATUS_SUCCESS) {
                // success
            } else {
                //Log.e(TAG, intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE));
            }
        }
    }
}
```

```

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

public final class IoUtils {

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    public static void closeQuietly(Socket c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }
    }

}
```

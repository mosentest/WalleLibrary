package mo.wall.org.apkinfo;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright (C), 2018-2019
 * Author:
 * Date: 2019-09-12 11:36
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public final class AFPUtils {

    /**
     * AFP
     *
     * @param ai
     * @param packageName
     * @return
     */
    public static String G(Context ai, String packageName) {
        try {
            return B(ai.getPackageManager().getApplicationInfo(packageName, 0).sourceDir);
        } catch (Exception appContext) {
            return null;
        }
    }

    public static final String B(String path) throws Exception {
        return D(new File(path));
    }

    private static String D(File file) throws Exception {
        FileInputStream e = null;
        try {
            e = new FileInputStream(file);
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] bArr = new byte[1024];
                int read;
                do {
                    read = e.read(bArr);
                    if (read > 0) {
                        md.update(bArr, 0, read);
                    }
                } while (read != -1);
                return E(md.digest());
            } catch (NoSuchAlgorithmException unused2) {
                throw new Exception("No such algorithm.");
            } catch (IOException ww) {
                throw new Exception("IO exception.");
            } catch (Throwable th) {
                try {
                    if (e != null) {
                        e.close();
                    }
                } catch (IOException unused3) {
                }
            }
        } catch (FileNotFoundException unused4) {
            throw new Exception("File not found or not accessible.");
        }
        return "";
    }

    private static String E(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
        }
        return sb.toString();
    }
}

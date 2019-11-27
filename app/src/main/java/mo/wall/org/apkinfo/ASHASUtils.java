package mo.wall.org.apkinfo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * Copyright (C), 2018-2019
 * Author:
 * Date: 2019-09-12 15:36
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public final class ASHASUtils {


    public static String G(Context context,String packageName) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException, CertificateException {
        StringBuilder certs = new StringBuilder();
        for (Signature md : context.getPackageManager().getPackageInfo(packageName, 64).signatures) {
            certs.append(C(MessageDigest.getInstance("SHA1").digest(H(md).getEncoded())));
            certs.append(";");
        }
        return certs.toString();
    }

    private static PublicKey H(Signature signature) throws CertificateException {
        return CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(signature.toByteArray())).getPublicKey();
    }

    public static String C(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int i = (b >>> 4) & 15;
            int i2 = 0;
            while (true) {
                char c;
                if (i < 0 || i > 9) {
                    c = (char) ((i - 10) + 97);
                } else {
                    c = (char) (i + 48);
                }
                buf.append(c);
                i = b & 15;
                int i3 = i2 + 1;
                if (i2 >= 1) {
                    break;
                }
                i2 = i3;
            }
        }
        return buf.toString();
    }

}

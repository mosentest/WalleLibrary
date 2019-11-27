package mo.wall.org.apkinfo;

import android.content.Context;

import java.io.File;

/**
 * Copyright (C), 2018-2019
 * Author:
 * Date: 2019-09-12 11:56
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public final class apk_sizeUtils {

    public static long D(Context ai,String packageName) {
        try {
            long length = new File(ai.getPackageManager().getApplicationInfo(packageName, 0).publicSourceDir).length();
            return length;
        } catch (Exception e) {
            return -1;
        }
    }
}

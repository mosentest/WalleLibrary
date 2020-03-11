package org.wall.mo.utils;

import android.text.TextUtils;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/5/30 上午9:22
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class StringUtils {

    /**
     * 校验对象是否为空
     *
     * @param object
     * @return
     */
    public static boolean isNULL(Object object) {
        return object == null;
    }

    /**
     * 本地校验数据是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    /**
     * 校验服务器数据是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmptyForServer(CharSequence str) {
        return isEmpty(str) || "null".equals(str.toString().trim()) || "NULL".equals(str.toString().trim());
    }
}

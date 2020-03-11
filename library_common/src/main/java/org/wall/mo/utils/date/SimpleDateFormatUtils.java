package org.wall.mo.utils.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-21 14:37
 * Description:
 * History:
 * ThreadLocal解决SimpleDateFormat问题
 * https://blog.csdn.net/luotuomianyang/article/details/52313045
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class SimpleDateFormatUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // 第一次调用get将返回null
    private static ThreadLocal threadLocal = new ThreadLocal();

    // 获取线程的变量副本，如果不覆盖initialValue，第一次get返回null，故需要初始化一个SimpleDateFormat，并set到threadLocal中
    public static DateFormat getDateFormat() {
        DateFormat df = (DateFormat) threadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(DATE_FORMAT);
            threadLocal.set(df);
        }
        return df;
    }
}

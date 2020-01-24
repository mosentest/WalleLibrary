package org.wall.mo.http;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-24 08:39
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class NetResult<T> {

    public T bean;
    public Header header;

    public static class Header {
        public String msg;
        public String code;
    }
}

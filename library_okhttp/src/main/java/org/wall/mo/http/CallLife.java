package org.wall.mo.http;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-21 17:03
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class CallLife {

    private List<Call> callLists = new ArrayList<>();

    /**
     * 每个P或者VM都独立一个
     *
     * @return
     */
    public static CallLife getCallLife() {
        return new CallLife();
    }

    public void add(Call call) {
        callLists.add(call);
    }

    public void remove() {
        for (Call call : callLists) {
            if (!call.isCanceled()) {
                call.cancel();
            }
        }
        callLists.clear();
    }
}

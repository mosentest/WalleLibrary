package org.wall.mo.http;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 自定义网络回调接口
 */
public interface NetCall {
    public void success(Call call, Response response);

    public void failed(Call call, Exception e);

    public CallLife getCallLife();
}
package org.wall.mo.http;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * 自定义网络回调接口
 * 一般情况，后台数据都会做加密处理，所以返回字符串后续自己出来，是最好的
 */
public abstract class NetCall {

    public abstract void success(Call call, String object);

    public abstract void failed(Call call, Exception e);

    public abstract CallLife getCallLife();

    /**
     * https://www.jianshu.com/p/6e07b80fd9f9
     *
     * @return
     */
    Type getType() {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (type instanceof Class) {
            return type;
        } else {
            return new TypeToken<String>() {
            }.getType();
        }
    }
}
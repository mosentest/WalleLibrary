package org.wall.mo.http;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * 自定义网络回调接口
 */
public abstract class NetCall<T> {

    public abstract void success(Call call, T object);

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
            return new TypeToken<T>() {
            }.getType();
        }
    }
}
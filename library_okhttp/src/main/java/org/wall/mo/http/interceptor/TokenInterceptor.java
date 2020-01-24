package org.wall.mo.http.interceptor;

import android.content.Context;

import org.wall.mo.http.OkHttpX;
import org.wall.mo.http.util.SPUtils;
import org.wall.mo.utils.log.WLog;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-22 15:55
 * Description:
 * History:
 * <p>
 * 这种只适合在头部修改参数，如果要在请求参数修改的话，不适合修改
 * 这种只适合在头部修改参数，如果要在请求参数修改的话，不适合修改
 * 这种只适合在头部修改参数，如果要在请求参数修改的话，不适合修改
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class TokenInterceptor implements Interceptor {
    private final static String TAG = TokenInterceptor.class.getSimpleName();
    private final static byte[] LOCK = new byte[1];
    private Context context;
    public TokenInterceptor(Context context) {
        this.context = context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        WLog.i(TAG, "response.code():" + response.code());
        //xxxx需求,假设是404
        if (response.code() == 404) {
            //判断token是否失效了
            Request newRequest = null;
            String token = SPUtils.getInstance(context).getString("token", "bbb");
            WLog.i(TAG, "token:" + token);
            synchronized (LOCK) {
                String tokenLock = SPUtils.getInstance(context).getString("token", "bbb");
                WLog.i(TAG, "in lock token:" + tokenLock);
                if (token.equals(tokenLock)) {
                    //判断token是否一样
//                    throw new IOException("token error");
                    try {
                        Response tokenResponse = OkHttpX.getInstance(context).postForm("url", new HashMap<>());
                        SPUtils.getInstance(context).putString("token", "xxx");
                    } catch (Exception e) {
                        //只能处理是io异常，如果其他异常，整个应用可能会gg
                        //所以 请求token产生的异常都转成io异常
                        throw new IOException(e);
                    }
                } else {
                    //已经token更换成功，不处理
                }
            }
            token = SPUtils.getInstance(context).getString("token", "bbb");
            Request.Builder newBuilder = chain.request().newBuilder();
            newBuilder.addHeader("token", token);
            newRequest = newBuilder.build();
            response = chain.proceed(newRequest);
        }
        return response;
    }
}

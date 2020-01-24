package org.wall.mo.http.interceptor;

import org.wall.mo.utils.log.WLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 如果仅仅是服务器返回不了，不建议重试，服务器也有压力
 * 这种不适合用于没网络的情况的重试
 * 有retryOnConnectionFailure 就好了
 */
public class RetryInterceptor implements Interceptor {

    private final static String TAG = RetryInterceptor.class.getSimpleName();

    public int maxRetry;//最大重试次数

    private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

    public RetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    /**
     * 在断网的情况下不会重试
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        WLog.i(TAG, "retryNum=" + retryNum);
        Response response = chain.proceed(request);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            WLog.i(TAG, "retryNum=" + retryNum);
            response = chain.proceed(request);
        }
        return response;
    }
}
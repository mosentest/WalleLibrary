package org.wall.mo.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * https://segmentfault.com/a/1190000012922833
 * https://www.jianshu.com/p/647dfd41194e?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation
 */
public class HttpCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //这个也可以针对特定的请求url不做缓存
        Response originResponse = chain.proceed(request);
        if (originResponse.body() != null) {
            long l = originResponse.body().contentLength();
            //大2m的大文件不缓存
            if (l >= 2 * 1024 * 1024) {
                return originResponse;
            }
        }
        //设置缓存时间为60秒，并移除了pragma消息头，移除它的原因是因为pragma也是控制缓存的一个消息头属性
        return originResponse
                .newBuilder()
                .removeHeader("pragma")
                .header("Cache-Control", "max-age=60")
                .build();
    }
}
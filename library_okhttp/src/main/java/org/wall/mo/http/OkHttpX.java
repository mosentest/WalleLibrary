package org.wall.mo.http;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.wall.mo.http.interceptor.HttpCacheInterceptor;
import org.wall.mo.http.interceptor.RetryInterceptor;
import org.wall.mo.http.cookies.CookiesManager;
import org.wall.mo.http.download.DownloadListener;
import org.wall.mo.http.interceptor.TokenInterceptor;
import org.wall.mo.http.ssl.HTTPSCerUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.Proxy;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-21 15:33
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class OkHttpX {

    public final static int READ_TIMEOUT = 100;

    public final static int CONNECT_TIMEOUT = 60;

    public final static int WRITE_TIMEOUT = 60;

    public static final MediaType JSON = MediaType.parse("application/json");

    private static final byte[] LOCKER = new byte[0];

    private static volatile OkHttpX mInstance;

    private OkHttpClient mOkHttpClient;

    private Gson mGson;

    private Context mContext;

    private CookiesManager mCookiesManager;

    private File cacheFile;

    /**
     * 是否打开log
     */
    private boolean debug;

    private OkHttpX(Context context) {
        this.mContext = context.getApplicationContext();
        mCookiesManager = new CookiesManager(mContext);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);//读取超时
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);//连接超时
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);//写入超时
        //支持HTTPS请求，跳过证书验证
        HTTPSCerUtils.setTrustAllCertificate(clientBuilder);

        clientBuilder.addInterceptor(new TokenInterceptor(context));

        cache(clientBuilder);

        retry(clientBuilder);

        //拦截器
        if (debug) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            clientBuilder.addInterceptor(httpLoggingInterceptor);
        }

        //不走代理网络
        clientBuilder.proxy(Proxy.NO_PROXY);
        //设置cookies
        clientBuilder.cookieJar(mCookiesManager);
        mOkHttpClient = clientBuilder.build();


        this.mGson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();
    }

    /***
     * 不建议服务器的重试
     * @param clientBuilder
     */
    private void retry(OkHttpClient.Builder clientBuilder) {
        clientBuilder.addInterceptor(new RetryInterceptor(1));

        //retryOnConnectionFailure 默认为true 会失败重连
        //https://www.cnblogs.com/ganchuanpu/p/8399681.html
        clientBuilder.retryOnConnectionFailure(true);
    }

    /**
     * 自带的okhttp缓存只能缓存get请求
     *
     * @param clientBuilder
     */
    private void cache(OkHttpClient.Builder clientBuilder) {
        //304 服务表示资源没改动过
        //https://segmentfault.com/a/1190000012922833

        //缓存文件夹
        cacheFile = new File(mContext.getCacheDir(), "ok_cache");
        //缓存大小为10M
        int cacheSize = 10 * 1024 * 1024;
        //创建缓存对象
        Cache cache = new Cache(cacheFile, cacheSize);
        clientBuilder.cache(cache);

        //自带的okhttp缓存只能缓存get请求
        /**
         *  @Nullable CacheRequest put(Response response) {
         *     String requestMethod = response.request().method();
         *
         *     if (HttpMethod.invalidatesCache(response.request().method())) {
         *       try {
         *         remove(response.request());
         *       } catch (IOException ignored) {
         *         // The cache cannot be written.
         *       }
         *       return null;
         *     }
         *     if (!requestMethod.equals("GET")) {
         *       // Don't cache non-GET responses. We're technically allowed to cache
         *       // HEAD requests and some POST requests, but the complexity of doing
         *       // so is high and the benefit is low.
         *       return null;
         *     }
         */
        clientBuilder.addNetworkInterceptor(new HttpCacheInterceptor());
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }


    private Request.Builder createDefaultRequest() {
        return new Request.Builder();
    }

    /**
     * 用户信息不关联，如果成功登陆进来，用户不一样，需要清除上一个用户的信息
     */
    public void logOutClear() {
        if (mCookiesManager != null) {
            mCookiesManager.getCookieStore().removeAll();
        }
        if (cacheFile != null) {
            File[] files = cacheFile.listFiles();
            for (File file : files) {
                if (file.exists()) {
                    file.delete();
                }
            }
        }

    }

    /**
     * 单例模式获取OkHttpUtil
     *
     * @return
     */
    public static OkHttpX getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCKER) {
                if (mInstance == null) {
                    mInstance = new OkHttpX(context);
                }
            }
        }
        return mInstance;
    }

    private Call preGet(String url, Map<String, String> bodyParams) {
        StringBuilder urlBuild = new StringBuilder();
        urlBuild.append(url);
        if (!url.endsWith("?")) {
            urlBuild.append("?");
        }
        if (bodyParams != null) {
            Set<Map.Entry<String, String>> entrySet = bodyParams.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                urlBuild.append(next.getKey());
                urlBuild.append("=");
                urlBuild.append(next.getValue());
                urlBuild.append("&");
            }
        }
        Request.Builder builder = createDefaultRequest();
        Request request = builder.get().url(urlBuild.toString()).build();
        return mOkHttpClient.newCall(request);
    }

    /**
     * get请求，同步方式，获取网络数据，是在主线程中执行的，需要新起线程，将其放到子线程中执行
     *
     * @param url
     * @return
     */
    public Response get(String url, Map<String, String> bodyParams) throws IOException {
        Call call = preGet(url, bodyParams);
        Response response = call.execute();
        return response;
    }

    /**
     * get请求，异步方式，获取网络数据，是在子线程中执行的，需要切换到主线程才能更新UI
     *
     * @param url
     * @param netCall
     * @return
     */
    public Call getAsync(String url, Map<String, String> bodyParams, final NetCall netCall) {
        Call call = null;
        try {
            call = preGet(url, bodyParams);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (netCall != null) {
                        netCall.failed(call, e);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (netCall != null) {
                            netCall.success(call, mGson.fromJson(response.body().string(), netCall.getType()));
                        }
                    } catch (Exception e) {
                        //所有异常都处理
                        if (debug) {
                            e.printStackTrace();
                        }
                        if (netCall != null) {
                            netCall.failed(call, e);
                        }
                    }

                }
            });
            if (netCall != null) {
                CallLife callLife = netCall.getCallLife();
                if (callLife != null) {
                    callLife.add(call);
                }
            }
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
            if (netCall != null) {
                netCall.failed(call, e);
            }
        }
        return call;
    }


    /**
     * post的请求参数，构造RequestBody
     *
     * @param bodyParams
     * @return
     */
    private RequestBody setRequestBody(Map<String, String> bodyParams) {
        RequestBody body = null;
        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        if (bodyParams != null) {
            Set<Map.Entry<String, String>> entries = bodyParams.entrySet();
            Iterator<Map.Entry<String, String>> entryIterator = entries.iterator();
            while (entryIterator.hasNext()) {
                Map.Entry<String, String> next = entryIterator.next();
                String key = next.getKey();
                String value = next.getValue();
                formEncodingBuilder.add(key, value);
            }
        }
        body = formEncodingBuilder.build();
        return body;

    }


    private Call prePost(String url, RequestBody requestBody) {
        RequestBody body = requestBody;
        Request.Builder requestBuilder = createDefaultRequest();
        Request request = requestBuilder.post(body).url(url).build();
        return mOkHttpClient.newCall(request);
    }

    /**
     * post请求，同步方式，提交数据，是在主线程中执行的，需要新起线程，将其放到子线程中执行
     *
     * @param url
     * @param bodyParams
     * @return
     */
    public Response postForm(String url, Map<String, String> bodyParams) throws IOException {
        Call call = prePost(url, setRequestBody(bodyParams));
        Response response = call.execute();
        return response;
    }


    /**
     * post请求，异步方式，提交数据，是在子线程中执行的，需要切换到主线程才能更新UI
     *
     * @param url
     * @param bodyParams
     * @param netCall
     */
    public Call postFormAsync(String url, Map<String, String> bodyParams, final NetCall netCall) {
        Call call = null;
        try {
            call = prePost(url, setRequestBody(bodyParams));
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (netCall != null) {
                        netCall.failed(call, e);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (netCall != null) {
                            netCall.success(call, mGson.fromJson(response.body().string(), netCall.getType()));
                        }
                    } catch (Exception e) {
                        //所有异常都处理
                        if (debug) {
                            e.printStackTrace();
                        }
                        if (netCall != null) {
                            netCall.failed(call, e);
                        }
                    }

                }
            });
            if (netCall != null) {
                CallLife callLife = netCall.getCallLife();
                if (callLife != null) {
                    callLife.add(call);
                }
            }
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
            if (netCall != null) {
                netCall.failed(call, e);
            }
        }
        return call;
    }


    public Response postJson(String url, Map<String, String> bodyParams) throws IOException {
        String json = mGson.toJson(bodyParams);
        Call call = prePost(url, RequestBody.create(JSON, json));
        Response response = call.execute();
        return response;
    }

    public Call postJsonAsync(String url, Map<String, String> bodyParams, final NetCall netCall) {
        Call call = null;
        try {
            String json = mGson.toJson(bodyParams);
            call = prePost(url, RequestBody.create(JSON, json));
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    netCall.failed(call, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (netCall != null) {
                            netCall.success(call, mGson.fromJson(response.body().string(), netCall.getType()));
                        }
                    } catch (Exception e) {
                        //所有异常都处理
                        if (debug) {
                            e.printStackTrace();
                        }
                        if (netCall != null) {
                            netCall.failed(call, e);
                        }
                    }
                }
            });
            if (netCall != null) {
                CallLife callLife = netCall.getCallLife();
                if (callLife != null) {
                    callLife.add(call);
                }
            }
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
            if (netCall != null) {
                netCall.failed(call, e);
            }
        }
        return call;
    }


    private Call preUpload(String url, Map<String, String> bodyParams, String fileKey, Map<String, String> uploadFileParams) {
        Call call = null;
        RequestBody requestBody = null;
        if (TextUtils.isEmpty(fileKey) || uploadFileParams == null || uploadFileParams.isEmpty()) {
            requestBody = setRequestBody(bodyParams);
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            if (bodyParams != null) {
                Set<Map.Entry<String, String>> entries = bodyParams.entrySet();
                Iterator<Map.Entry<String, String>> entryIterator = entries.iterator();
                while (entryIterator.hasNext()) {
                    Map.Entry<String, String> next = entryIterator.next();
                    String key = next.getKey();
                    String value = next.getValue();
                    builder.addFormDataPart(key, value);
                }
            }
            Set<Map.Entry<String, String>> uploadFileEntries = uploadFileParams.entrySet();
            Iterator<Map.Entry<String, String>> iterator = uploadFileEntries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> uploadFileNext = iterator.next();
                String fileName = uploadFileNext.getKey();
                String filePath = uploadFileNext.getValue();
                builder.addFormDataPart(fileKey, fileName, RequestBody.create(MultipartBody.FORM, new File(filePath)));
            }
            requestBody = builder.build();
        }
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        call = mOkHttpClient.newCall(request);
        return call;
    }


    /**
     * 异步上传文件
     *
     * @param url
     * @param bodyParams
     * @param fileKey
     * @param uploadFileParams
     * @param netCall
     * @return
     */
    public Call uploadAsync(String url,
                            Map<String, String> bodyParams,
                            String fileKey,
                            Map<String, String> uploadFileParams, final NetCall netCall) {
        Call call = null;
        try {
            call = preUpload(url, bodyParams, fileKey, uploadFileParams);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    netCall.failed(call, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (netCall != null) {
                            netCall.success(call, mGson.fromJson(response.body().string(), netCall.getType()));
                        }
                    } catch (Exception e) {
                        //所有异常都处理
                        if (debug) {
                            e.printStackTrace();
                        }
                        if (netCall != null) {
                            netCall.failed(call, e);
                        }
                    }
                }
            });
            if (netCall != null) {
                CallLife callLife = netCall.getCallLife();
                if (callLife != null) {
                    callLife.add(call);
                }
            }
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
            if (netCall != null) {
                netCall.failed(call, e);
            }
        }
        return call;
    }


    /**
     * 下载文件,支持断点续传
     *
     * @param url
     * @param startsPoint
     * @param fileName
     * @param downloadPath
     * @param downloadListener
     * @return
     */
    public Call download(String url, final long startsPoint, String fileName, String downloadPath, final DownloadListener downloadListener) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection", "close")    //这里不设置可能产生EOF错误
                .header("RANGE", "bytes=" + startsPoint + "-")//断点续传
                .build();
        // 发起请求
        Call call = null;
        try {
            call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    downloadListener.fail(-1, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int code = response.code();
                    try {
                        if (response.isSuccessful()) {
                            long tempStartPoint = startsPoint;
                            File file = new File(downloadPath + File.separator + fileName);
                            long length = response.body().contentLength();
                            if (length == 0) {
                                // 说明文件已经下载完，直接跳转安装就好
                                downloadListener.complete(file.getAbsolutePath());
                                return;
                            }
                            if (response.code() == 200) {
                                //说明后台不支持断点续传
                                tempStartPoint = 0;
                                if (file.exists()) {
                                    file.delete();
                                }
                            }
                            downloadListener.start(tempStartPoint);
                            // 保存文件到本地
                            InputStream is = null;
                            RandomAccessFile randomAccessFile = null;
                            BufferedInputStream bis = null;
                            byte[] buff = new byte[1024];
                            int len = 0;
                            try {
                                is = response.body().byteStream();
                                bis = new BufferedInputStream(is);
                                // 随机访问文件，可以指定断点续传的起始位置
                                randomAccessFile = new RandomAccessFile(file, "rwd");
                                randomAccessFile.seek(tempStartPoint);
                                while ((len = bis.read(buff)) != -1) {
                                    randomAccessFile.write(buff, 0, len);
                                    long fileLength = randomAccessFile.length();
                                    long index = fileLength / length + startsPoint;
                                    downloadListener.loading(index);
                                }
                                // 下载完成
                                downloadListener.complete(file.getAbsolutePath());
                            } catch (Exception e) {
                                //所有异常都处理
                                if (debug) {
                                    e.printStackTrace();
                                }
                                downloadListener.fail(-1, e);
                            } finally {
                                try {
                                    if (is != null) {
                                        is.close();
                                    }
                                    if (bis != null) {
                                        bis.close();
                                    }
                                    if (randomAccessFile != null) {
                                        randomAccessFile.close();
                                    }
                                } catch (Exception e) {
                                    //所有异常都处理
                                    if (debug) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } else {
                            downloadListener.fail(code, null);
                        }
                    } catch (Exception e) {
                        //所有异常都处理
                        if (debug) {
                            e.printStackTrace();
                        }
                        downloadListener.fail(-1, e);
                    }
                }
            });
        } catch (Exception e) {
            //所有异常都处理
            if (debug) {
                e.printStackTrace();
            }
            downloadListener.fail(-1, e);
        }
        return call;
    }
}

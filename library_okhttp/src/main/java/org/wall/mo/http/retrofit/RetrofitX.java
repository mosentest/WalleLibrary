package org.wall.mo.http.retrofit;

import android.content.Context;

import com.google.gson.Gson;

import org.wall.mo.http.OkHttpX;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-25 13:37
 * Description:
 * History:
 *
 * 参考资料
 * https://www.jianshu.com/p/308f3c54abdd/
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class RetrofitX {
    private volatile static RetrofitX instance;

    private final Retrofit retrofit;

    private final Context context;

    public RetrofitX(Context context) {
        this.context = context.getApplicationContext();

        Gson gson = OkHttpX.getInstance(context).getGson();
        OkHttpClient okHttpClient = OkHttpX.getInstance(context).getOkHttpClient();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

    public static RetrofitX getInstance(Context context) {
        if (instance == null) {
            synchronized (RetrofitX.class) {
                if (instance == null) {
                    instance = new RetrofitX(context);
                }
            }
        }
        return instance;
    }

}

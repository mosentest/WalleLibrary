package com.rmyh.recyclerviewsuspend.common;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wen on 2017/8/8.
 */

public class MyApplication extends Application {

    public  static Context context;

    public static Context getContext(){

        return context;
    };

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}

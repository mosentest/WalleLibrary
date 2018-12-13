/*
 * Copyright Txus Ballesteros 2015 (@txusballesteros)
 *
 * This file is part of some open source application.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * Contact: Txus Ballesteros <txus.ballesteros@gmail.com>
 */
package com.txusballesteros.demo;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.txusballesteros.SnakeView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private float[] values = new float[]{60, 70, 80, 90, 100,
            150, 150, 160, 170, 175, 180,
            170, 140, 130, 110, 90, 80, 60};
    private TextView text;
    private SnakeView snakeView;
    private int position = 0;
    private boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(null);
        text = (TextView) findViewById(R.id.text);
        snakeView = (SnakeView) findViewById(R.id.snake);

//        Request.Builder builder = new Request.Builder();
//        Request build = builder.url("https://www.baidu.com").get().build();
//        new OkHttpClient().newCall(build).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("TAG", Log.getStackTraceString(e));
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                boolean successful = response.isSuccessful();
//                Log.e("TAG", successful + "");
//            }
//        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    URL url = new URL("https://www.baidu.com");
                    URL url = new URL("https://raw.githubusercontent.com/chenupt/DragTopLayout/master/imgs/sample-debug-1.2.1.apk");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(5 * 1000);
                    urlConnection.setReadTimeout(5 * 1000);
                    Map<String, List<String>> requestProperties = urlConnection.getRequestProperties();
                    Set<Map.Entry<String, List<String>>> entries = requestProperties.entrySet();
                    for (Map.Entry<String, List<String>> temp : entries) {
                        String key = temp.getKey();
                        Log.e("TAG", "key:" + key);
                        List<String> value = temp.getValue();
                        for (String res : value) {
                            Log.e("TAG", "value:" + res);
                        }
                    }
                    //urlConnection.getInputStream();
                    urlConnection.connect();
                    int code = urlConnection.getResponseCode();
                    Log.e("TAG", code + "");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    @Override
    protected void onStart() {
        super.onStart();
        stop = false;
//        snakeView.start();
        generateValue();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop = true;
    }

    private void generateValue() {
        if (position < (values.length - 1)) {
            position++;
        } else {
            position = 0;
        }
        float value = values[position];
        snakeView.addValue(value);
        text.setText(Integer.toString((int) value));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!stop) {
                    generateValue();
                }
            }
        }, 500);
    }
}

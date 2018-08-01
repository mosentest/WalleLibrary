package org.walle.mo.urlconnection.donwload;

import android.content.Context;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 作者 create by moziqi on 2018/8/1
 * 邮箱 709847739@qq.com
 * 说明
 **/
public class DownloadManagerHelper {

    private static DownloadManagerHelper helper = null;

    private Context context;

    private static final int RUN_MAX = 5;//同时最多5个

    private ConcurrentLinkedQueue<DownloadRunnable> waitDownloadList;  //等待队列

    private ConcurrentLinkedQueue<DownloadRunnable> runDownloadList; //现在运行中的队列

    private Executor executor = Executors.newCachedThreadPool(); //线程池

    private Object lock = new Object(); //同步锁

    private DownloadManagerHelper(Context context) {
        this.context = context;
        waitDownloadList = new ConcurrentLinkedQueue<>();
        runDownloadList = new ConcurrentLinkedQueue<>();
    }

    public static DownloadManagerHelper getInstance(Context context) {
        if (helper == null) {
            synchronized (DownloadManagerHelper.class) {
                if (helper == null) {
                    helper = new DownloadManagerHelper(context.getApplicationContext());
                }
            }
        }
        return helper;
    }

    /**
     * 下载
     *
     * @param downloadInfo
     */
    public void download(DownloadInfo downloadInfo, DownloadListener downloadListener) {
        if (downloadInfo == null) {
            return;
        }
        synchronized (lock) {
            DownloadRunnable downloadRunnable = new DownloadRunnable(downloadInfo, downloadListener);
            int size = runDownloadList.size();
            if (size > 5) {
                waitDownloadList.add(downloadRunnable);
            } else {
                runDownloadList.add(downloadRunnable);
            }
        }
    }

    public void getNextDownload() {

    }
}

package org.walle.mo.urlconnection.donwload;

/**
 * 作者 create by moziqi on 2018/8/1
 * 邮箱 709847739@qq.com
 * 说明
 **/
public class DownloadRunnable implements Runnable {

    private DownloadInfo downloadInfo;

    private DownloadListener downloadListener;

    public DownloadRunnable(DownloadInfo downloadInfo, DownloadListener downloadListener) {
        this.downloadInfo = downloadInfo;
        this.downloadListener = downloadListener;
    }

    @Override
    public void run() {

    }
}

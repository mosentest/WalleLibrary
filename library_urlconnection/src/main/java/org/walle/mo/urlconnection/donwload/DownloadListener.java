package org.walle.mo.urlconnection.donwload;

/**
 * 作者 create by moziqi on 2018/8/1
 * 邮箱 709847739@qq.com
 * 说明
 **/
public interface DownloadListener {

    public void onStart(DownloadInfo downloadInfo);

    public void onProgress(DownloadInfo downloadInfo, int progress);

    public void onFailure(DownloadInfo downloadInfo, int code, int msg);

    public void onFinish(DownloadInfo downloadInfo);
}

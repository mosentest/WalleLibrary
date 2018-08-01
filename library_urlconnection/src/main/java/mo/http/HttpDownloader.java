package mo.http;

import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;


public class HttpDownloader extends HttpTask {

    static String TAG = HttpDownloader.class.getSimpleName();

    HttpClient client;
    Request request;
    DownloadCallback callback;
    String savePath;

    public HttpDownloader(HttpClient client, Request request, String savePath, DownloadCallback callback) {
        this.client = client;
        this.request = request;
        this.savePath = savePath;
        this.callback = callback;
    }

    @Override
    public String host() {
        return request.host();
    }

    @Override
    public final void run() {
        try {
            execute();
        } catch (Exception e) {
            if (callback != null) {
                callback.onFailure(-1, e.getMessage());
            }
        } finally {
            client.callFinished(this);
        }
    }

    void execute() {
        downloadFromBreakPoint();
    }

    void downloadFromBreakPoint() {
        //LogUtil.log(TAG, "downloadFromBreakPoint");
        boolean failed = false;
        InputStream is = null;
//        RandomAccessFile savedFile = null;
        RandomAccessFile randomAccessFile = null;
        File file = null;
        long downloadLength = 0;   //记录已经下载的文件长度
        long sum = 0;
        file = new File(savePath);
        if (file.exists()) {
            //如果文件存在的话，得到文件的大小
            downloadLength = file.length();
            sum = downloadLength;
            //LogUtil.log(TAG, "downloadFromBreakPoint  file exists, file.length=" + downloadLength);
        } else {
            File parentDir = file.getParentFile();
            if (parentDir == null) {
                //LogUtil.log(TAG, "downloadFromBreakPoint  getParentFile failed");
                failed = true;
                if (callback != null) {
                    callback.onFailure(-1, null);
                }
                return;
            }
            if (!parentDir.exists()) {
                //LogUtil.log(TAG, "downloadFromBreakPoint  mkdirs dir=" + parentDir.getAbsolutePath());
                parentDir.mkdirs();
            }
        }

        //Response response = null;
        HttpURLConnection urlConn = null;
        try {
            // 新建一个URL对象
            URL url = new URL(request.url);
            // 打开一个HttpURLConnection连接
//	        urlConn = (HttpURLConnection) url.openConnection();
            // 打开一个HttpURLConnection连接
            if ("https".equals(url.getProtocol().toLowerCase(Locale.getDefault()))) {
                HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                HttpsHelper.trustAllHosts(https);
                https.setHostnameVerifier(HttpsHelper.DO_NOT_VERIFY);
                urlConn = https;
            } else {
                urlConn = (HttpURLConnection) url.openConnection();
            }
            // 设置连接主机超时时间
            urlConn.setConnectTimeout(client.getConnectTimeout());
            //设置从主机读取数据超时
            urlConn.setReadTimeout(client.getReadTimeout());
            // 设置是否使用缓存  默认是true
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod("GET");
            //urlConn设置请求头信息
            //设置请求中的媒体类型信息。
            //urlConn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            //设置客户端与服务连接类型
            urlConn.addRequestProperty("Connection", "Keep-Alive");

            if (downloadLength > 0) {
                // 设置断点续传的开始位置
                urlConn.setRequestProperty("Range", "bytes=" + downloadLength + "-");
            }
            // 判断请求是否成功
            int responseCode = urlConn.getResponseCode();
            //LogUtil.log(TAG, "downloadFromBreakPoint  responseCode=" + responseCode);
            if (HttpURLConnection.HTTP_OK == responseCode || HttpURLConnection.HTTP_PARTIAL == responseCode) { //连接成功
                //文件总长度,如果是200，才能获取全部长度，不然只能获取剩余的长度
                int contentLength = urlConn.getContentLength();
                //LogUtil.log(TAG, "downloadFromBreakPoint  contentLength=" + contentLength);
                //这个才是总的长度
	        	contentLength += sum;

                if(downloadLength == contentLength){
                    urlConn.disconnect();
                    if(callback != null){
                        callback.onDownloaded();
                    }
                    return;
                }else if(downloadLength > contentLength){
                    //LogUtil.log(TAG, "downloadFromBreakPoint  downloadLength > contentLength  delete");
                    file.delete();
                    downloadLength = 0;
                    sum = 0;
                }

                randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(downloadLength);
                //当调用getInputStream方法时才真正将请求体数据上传至服务器
                is = urlConn.getInputStream();
//	        	FileOutputStream fos = new FileOutputStream(file, true);  


                byte[] buffer = new byte[1024 * 4];
                int length;
//                long startTime = System.currentTimeMillis();  
                while ((length = is.read(buffer)) != -1) {
//                    fos.write(buffer, 0, length); 
                    randomAccessFile.write(buffer, 0, length);
                    sum += length;
                    float percent = sum * 100.0f / contentLength;

                    int p = (int) percent / 2;
                    if (callback != null) {
                        callback.onProgress(p);
                    }
                }
            } else {
                //failed
                //LogUtil.log(TAG, "downloadFromBreakPoint  onFailure=");
                if (callback != null) {
                    callback.onFailure(responseCode, null);
                }
                failed = true;
            }

        } catch (Exception e) {
            //LogUtil.error(TAG, "downloadFromBreakPoint  Exception=", e);
            if (callback != null) {
                callback.onFailure(0, Log.getStackTraceString(e));
            }
            failed = true;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 关闭连接
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        //LogUtil.log(TAG, "downloadFromBreakPoint  failed=" + failed);
        if (failed) {
            return;
        }
        if (callback != null) {
            callback.onDownloaded();
        }
    }
}

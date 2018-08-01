package org.walle.mo.urlconnection.donwload;

import java.io.Serializable;

/**
 * 作者 create by moziqi on 2018/8/1
 * 邮箱 709847739@qq.com
 * 说明
 **/
public class DownloadInfo implements Serializable {

    public String url;

    public String md5;

    public String savePath;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownloadInfo that = (DownloadInfo) o;

        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (md5 != null ? !md5.equals(that.md5) : that.md5 != null) return false;
        return savePath != null ? savePath.equals(that.savePath) : that.savePath == null;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (md5 != null ? md5.hashCode() : 0);
        result = 31 * result + (savePath != null ? savePath.hashCode() : 0);
        return result;
    }
}

package org.wall.mo.base.interfaces;

public interface StatusView {

    /**
     * 加载view
     */
    public void statusLoadingView();

    /**
     * 没网络view
     */
    public void statusNetWorkView();

    /**
     * 错误信息view
     *
     * @param type
     * @param msg
     */
    public void statusErrorView(int type, String msg);

    /**
     * 正常view
     */
    public void statusContentView();
}
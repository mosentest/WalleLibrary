package org.wall.mo.base.interfaces;

public interface ILoadView {
    /**
     * 请求前
     *
     * @param tipMsg 加载的提示
     */
    public void onLoadStart(boolean showLoading, String tipMsg);

    /**
     * 请求结束
     */
    public void onLoadFinish(boolean showLoading);
}
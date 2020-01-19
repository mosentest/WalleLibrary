package org.wall.mo.base.interfaces;

public interface ILoadView {
    /**
     * 请求前
     *
     * @param flag   标记来源
     * @param tipMsg 加载的提示
     */
    public void onLoadStart(boolean showLoading, int flag, String tipMsg);

    /**
     * 请求成功
     *
     * @param flag
     */
    public void onLoadSuccess(boolean showLoading, int flag, Object o);


    /**
     * 请求失败
     *
     * @param flag
     */
    public void onLoadFail(boolean showLoading, int flag);

    /**
     * 请求失败 拦截自定义处理
     *
     * @param flag
     */
    public void onLoadInterceptFail(int flag, Object failObj);

    /**
     * 请求失败
     *
     * @param flag
     * @param failObj 错误对象
     */
    public void onLoadDialogFail(int flag, Object failObj);

    /**
     * 请求失败
     *
     * @param flag
     * @param failObj 错误对象
     */
    public void onLoadToastFail(int flag, Object failObj);
}
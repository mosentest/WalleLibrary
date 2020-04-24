package org.wall.mo.base.helper;

import org.wall.mo.base.callback.AbsRepositoryCallBack;
import org.wall.mo.base.interfaces.ILoadView;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-21 10:43
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class LoadHelper<T, F> {

    /**
     * 当前页码数量
     */
    private int mCurrentPage;

    /**
     * 总共数量
     */
    private int mTotalPage;


    /**
     * @param loadView
     * @param flag
     * @param refresh
     * @param refreshCallback
     * @param <T>
     * @param <F>
     * @return
     */
    public <T, F> AbsRepositoryCallBack<T, F> getRefreshRepositoryCallBack(ILoadView loadView,
                                                                           int flag,
                                                                           boolean refresh,
                                                                           RefreshCallback<T, F> refreshCallback) {
        return new AbsRepositoryCallBack<T, F>(loadView, flag) {
            @Override
            public void onSuccess(int flag, T bean) {
                if (bean instanceof LoadMoreData) {
                    mTotalPage = ((LoadMoreData) bean).totalPage;
                }
                mCurrentPage++;
                if (refreshCallback != null) {
                    refreshCallback.loadSuccess(flag, refresh, mCurrentPage == mTotalPage, bean);
                }
            }

            @Override
            public void onError(int flag, F bean) {
                if (refreshCallback != null) {
                    refreshCallback.loadError(flag, refresh, bean);
                }
            }
        };
    }

    public interface RefreshCallback<Body, Fail> {

        /**
         * 针对请求失败
         *
         * @param refresh
         */
        public void loadError(int flag, boolean refresh, Fail fail);

        /**
         * 针对请求成功
         *
         * @param refresh
         */
        public void loadSuccess(int flag, boolean refresh, boolean loadEnd, Body body);
    }


    public static class LoadMoreData {
        /**
         * 总共数量
         */
        public int totalPage;
    }
}

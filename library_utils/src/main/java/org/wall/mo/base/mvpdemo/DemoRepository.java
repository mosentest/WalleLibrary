package org.wall.mo.base.mvpdemo;

import org.wall.mo.utils.thread.ExRunnable;
import org.wall.mo.utils.thread.MainThreadExecutor;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-12-26 20:25
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class DemoRepository {


    private static DemoRepository instance;

    public static DemoRepository getInstance() {
        if (instance == null) {
            instance = new DemoRepository();
        }
        return instance;
    }

    public void loadUserInfo(final RepositoryCallBack<Object, Object> repositoryCallBack) {
        MainThreadExecutor.getExecutor().execute(new ExRunnable() {
            @Override
            public void runEx() {
                if (repositoryCallBack != null) {
                    repositoryCallBack.onSuccess(new Object());
                }
            }

            @Override
            public void exMsg(String errorMsg) throws RuntimeException {
                if (repositoryCallBack != null) {
                    repositoryCallBack.onFail(new Object());
                }
            }
        });


    }
}

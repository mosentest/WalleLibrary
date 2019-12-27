package org.wall.mo.base.mvp.demo;

import android.os.Bundle;

import java.lang.ref.WeakReference;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-08-18 15:34
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class DemoPresenter extends DemoContract.Presenter {

    @Override
    public void postMsg(final int flag) {
        DemoRepository.getInstance().loadUserInfo(new RepositoryCallBack<Object, Object>(getView(), flag) {

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onPause() {

    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onDestroy() {

    }
}

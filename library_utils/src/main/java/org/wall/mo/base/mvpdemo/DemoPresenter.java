package org.wall.mo.base.mvpdemo;

import android.os.Bundle;

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
            @Override
            public void onSuccess(Object bean) {
                super.onSuccess(bean);
            }

            @Override
            public void onFail(Object bean) {
                super.onFail(bean);
            }

            @Override
            public boolean interceptError() {
                return super.interceptError();
            }

            @Override
            public boolean showError() {
                return super.showError();
            }

            @Override
            public boolean toast() {
                return super.toast();
            }
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

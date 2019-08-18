package org.wall.mo.base.mvp.demo;

import org.wall.mo.base.mvp.BaseContract;
import org.wall.mo.base.mvp.BaseContract.BasePresenter;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-08-18 15:32
 * Description: ${DESCRIPTION}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class DemoContract {

    public static abstract class Presenter extends BasePresenter<View> {

        public abstract void postMsg();
    }

    public interface View extends BaseContract.BaseView {

    }
}

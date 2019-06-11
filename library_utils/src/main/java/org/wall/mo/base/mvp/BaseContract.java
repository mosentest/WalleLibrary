package org.wall.mo.base.mvp;

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2019/6/10-15:44
 * desc   :
 * https://github.com/googlesamples/android-architecture
 * https://github.com/googlesamples/android-architecture/blob/todo-mvp/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/BasePresenter.java
 * version: 1.0
 */
public interface BaseContract {
    public interface BasePresenter {
        /**
         * 放在onResume方法里面
         */
        void start();
    }

    public interface BaseView {

    }
}

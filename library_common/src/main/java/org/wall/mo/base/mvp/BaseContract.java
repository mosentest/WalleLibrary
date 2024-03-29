package org.wall.mo.base.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;

import org.wall.mo.base.interfaces.ILoadView;
import org.wall.mo.base.interfaces.IStatusView;
import org.wall.mo.utils.ILifecycleObserver;

import java.lang.ref.WeakReference;

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

    public abstract class BasePresenter<View extends BaseView> implements ILifecycleObserver {

        /**
         * 弱引用持有view
         */
        protected WeakReference<View> mViewReference;

        protected abstract void onCreate(@Nullable Bundle savedInstanceState);


        protected abstract void onRestoreInstanceState(@Nullable Bundle savedInstanceState);

        protected abstract void onStart();

        protected abstract void onPause();

        protected abstract void onStop();

        protected abstract void onDestroy();

        /**
         * 子类获取BaseView对象
         * 注意：获取后应该判断是否为空，并且不要使用缓存BaseView实例变量，不然会造成内存泄漏，
         * 应直接调用该方法获取
         *
         * @return 获取BaseView对象
         */
        @Nullable
        protected View getView() {
            return mViewReference == null ? null : mViewReference.get();
        }

        /**
         * 网络请求回来判断这个是否为空
         * <p>
         * 因为不感知生命周期，所以需要判断当前是否绑定
         *
         * @return
         */
        protected boolean isAttach() {
            return getView() != null;
        }

        /**
         * 关联View对象
         *
         * @param view View层实例
         */
        protected void attachView(View view) {
            if (mViewReference == null) {
                mViewReference = new WeakReference<>(view);
            }
        }

        /**
         * 取消关联View
         */
        protected void detachView() {
            if (mViewReference != null) {
                mViewReference.clear();
                mViewReference = null;
            }
        }

        @Override
        public void onLifeClear() {
            ILifecycleObserver.InnerClass.clear(this);
        }
    }

    public interface BaseView extends ILoadView, IStatusView {
    }


}

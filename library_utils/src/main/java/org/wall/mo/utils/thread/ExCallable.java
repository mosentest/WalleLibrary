package org.wall.mo.utils.thread;

import org.wall.mo.utils.log.WLog;

import java.util.concurrent.Callable;

public abstract class ExCallable<V> implements Callable {

    private final static String TAG = "ExRunnable";

    @Override
    public V call() throws Exception {
        V v = null;
        try {
            v = callEx();
        } catch (Exception e) {
            String stackTraceString = WLog.getStackTraceString(e);
            //这里的异常信息，可以统一往服务上报，做代码优化
            exMsg(stackTraceString);
            WLog.e(TAG, stackTraceString);
        }
        return v;
    }

    public abstract V callEx();

    public abstract void exMsg(String errorMsg) throws RuntimeException;
}

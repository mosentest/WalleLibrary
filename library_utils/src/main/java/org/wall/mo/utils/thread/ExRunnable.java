package org.wall.mo.utils.thread;

import org.wall.mo.utils.log.WLog;

/**
 * runnable会在异常，所以为了减少加班，可以在这里统一异常处理
 */
public abstract class ExRunnable implements Runnable {

    private final static String TAG = "ExRunnable";

    @Override
    public void run() {
        try {
            runEx();
        } catch (Exception e) {
            String stackTraceString = WLog.getStackTraceString(e);
            //这里的异常信息，可以统一往服务上报，做代码优化
            exMsg(stackTraceString);
            WLog.e(TAG, stackTraceString);
        }
    }

    public abstract void runEx();

    public abstract void exMsg(String errorMsg) throws RuntimeException;
}

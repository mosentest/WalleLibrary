package org.wall.mo.utils.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Future;

public class MainThreadExecutor implements IExecutor {

    private Handler handler;

    private volatile static MainThreadExecutor executor;

    public static MainThreadExecutor getExecutor() {
        if (executor == null) {
            synchronized (MainThreadExecutor.class) {
                if (executor == null) {
                    executor = new MainThreadExecutor();
                }
            }
        }
        return executor;
    }

    private MainThreadExecutor() {
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(ExRunnable runnable) {
        handler.post(runnable);
    }

    @Override
    public <T> Future<T> submit(ExCallable<T> exCallable) {
        return null;
    }
}

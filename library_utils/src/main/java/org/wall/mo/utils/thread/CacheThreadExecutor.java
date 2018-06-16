package org.wall.mo.utils.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 可以并发请求的线程池
 */
public class CacheThreadExecutor implements IExecutor {


    private static CacheThreadExecutor executor;

    private ExecutorService cacheExecutor;

    public static CacheThreadExecutor getExecutor() {
        if (executor == null) {
            synchronized (CacheThreadExecutor.class) {
                if (executor == null) {
                    executor = new CacheThreadExecutor();
                }
            }
        }
        return executor;
    }

    private CacheThreadExecutor() {
        cacheExecutor = Executors.newCachedThreadPool();
    }


    @Override
    public void execute(ExRunnable runnable) {
        cacheExecutor.execute(runnable);
    }

    @Override
    public <T> Future<T> submit(ExCallable<T> exCallable) {
        return cacheExecutor.submit(exCallable);
    }
}

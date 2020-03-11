package org.wall.mo.utils.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 单线程执行数据库操作
 */
public class SQLiteThreadExecutor implements IExecutor {

    private volatile static SQLiteThreadExecutor executor;

    private ExecutorService singleExecutor;

    public static SQLiteThreadExecutor getExecutor() {
        if (executor == null) {
            synchronized (SQLiteThreadExecutor.class) {
                if (executor == null) {
                    executor = new SQLiteThreadExecutor();
                }
            }
        }
        return executor;
    }

    private SQLiteThreadExecutor() {
        singleExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(ExRunnable runnable) {
        singleExecutor.execute(runnable);

    }

    @Override
    public <T> Future<T> submit(ExCallable<T> exCallable) {
        return singleExecutor.submit(exCallable);
    }
}

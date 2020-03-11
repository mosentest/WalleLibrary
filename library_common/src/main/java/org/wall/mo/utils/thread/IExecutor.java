package org.wall.mo.utils.thread;

import java.util.concurrent.Future;

public interface IExecutor {

    public void execute(ExRunnable runnable);

    public <T> Future<T> submit(ExCallable<T> exCallable);
}

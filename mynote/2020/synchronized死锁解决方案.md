java中，解决死锁一般有如下方法：

1)尽量使用tryLock(long timeout, TimeUnit unit)的方法(ReentrantLock、ReentrantReadWriteLock)，设置超时时间，超时可以退出防止死锁。
2)尽量使用java.util.concurrent(jdk 1.5以上)包的并发类代替手写控制并发，比较常用的是ConcurrentHashMap、ConcurrentLinkedQueue、AtomicBoolean等等，实际应用中java.util.concurrent.atomic十分有用，简单方便且效率比使用Lock更高
3)尽量降低锁的使用粒度，尽量不要几个功能用同一把锁
4)尽量减少同步的代码块


由于我是长期后台挂的任务，只能试试tryLock
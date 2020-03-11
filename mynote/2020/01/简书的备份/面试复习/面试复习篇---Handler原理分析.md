

Handler发消息，最终调用enqueueMessage方法调用MessageQueued对象，MessageQueue的enqueueMessage方法的是做对象锁的同步操作，next方法也是做了对象锁的同步操作，MessageQueue内部有个mMessages（Message）对象，这2个方法都是操作这个msg对象，所以线程是安全，enqueueMessage是负责把消息存在message，next是负责把消息返回给Looper

#enqueueMessage方法的部分代码
``` 
      synchronized (this) {
            if (mQuitting) {
                IllegalStateException e = new IllegalStateException(
                        msg.target + " sending message to a Handler on a dead thread");
                Log.w("MessageQueue", e.getMessage(), e);
                msg.recycle();
                return false;
            }

            msg.markInUse();
            msg.when = when;
            Message p = mMessages;
            boolean needWake;
            if (p == null || when == 0 || when < p.when) {
                // New head, wake up the event queue if blocked.
                msg.next = p;
                mMessages = msg;
                needWake = mBlocked;
            } else {
                // Inserted within the middle of the queue.  Usually we don't have to wake
                // up the event queue unless there is a barrier at the head of the queue
                // and the message is the earliest asynchronous message in the queue.
                needWake = mBlocked && p.target == null && msg.isAsynchronous();
                Message prev;
                for (;;) {
                    prev = p;
                    p = p.next;
                    if (p == null || when < p.when) {
                        break;
                    }
                    if (needWake && p.isAsynchronous()) {
                        needWake = false;
                    }
                }
                msg.next = p; // invariant: p == prev.next
                prev.next = msg;
            }
```

#next方法的部分代码
```
next方法：
        synchronized (this) {
                // Try to retrieve the next message.  Return if found.
                final long now = SystemClock.uptimeMillis();
                Message prevMsg = null;
                Message msg = mMessages;
                if (msg != null && msg.target == null) {
                    // Stalled by a barrier.  Find the next asynchronous message in the queue.
                    do {
                        prevMsg = msg;
                        msg = msg.next;
                    } while (msg != null && !msg.isAsynchronous());
                }
                if (msg != null) {
                    if (now < msg.when) {
                        // Next message is not ready.  Set a timeout to wake up when it is ready.
                        nextPollTimeoutMillis = (int) Math.min(msg.when - now, Integer.MAX_VALUE);
                    } else {
                        // Got a message.
                        mBlocked = false;
                        if (prevMsg != null) {
                            prevMsg.next = msg.next;
                        } else {
                            mMessages = msg.next;
                        }
                        msg.next = null;
                        if (false) Log.v("MessageQueue", "Returning message: " + msg);
                        return msg;
                    }
                } else {
                    // No more messages.
                    nextPollTimeoutMillis = -1;
                }
```
在Looper的loop方法通过for循环调用MessageQueue的next，获取队列的消息，最终会调用msg.target.dispatchMessage(msg)
```
    public static void loop() {
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        final MessageQueue queue = me.mQueue;

        // Make sure the identity of this thread is that of the local process,
        // and keep track of what that identity token actually is.
        Binder.clearCallingIdentity();
        final long ident = Binder.clearCallingIdentity();

        for (;;) {
            Message msg = queue.next(); // might block
            if (msg == null) {
                // No message indicates that the message queue is quitting.
                return;
            }

            // This must be in a local variable, in case a UI event sets the logger
            Printer logging = me.mLogging;
            if (logging != null) {
                logging.println(">>>>> Dispatching to " + msg.target + " " +
                        msg.callback + ": " + msg.what);
            }

            msg.target.dispatchMessage(msg);

            if (logging != null) {
                logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);
            }

            // Make sure that during the course of dispatching the
            // identity of the thread wasn't corrupted.
            final long newIdent = Binder.clearCallingIdentity();
            if (ident != newIdent) {
                Log.wtf(TAG, "Thread identity changed from 0x"
                        + Long.toHexString(ident) + " to 0x"
                        + Long.toHexString(newIdent) + " while dispatching to "
                        + msg.target.getClass().getName() + " "
                        + msg.callback + " what=" + msg.what);
            }

            msg.recycleUnchecked();
        }
    }

```
handler的dispatchMessage会把msg传给handleCallback回调
```
    public void dispatchMessage(Message msg) {
        if (msg.callback != null) {
            handleCallback(msg);
        } else {
            if (mCallback != null) {
                if (mCallback.handleMessage(msg)) {
                    return;
                }
            }
            handleMessage(msg);
        }
    }
```
看完源码后，总结下
从handler中获取一个消息对象，把数据封装到消息对象中，通过handler的send…方法把消息push到MessageQueue队列中。 
Looper对象会轮询MessageQueue队列，把消息对象取出。 
通过dispatchMessage分发给Handler，再回调用Handler实现的handleMessage方法处理消息
#Handler的实现中适及以下对象： 
1、Handler本身：负责消息的发送和处理 
2、Message：消息对象 
3、MessageQueue：消息队列（用于存放消息对象的数据结构） 
4、Looper：消息队列的处理者（用于轮询消息队列的消息对象，取出后回调handler的dispatchMessage进行消息的分发，dispatchMessage方法会回调handleMessage方法把消息传入，由Handler的实现类来处理）

Message对象的内部实现是链表，最大长度是50，用于缓存消息对象，达到重复利用消息对象的目的，以减少消息对象的创建，所以通常我们要使用obtainMessage方法来获取消息对象
##安全：
Handler的消息处理机制是线程安全的
##关系:
创建Handler时会创建Looper，Looper对象的创建又创建了MessageQueue


#Looper实现原理分析
内部用ThreadLocal维持不同线程的Looper对象
```
static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
```
#ThreadLocal原理分析
ThreadLocal类用来提供线程内部的局部变量。这些变量在多线程环境下访问(通过get或set方法访问)时能保证各个线程里的变量相对独立于其他线程内的变量，ThreadLocal实例通常来说都是private static类型。 
总结：ThreadLocal不是为了解决多线程访问共享变量，而是为每个线程创建一个单独的变量副本，提供了保持对象的方法和避免参数传递的复杂性
ThreadLocal类中有一个静态内部类ThreadLocalMap(其类似于Map)，用键值对的形式存储每一个线程的变量副本，ThreadLocalMap中元素的key为当前ThreadLocal对象，而value对应线程的变量副本，每个线程可能存在多个ThreadLocal

#引用链接
https://blog.csdn.net/blackzhangwei/article/details/51945516
https://blog.csdn.net/lhqj1992/article/details/52451136 

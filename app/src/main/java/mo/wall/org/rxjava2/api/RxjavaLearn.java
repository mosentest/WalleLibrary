package mo.wall.org.rxjava2.api;

import org.wall.mo.dropdownmenu.TabBean;
import org.wall.mo.utils.log.WLog;
import org.wall.mo.utils.thread.CacheThreadExecutor;
import org.wall.mo.utils.thread.ExRunnable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-11-18 11:06
 * Description:
 * History: 参考学习地址：https://www.jianshu.com/p/e1c48a00951a
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class RxjavaLearn {

    final static String TAG = RxjavaLearn.class.getSimpleName();

    static {
        /**
         * 第二个异常就会走到这里
         */
        RxJavaPlugins.setErrorHandler(throwable -> {
            WLog.i(TAG, "RxJavaPlugins:" + throwable);
        });
    }

    /**
     * 退出app的时候清除
     */
    public static CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public static <T> ObservableTransformer<T, T> io2MainObservable() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return io2MainObservable();
    }

    public static void testCreate() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    emitter.onNext("create");
                } catch (Exception e) {
                    emitter.onError(e);
                } finally {
                    emitter.onComplete();
                }
            }
        })
                .compose(applySchedulers())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String o) {
                        WLog.i(TAG, "onNext:" + o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        WLog.i(TAG, "onError:" + e);
                    }

                    @Override
                    public void onComplete() {
                        WLog.i(TAG, "onComplete");
                    }
                });

    }

    public static void testNextNull() {
        Observable<Object> objectObservable = Observable.create(emitter -> {
            try {
                /**
                 * 这是异常1
                 */
                emitter.onNext(null);
                /**
                 * 这是异常2
                 */
                throw new RuntimeException("testError");
                /**
                 * 触发
                 * The exception could not be delivered to the consumer because it has already canceled/disposed
                 * the flow or the exception has nowhere to go to begin with.
                 * Further reading: https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#error-handling
                 * | java.lang.RuntimeException: testError
                 */
            } catch (Exception e) {
                emitter.onError(e);
            } finally {
                emitter.onComplete();
            }
        });
        Disposable subscribe = objectObservable.subscribe(o -> {
            //onNext
            WLog.i(TAG, "onNext:" + o);
            //这里接收数据项
        }, throwable -> {
            //onError
            WLog.i(TAG, "onError:" + throwable);
        }, () -> {
            //onComplete
            WLog.i(TAG, "onComplete");
        });
        mCompositeDisposable.add(subscribe);
    }

    public static void testError() {
        Observable<Object> objectObservable = Observable.create(emitter -> {
            try {
                throw new RuntimeException("testError");
            } catch (Exception e) {
                emitter.onError(e);
            } finally {
                emitter.onComplete();
            }
        });
        Disposable subscribe = objectObservable.subscribe(o -> {
            //onNext
            WLog.i(TAG, "onNext:" + o);
            //这里接收数据项
        }, throwable -> {
            //onError
            WLog.i(TAG, "onError:" + throwable);
        }, () -> {
            //onComplete
            WLog.i(TAG, "onComplete");
        });
        mCompositeDisposable.add(subscribe);
    }


    public static void testMap() {
        Disposable subscribe = Observable.just(1, 2, 3, 4, 5, 6, 7)
                .compose(applySchedulers())
                .map(o -> {
                    if (o > 3) {
                        return "" + o;
                    }
                    return "error";
                }).subscribe(s -> {
                    WLog.i(TAG, "onNext:" + s);
                }, throwable -> {
                    WLog.i(TAG, "onError:" + throwable);
                }, () -> {
                    WLog.i(TAG, "onComplete");
                });
        mCompositeDisposable.add(subscribe);
    }


    /**
     * 另外一个过滤操作符 distinct 这是过滤重复数据，就好像数据库的sql
     */
    public static void testFilter() {
        Disposable subscribe = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .compose(applySchedulers())
                .filter(integer -> integer > 6).subscribe(integer -> {
                            WLog.i(TAG, "onNext:" + integer);
                        }

                );
        mCompositeDisposable.add(subscribe);
    }


    public static void testConcat() {
        Observable<Integer> just1 = Observable.create(emitter -> {
            try {
                emitter.onNext(7);
                Thread.sleep(1000);
                emitter.onNext(8);
                Thread.sleep(1000);
                emitter.onNext(9);
            } catch (Exception e) {
                emitter.onError(e);
            } finally {
                emitter.onComplete();
            }
        });
        Observable<Integer> just2 = Observable.just(2, 4);
        Observable.concat(just1, just2).compose(applySchedulers()).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                WLog.i(TAG, "onSubscribe");
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Integer integer) {
                WLog.i(TAG, "onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                WLog.i(TAG, "onError:" + e);
            }

            @Override
            public void onComplete() {
                WLog.i(TAG, "onComplete");
            }
        });
    }

    public static void testFlatMap() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext("1");
            emitter.onNext("2");
            emitter.onNext("3");
        }).compose(applySchedulers()).flatMap((Function<String, ObservableSource<Integer>>) s -> {
//            if ("2".equals(s)) {
//                return Observable.error(new Exception("String is 2"));
//            }
            return new Observable<Integer>() {
                @Override
                protected void subscribeActual(Observer<? super Integer> observer) {
                    observer.onNext(Integer.parseInt(s));
                }
            };
        }).compose(applySchedulers()).flatMap((Function<Integer, ObservableSource<String>>) integer -> {
//            if (integer == 1) {
//                return Observable.error(new Exception("integer is 1"));
//            }
            return Observable.create(emitter -> {
                emitter.onNext(String.valueOf(integer));
            });
        }).compose(applySchedulers()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(String s) {
                WLog.i(TAG, Thread.currentThread().getName() + ".onNext:" + s);
            }

            @Override
            public void onError(Throwable e) {
                WLog.i(TAG, Thread.currentThread().getName() + ".onError:" + e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 把 第一个和第二个合并数据起来处理
     */
    public static void testZip() {
        Observable<Integer> just1 = Observable.just(1, 3);
        Observable<Integer> just2 = Observable.just(2, 4);
        Observable.zip(just1, just2, new BiFunction<Integer, Integer, Object>() {
            @Override
            public Object apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).compose(applySchedulers()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Object o) {
                WLog.i(TAG, Thread.currentThread().getName() + ".onNext:" + o);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /***
     *  Merge 可能会让合并的Observables发射的数据交错
     */
    public static void testMerge() {
        Observable<String> obs1 = Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext("1");
            Thread.sleep(1000);
            emitter.onNext("2");
            Thread.sleep(1000);
            emitter.onNext("3");
        }).compose(applySchedulers());
        Observable<String> obs2 = Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext("4");
            Thread.sleep(1000);
            emitter.onNext("5");
            Thread.sleep(1000);
            emitter.onNext("6");
        })
//                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
                .compose(applySchedulers());
        Observable.merge(obs1, obs2).compose(applySchedulers()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(String s) {
                WLog.i(TAG, Thread.currentThread().getName() + ".onNext:" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * https://www.jianshu.com/p/2816db14fa3e
     */
    public static void testRetryWhen() {
        Observable.create(emitter -> {
            try {
                //emitter.onNext(1);
                emitter.onNext(null);
            } catch (Exception e) {
                emitter.onError(e);
            } finally {
                emitter.onComplete();
            }
        }).compose(applySchedulers()).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {

            private int maxRetries = 2;
            private int retryDelayMillis = 1000;
            private int retryCount;

            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                WLog.i(TAG, "retryWhen+" + Thread.currentThread().getName());
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        if (++retryCount <= maxRetries) {
                            WLog.i(TAG, "我在重试:" + retryCount + "---" + Thread.currentThread().getName());
                            return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                        }
                        return Observable.error(throwable);
                    }
                });
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Object o) {
                WLog.i(TAG, Thread.currentThread().getName() + ".onNext:" + o);
            }

            @Override
            public void onError(Throwable e) {
                WLog.i(TAG, Thread.currentThread().getName() + ".onError:" + e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    final static Lock LOCK = new ReentrantLock();

    public static void testLock() {
        CacheThreadExecutor.getExecutor().execute(new ExRunnable() {
            @Override
            public void runEx() {
                lockMain(".in lock.1", 10000, ".in lock.1 after", ".no lock.1");
            }

            @Override
            public void exMsg(String errorMsg) throws RuntimeException {

            }
        });
        CacheThreadExecutor.getExecutor().execute(new ExRunnable() {
            @Override
            public void runEx() {
                lockMain(".in lock.2", 3000, ".in lock.2 after", ".no lock.2");
            }

            @Override
            public void exMsg(String errorMsg) throws RuntimeException {

            }
        });
    }

    /**
     * 没锁就不执行释放内存
     *
     * @param s
     * @param i
     * @param s2
     * @param s3
     */
    private static void lockMain(String s, int i, String s2, String s3) {
        if (LOCK.tryLock()) {
            WLog.i(TAG, Thread.currentThread().getName() + s);
            try {
                Thread.sleep(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WLog.i(TAG, Thread.currentThread().getName() + s2);
        } else {
            WLog.i(TAG, Thread.currentThread().getName() + s3);
            return;
        }
        LOCK.unlock();
    }
}



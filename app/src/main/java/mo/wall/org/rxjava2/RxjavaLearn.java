package mo.wall.org.rxjava2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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


    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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

    public void test1() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    emitter.onNext(null);
                } catch (Exception e) {
                    emitter.onError(e);
                } finally {
                    emitter.onComplete();
                }
            }
        })
                .compose(applySchedulers())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void test2() {
        Observable<Object> objectObservable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    emitter.onNext(null);
                } catch (Exception e) {
                    emitter.onError(e);
                } finally {
                    emitter.onComplete();
                }
            }
        });
        Disposable subscribe = objectObservable.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                try {
                    //onNext
                    //这里接收数据项
                } catch (Exception e) {

                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //onError
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                //onComplete
            }
        });
        compositeDisposable.add(subscribe);
    }


    public void test3Map() {
        Disposable subscribe = Observable.just(1, 2, 3, 4, 5, 6, 7)
                .compose(applySchedulers())
                .map(new Function<Object, String>() {
                    @Override
                    public String apply(Object o) throws Exception {
                        return "" + o;
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });
    }
}



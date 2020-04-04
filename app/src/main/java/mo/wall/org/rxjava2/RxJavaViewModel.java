package mo.wall.org.rxjava2;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import mo.wall.org.rxjava2.api.RxjavaLearn;
import mo.wall.org.rxjava2.data.RxJavaAcceptPar;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/4 2:07 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class RxJavaViewModel extends ViewModel {

    public MutableLiveData<List<RxJavaAcceptPar>> mListLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> mStatus = new MutableLiveData<>();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void loadData(boolean isFirst) {
        if (isFirst) {
            mStatus.setValue(1);
        }
        Observable.create((ObservableOnSubscribe<List<RxJavaAcceptPar>>) emitter -> {
            try {
                Thread.sleep(500);
                List<RxJavaAcceptPar> pars = new ArrayList<>();
                Method[] methods = RxjavaLearn.class.getDeclaredMethods();
                for (Method method : methods) {
                    String name = method.getName();
                    if (name.startsWith("test")) {
                        pars.add(new RxJavaAcceptPar.Builder().name(name).build());
                    }
                }
                emitter.onNext(pars);
            } catch (Exception e) {
                emitter.onError(e);
            } finally {
                emitter.onComplete();
            }
        }).

                compose(RxjavaLearn.applySchedulers())
                .

                        subscribe(new Observer<List<RxJavaAcceptPar>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                mCompositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(List<RxJavaAcceptPar> rxJavaAcceptPars) {
                                mListLiveData.setValue(rxJavaAcceptPars);
                                mStatus.postValue(2);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (isFirst) {
                                    mStatus.setValue(3);
                                }
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
        //判断是否解除订阅
        boolean disposed = mCompositeDisposable.isDisposed();
        if (!disposed) {
            //消除订阅
            mCompositeDisposable.clear();
            //解除订阅
            mCompositeDisposable.dispose();
        }
    }
}

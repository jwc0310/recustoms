package com.andy.recustomviews.rxjava2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Rxjava_retry {

    public static void test() {

    }

    /**
     * 1.触发retry最重要的条件是看时候会发射throwable(与repeat对比)
     * 2.没有任何参数指定的时候,会无限次重试{{demo0}}
     * 3.指定重试的次数{{demo1}}
     * 4.指定终止条件{{demo2}}
     * 5.指定重试次数以及终止条件,则两条有一条满足即终止,其余均与3,4同
     *
     */
    public static void test_retry() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

            }
        }).retry()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}

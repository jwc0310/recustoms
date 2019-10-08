package com.andy.recustomviews.rxjava2;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 创建操作符
 */
public class TestCreate {

    public static void test() {
        createTest();
    }

    //just
    //创建一个被观察者，可以发送多个事件，但不能超过10个
    //fromArray 与 just相似 可以传入多个事件， 也可以传入一个数组
    private static void justTest() {
        Observable observable = Observable.just("1", "2", "3");
        Observer observer = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }

    //create()
    //创建一个被观察者  发送一个事件
    //构造方法
    //public static <T> Observable<T> create(ObservableOnSubscribe<T> source)
    private static void createTest() {
        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onComplete();
            }
        });

        Observer observer = new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.e("----", "-----onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e("----", "-----onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("----", "-----onError");
            }

            @Override
            public void onComplete() {
                Log.e("----", "-----onComplete");
            }

        };

        observable.subscribe(observer);
    }

}

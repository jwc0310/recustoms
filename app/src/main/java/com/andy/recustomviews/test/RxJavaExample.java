package com.andy.recustomviews.test;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/20.
 */
public class RxJavaExample {

    private static Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>(){

        @Override
        public void call(Subscriber<? super String> subscriber) {
            //发送一个hello world事件
            subscriber.onNext("Hello world!");
            subscriber.onNext("Hello world2!");
            //事件发送完成
            subscriber.onCompleted();
        }
    });

    private static Observable<String> observable2 = Observable.just("Hello, ", "hello,");

    private static Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            Log.e("Andy", "complete");
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            Log.e("Andy", "onNext");
        }
    };

    public static void bind(){
        observable.subscribe(subscriber);
        observable2.subscribe(subscriber);
    }
}

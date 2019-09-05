package com.othershe.mdview.net.rxjava;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

public class Rxjava2Test {

    public static final String TAG = "Rxjava2Test";

    public static void Rxjava2TestMain() {
        TestCreate.test();
        TestMap.test();
    }

    //Observable ( 被观察者 ) / Observer ( 观察者 )
    //Flowable （被观察者）/ Subscriber （观察者）  支持背压 控制时间速度

    //Rxjava 三步
    //1, 初始化Observable
    //2, 初始化observer
    //3, 建立订阅关系

    //线程切换
    //Schedulers.io() io操作的线程, 通常用于网络,读写文件等io密集
    //Schedulers.computation()  cpu密集  大量计算
    //Schedulers.newThread()  常规新线程
    //AndroidSchedulers.mainThread(), 代表Android的主线程

    //采用 interval 操作符实现心跳间隔任务
    //善用 zip 操作符，实现多个接口数据共同更新 UI
    //flatMap 实现多个网络请求依次依赖
    //采用 concat 操作符先读取缓存再通过网络请求获取数据
    //map 操作符可以将一个 Observable 对象通过某种关系转换为另一个Observable 对象。
    public static void TestGetDataFromServer() {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> emitter) throws Exception {

            }
        }).map(new Function<Response, Object>() {
            @Override
            public Object apply(Response response) throws Exception {
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public static void test() {
        Observable.create(new ObservableOnSubscribe<Integer>() {   //第一步: 初始化Observable

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                Log.e(TAG, "Observable emit 1" + "\n");
                emitter.onNext(1);
                Log.e(TAG, "Observable emit 2" + "\n");
                emitter.onNext(2);
                Log.e(TAG, "Observable emit 3" + "\n");
                emitter.onNext(3);
                emitter.onComplete();
                Log.e(TAG, "Observable emit 4" + "\n" );
                emitter.onNext(4);
                Log.e(TAG, "Observable thread is : " + Thread.currentThread().getName());

            }
        }).subscribeOn(Schedulers.newThread())  //
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "After observeOn(mainThread)，Current thread is " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "After observeOn(io)，Current thread is " + Thread.currentThread().getName());
//                    }
//                })
                .subscribe(new Observer<Integer>(){   //第三部: 订阅

                    //第二部: 初始化Observer
                    private int i;
                    private Disposable mDisposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe" + "\n" );
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext " + integer.toString() + "\n" );
                        i++;
                        if (i == 2) {
                            // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，
                            // 让Observer观察者不再接收上游事件
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError : value : " + e.getMessage() + "\n" );
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete" + "\n" );
                    }
        });
    }

}

package com.andy.recustomviews.rxjava2;

import android.util.Log;

import com.andy.recustomviews.bean.Book;
import com.andy.recustomviews.bean.People;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


/**
 * 转换操作符
 */
public class TestMap {

    public static void test() {
        testMap();
        testFlatMap();
    }

    //map 将被观察着发送的数据进行类型转换
    private static void testMap() {
        Observable.fromArray(new String[]{"1", "12", "123", "1234"})
                .map(new Function<String, Integer> () {
                    @Override
                    public Integer apply(String o) throws Exception {
                        return Integer.valueOf(o);
                    }
                }).subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("testMap", "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e("testMap", "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("testMap", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("testMap", "onComplete");
                    }
                });
    }

    /**
     * flatMap()/concatMap()   前者无序, 后者有序
     *
     * 该方法可以将事件序列中的元素进行加工，返回一个新的被观察者
     * 1，将传入的事件对象装换成一个Observable对象
     * 2，这时不会直接发送这个Observable，而是将这个Observable激活让他
     *      自己开始发送事件
     * 3，每一个创建出来的Observable发送的事件，都被汇入同一个Observable，
     *      这个Observable负责将这些事件统一交给Subscriber的回调方法
     */
    private static void testFlatMap() {
        //初始化数据
        ArrayList<Book> books = new ArrayList<>(3);
        books.add(new Book("开发艺术探索",new ArrayList<String>(){{add("2018.01.01 第一版");add("2018.01.02 第二版");}}));
        books.add(new Book("第一行代码",new ArrayList<String>(){{add("2019.01.01 第一版");add("2019.01.02 第二版");}}));

        ArrayList<People> libraries = new ArrayList<>(2);
        libraries.add(new People("Tom",books));
        libraries.add(new People("Jerry",books));

        //打印每个人收藏的书籍相关信息 fromInterable + map
        Observable.fromIterable(libraries).map(new Function<People, ArrayList<Book>>() {
            @Override
            public ArrayList<Book> apply(People people) throws Exception {
                return people.getSaveBooks();
            }
        }).subscribe(new Observer<ArrayList<Book>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("testFlatMap", "onSubscribe");
            }

            @Override
            public void onNext(ArrayList<Book> books) {
                for (Book book : books) {
                    Log.e("testFlatMap", book.getName() +", " +book.getHistroy().toString());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("testFlatMap", "onError");
            }

            @Override
            public void onComplete() {
                Log.e("testFlatMap", "onComplete");
            }
        });


        //进一步要求， 只打印每个人收藏的每本书的每一次修订记录
        Observable.fromIterable(libraries).flatMap(new Function<People, ObservableSource<Book>>() {
            @Override
            public ObservableSource<Book> apply(People people) throws Exception {
                //获取当前传递进来的用户的每一本收藏
                return Observable.fromIterable(people.getSaveBooks());
            }
        }).flatMap(new Function<Book, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Book book) throws Exception {
                //获取每一本的修订记录
                return Observable.fromIterable(book.getHistroy());
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("testFlatMap", "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.e("testFlatMap", s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("testFlatMap", "onError");
            }

            @Override
            public void onComplete() {
                Log.e("testFlatMap", "onComplete");
            }
        });
    }


}

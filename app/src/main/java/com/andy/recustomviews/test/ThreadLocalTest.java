package com.andy.recustomviews.test;

import android.util.Log;

/**
 * ThreadLocal class
 * Created by Andy on 2016/12/16.
 */
public class ThreadLocalTest {

    private String TAG = ThreadLocalTest.class.getSimpleName();

    ThreadLocal<String> mThreadLocal = new ThreadLocal<>();
    String hot1, hot2, hot3;

    public void testThreadLocal(){
        mThreadLocal.set("东京热");
        new HotThread1().start();
        new HotThread2().start();
        hot3=mThreadLocal.get();
        try{
            Thread.sleep(1000*4);
            Log.e(TAG,"HotThread1获取到的变量值: "+hot1);
            Log.e(TAG,"HotThread2获取到的变量值: "+hot2);
            Log.e(TAG,"MainThread获取到的变量值: "+hot3);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class HotThread1  extends Thread{
        @Override
        public void run() {
            super.run();
            mThreadLocal.set("北京热");
            hot1=mThreadLocal.get();
        }
    }

    private class HotThread2  extends Thread{
        @Override
        public void run() {
            super.run();
            mThreadLocal.set("南京热");
            hot2=mThreadLocal.get();
        }
    }

}

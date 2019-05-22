package com.andy.recustomviews.java;

/**
 * Created by Administrator on 2017/6/17.
 */
public class SyncThread implements Runnable {

    public static int getCount() {
        return count;
    }

    private static int count;

    public SyncThread(){
        count = 0;
    }

    @Override
    public void run() {
        synchronized (this){
            for (int i = 0; i < 5; i++){
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package com.andy.recustomviews.java;

/**
 * Created by Administrator on 2017/4/8.
 */
public class MultiThread {

    public static int count = 0;
    public static void inc() {
        try{
            Thread.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        count++;
    }

    public static void main(String[] args){
        for (int i = 0; i < 1000; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MultiThread.inc();
                }
            }).start();
        }

        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + MultiThread.count);

    }

}

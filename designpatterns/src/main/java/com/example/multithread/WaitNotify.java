package com.example.multithread;

public class WaitNotify {


    public static void main(String[] args) {

        try {

            Object lock = new Object();
            ProductThread consumer = new ProductThread(lock);
            consumer.start();
            Thread.sleep(3000);

            ConsumeThread productor = new ConsumeThread(lock);
            productor.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ConsumeThread extends Thread {

    private Object lock;

    public ConsumeThread(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        //when sync block exec over， wait will exec
        synchronized (lock) {
            System.out.println("start notify time = " + System.currentTimeMillis());
            lock.notify();
            System.out.println("end notify time = " + System.currentTimeMillis());
        }
    }


}

class ProductThread extends Thread {

    private Object lock;

    public ProductThread(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                System.out.println("start wait time = " + System.currentTimeMillis());
                lock.wait();
                System.out.println("end wait time = " + System.currentTimeMillis());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

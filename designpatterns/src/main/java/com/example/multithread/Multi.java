package com.example.multithread;

public class Multi {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ", priority = " + Thread.currentThread().getPriority());

        MyThread_2 myThread_2 = new MyThread_2();
        Thread a = new Thread(myThread_2, "A");
        Thread b = new Thread(myThread_2, "B");
        Thread c = new Thread(myThread_2, "C");
        Thread d = new Thread(myThread_2, "D");
        a.start();
        b.start();
        c.start();
        d.start();
    }
}

class MyThread_2 extends Thread {
    private int count = 5;
    public MyThread_2() {
        super();
    }

    @Override
    synchronized public void run() {
        count --;
        System.out.println(Thread.currentThread().getName() + ", " + count + ", priority = " + this.getPriority());
    }
}

class MyThread_1 extends Thread {
    private int count = 5;
    public MyThread_1(String name) {
        super();
        setName(name);
    }

    @Override
    public void run() {
        while (count > 0) {
            count --;
            System.out.println(Thread.currentThread().getName() + " " + count + ", priority = " + this.getPriority());
        }
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.println("thread name: " + Thread.currentThread().getName() + ", " + Thread.currentThread().getId()
                    + ", "+ Thread.currentThread().getPriority());
        }

    }
}

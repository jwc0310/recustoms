package com.example.multithread;

public class Run {

    public static void main(String[] args) {
        HasSelfPrivateNum has = new HasSelfPrivateNum();
       MyThreadA myThreadA = new MyThreadA(has, "a");
       myThreadA.start();


        MyThreadA myThreadB = new MyThreadA(has, "b");
        myThreadB.start();
    }
}

class HasSelfPrivateNum {

    private int num = 0;

    synchronized public void addI(String username) {
        try {
            if (username.equals("a")) {
                num = 100;
                System.out.println("a set over");
                Thread.sleep(2000);
            } else {
                num = 200;
                System.out.println("b set over");
            }

            System.out.println("username " + username +", num = " + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyThreadA extends Thread {
    private HasSelfPrivateNum hasSelfPrivateNum;
    private String username;
    public MyThreadA(HasSelfPrivateNum hasSelfPrivateNum, String username) {
        super();
        this.hasSelfPrivateNum = hasSelfPrivateNum;
        this.username = username;
    }

    @Override
    synchronized public void run() {
        hasSelfPrivateNum.addI(username);
    }
}
class MyThreadB extends Thread {

    private int count = 5;

    public MyThreadB() {
        super();
    }

    @Override
    synchronized public void run() {
        count --;
        System.out.println(Thread.currentThread().getName() + ", " + count + ", priority = " + this.getPriority());
    }
}

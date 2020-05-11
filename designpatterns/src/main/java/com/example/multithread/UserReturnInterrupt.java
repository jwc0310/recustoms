package com.example.multithread;
/**
 * stop thread
 */
public class UserReturnInterrupt {

    public static void main(String[] args) {
        try {
            MyThread3 thread2 = new MyThread3();
            thread2.start();
            Thread.sleep(1000);
            thread2.interrupt();
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
    }

}

class MyThread3 extends Thread {

    @Override
    public void run() {
        super.run();

        while (true) {
            if (this.isInterrupted()) {
                System.out.println("stop");
                return;
            }

            System.out.println("timer: " + System.currentTimeMillis());
        }
    }

}

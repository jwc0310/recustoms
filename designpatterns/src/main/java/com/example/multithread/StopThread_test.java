package com.example.multithread;
/**
 * stop thread
 */
public class StopThread_test {

    public static void main(String[] args) {
        try {
            MyThread2 thread2 = new MyThread2();
            thread2.start();
            Thread.sleep(1000);
            thread2.interrupt();
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
    }

}

class MyThread2 extends Thread {

    @Override
    public void run() {
        super.run();

        try {
            for (int i = 0; i < 500000; i++) {
                if (Thread.interrupted()) {
                    System.out.println("stop and exit");
                    throw new InterruptedException();
                }

                System.out.println("i=" + (i+1));
            }

            System.out.println("behind for");
        } catch (InterruptedException e) {
            System.out.println("over");
            e.printStackTrace();
        }
    }

}

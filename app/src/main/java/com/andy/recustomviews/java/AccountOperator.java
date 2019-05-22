package com.andy.recustomviews.java;

/**
 * Created by Administrator on 2017/6/17.
 */
public class AccountOperator implements Runnable {
    private Account account;
    public AccountOperator(Account account){
        this.account = account;
    }

    @Override
    public void run() {
        synchronized (account){
            account.deposit(500);
            account.withdraw(500);
            System.out.println(Thread.currentThread().getName() + ":" + account.getBalance());
        }
    }
}

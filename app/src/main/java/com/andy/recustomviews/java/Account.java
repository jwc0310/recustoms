package com.andy.recustomviews.java;

/**
 * Created by Administrator on 2017/6/17.
 */
public class Account {


    public Account(String name, float amount){
        this.name = name;
        this.amount = amount;
    }

    //存钱
    public void deposit(float amt){
        amount += amt;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //取钱
    public void withdraw(float amt){
        amount -= amt;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public float getBalance(){
        return amount;
    }

    String name;
    float amount;
}

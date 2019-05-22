package com.andy.recustomviews.java;

/**
 * Created by Administrator on 2017/3/23.
 */
public class Study {
    //依赖类
    private Boy boy;

    public Study() {
        boy = new Boy("Andy");
    }

    public void run(){
        boy.run();
    }



}

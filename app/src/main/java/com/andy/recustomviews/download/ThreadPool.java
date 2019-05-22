package com.andy.recustomviews.download;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Created by Administrator on 2016/12/30.
 */
public class ThreadPool {

    private static ThreadPool instance = null;
    private ExecutorService executorService;

    private ThreadPool(){
        executorService = Executors.newFixedThreadPool(3);
    }
    public static ThreadPool getInstance(){
        if(instance == null){
            instance = new ThreadPool();
        }
        return instance;
    }

    public void addTask(Runnable runnable){
        executorService.submit(runnable);
    }

    public void closePool(){
        executorService.shutdownNow();
    }
}

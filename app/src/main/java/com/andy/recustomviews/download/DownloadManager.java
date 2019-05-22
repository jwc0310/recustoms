package com.andy.recustomviews.download;

import android.util.Log;

/**
 *
 * Created by Administrator on 2016/12/30.
 */
public class DownloadManager {

    private static final String TAG = DownloadManager.class.getSimpleName();

    public static void download(String url){
        Log.e(TAG, "download");
    }
    public static void pause(String url){
        Log.e(TAG, "pause");
    }
    public static void resume(String url){
        Log.e(TAG, "resume");
    }
    public static void install(){
        Log.e(TAG, "install");
    }
    public static void open(){
        Log.e(TAG, "open");
    }
    public static void complete(){
        Log.e(TAG, "complete");
    }

}

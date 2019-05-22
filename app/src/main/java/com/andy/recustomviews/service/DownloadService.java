package com.andy.recustomviews.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.andy.recustomviews.download.DownloadManager;

/**
 *
 * Created by Administrator on 2016/12/30.
 */
public class DownloadService extends Service {

    private static final String TAG = DownloadService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.e(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.e(TAG, "onStartCommand " +startId);
        String action = intent.getExtras().getString("action");
        String url = intent.getExtras().getString("url");
        Log.e(TAG, "action = "+action+", url = "+url);
        if ("download".equals(action)){
            DownloadManager.download(url);
        }else if ("pause".equals(action)){
            DownloadManager.pause(url);
        }else if ("resume".equals(action)){
            DownloadManager.resume(url);
        }else if ("open".equals(action)){
            DownloadManager.open();
        }else if ("install".equals(action)){
            DownloadManager.install();
        }else if ("complete".equals(action)){
            DownloadManager.complete();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy(){
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

}

package com.andy.recustomviews.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.andy.recustomviews.activity.MainActivity;
import com.andy.recustomviews.download.DownloadManager;
import com.andy.recustomviews.tools.Shotter;

/**
 *
 * Created by Administrator on 2016/12/30.
 */
public class ShotterService extends Service {

    private static final String TAG = ShotterService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        Log.e("screencap", "receive start service");
        Shotter shotter = new Shotter();
        shotter.startScreenShot(new Shotter.OnShotListener() {
            @Override
            public void onFinish() {
                // finish();
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}

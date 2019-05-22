package com.andy.recustomviews.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.andy.recustomviews.download.DownloadManager;

/**
 *
 * Created by Administrator on 2016/12/30.
 */
public class LocalService extends Service {

    private static final String TAG = LocalService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return new LocalBinder();
    }

    public void sayHelloWorld(){
        Toast.makeText(this.getApplicationContext(), "hello world local service", Toast.LENGTH_SHORT).show();
    }

    public class LocalBinder extends Binder{
        public LocalService getService() {
            return LocalService.this;
        }
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
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy(){
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

}

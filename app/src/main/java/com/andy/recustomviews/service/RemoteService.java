package com.andy.recustomviews.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.andy.recustomviews.activity.BinderActivity;
import com.andy.recustomviews.download.DownloadManager;

/**
 *
 * Created by Administrator on 2016/12/30.
 */
public class RemoteService extends Service {
    public static final int MSG_SAY_HELLO = 0;

    private static final String TAG = RemoteService.class.getSimpleName();

    Handler IncomingHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if(msg.replyTo != null){
                Message msg_client = this.obtainMessage();
                msg_client.what = BinderActivity.SAY_HELLO_TO_CLIENT;
                try {
                    ((Messenger)msg.replyTo).send(msg_client);
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(RemoteService.this.getApplicationContext(), "Hello Remote Service!",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }

    };

    Messenger  messager = new Messenger (IncomingHandler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return messager.getBinder();
    }

    public void sayHelloWorld(){
        Toast.makeText(this.getApplicationContext(), "hello world local service", Toast.LENGTH_SHORT).show();
    }

    public class LocalBinder extends Binder{
        public RemoteService getService() {
            return RemoteService.this;
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

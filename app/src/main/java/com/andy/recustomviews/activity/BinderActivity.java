package com.andy.recustomviews.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.andy.recustomviews.R;
import com.andy.recustomviews.download.DownloadRunnable;
import com.andy.recustomviews.download.ThreadPool;
import com.andy.recustomviews.service.DownloadService;
import com.andy.recustomviews.service.LocalService;
import com.andy.recustomviews.service.RemoteService;

/**
 * Created by Administrator on 2016/12/30.
 */
public class BinderActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = BinderActivity.class.getSimpleName();
    private static final String url = "www.baidu.com";
    ServiceConnection mSc;
    private LocalService localService;

    public static final int SAY_HELLO_TO_CLIENT = 0;

    class IncomingHandler extends Handler{

        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case SAY_HELLO_TO_CLIENT:
                    Toast.makeText(BinderActivity.this, "hello world remote Client", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    Messenger messenger = new Messenger(new IncomingHandler());

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_binder);
        findViewById(R.id.download).setOnClickListener(this);
        findViewById(R.id.pause).setOnClickListener(this);
        findViewById(R.id.open).setOnClickListener(this);
        findViewById(R.id.resume).setOnClickListener(this);
        findViewById(R.id.install).setOnClickListener(this);
        findViewById(R.id.complete).setOnClickListener(this);
        for (int i = 0; i < 10; i++){
            ThreadPool.getInstance().addTask(new DownloadRunnable("www.csdn.net/"+i, "tag"+i));
        }


        mSc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.e(TAG, "service connected");
                Message msg = new Message();
                msg.what = RemoteService.MSG_SAY_HELLO;
                msg.replyTo = messenger;
                try {
                    messenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.e(TAG, "service disconnected");
            }
        };

    }

    @Override
    protected void onStart(){
        super.onStart();
        //TODO 绑定service
    }

    @Override
    protected void onStop(){
        super.onStop();
        //TODO 解绑service
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    private void startService(String action){
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("url", url);
        intent.putExtra("action", action);
        startService(intent);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.download == id){
            Intent service = new Intent(this, LocalService.class);
            this.bindService(service, mSc, Context.BIND_AUTO_CREATE);
        }else if (R.id.pause == id){
            this.unbindService(mSc);
        }else if (R.id.open == id){
            localService.sayHelloWorld();
        }else if (R.id.resume == id){
            Intent intent = new Intent(this, RemoteService.class);
            this.bindService(intent, mSc, Context.BIND_AUTO_CREATE);
        }else if (R.id.install == id){
            startService("install");
        }else if (R.id.complete == id){
            this.unbindService(mSc);
        }
    }
}

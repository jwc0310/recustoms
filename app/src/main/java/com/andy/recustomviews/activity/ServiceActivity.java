package com.andy.recustomviews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.andy.recustomviews.R;
import com.andy.recustomviews.download.DownloadRunnable;
import com.andy.recustomviews.download.ThreadPool;
import com.andy.recustomviews.service.DownloadService;

/**
 * Created by Administrator on 2016/12/30.
 */
public class ServiceActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ServiceActivity.class.getSimpleName();
    private static final String url = "www.baidu.com";
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_service);
        findViewById(R.id.download).setOnClickListener(this);
        findViewById(R.id.pause).setOnClickListener(this);
        findViewById(R.id.open).setOnClickListener(this);
        findViewById(R.id.resume).setOnClickListener(this);
        findViewById(R.id.install).setOnClickListener(this);
        findViewById(R.id.complete).setOnClickListener(this);
        for (int i = 0; i < 10; i++){
            ThreadPool.getInstance().addTask(new DownloadRunnable("www.csdn.net/"+i, "tag"+i));
        }
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
                startService("download");
        }else if (R.id.pause == id){
            startService("pause");
        }else if (R.id.open == id){
            startService("open");
        }else if (R.id.resume == id){
            startService("resume");
        }else if (R.id.install == id){
            startService("install");
        }else if (R.id.complete == id){
            startService("complete");
        }
    }
}

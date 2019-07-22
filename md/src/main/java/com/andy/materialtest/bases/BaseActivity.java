package com.andy.materialtest.bases;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.andy.materialtest.ActivityCollector;
import com.andy.materialtest.framework.EventbusUtil;

public abstract class BaseActivity extends AppCompatActivity {

    public abstract int contentViewResId();

    protected Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        ctx = this;
        setContentView(contentViewResId());
        EventbusUtil.getInstance().register(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        EventbusUtil.getInstance().unregister(this);
    }
}

package com.andy.materialtest.bases;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.andy.materialtest.ActivityCollector;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    public abstract int contentViewResId();

    protected Context ctx;
    protected AppCompatActivity activity;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        ctx = this;
        activity = this;
        setContentView(contentViewResId());
        unbinder = ButterKnife.bind(this);
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
    public void onDestroy(){
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

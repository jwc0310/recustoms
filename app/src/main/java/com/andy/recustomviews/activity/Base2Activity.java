package com.andy.recustomviews.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * base activity
 * Created by Andy on 2016/12/16.
 */
public abstract class Base2Activity extends AppCompatActivity {

    public Context context;
    private Unbinder unbinder;
    @Override
    protected final void onCreate(Bundle bundle){
        super.onCreate(bundle);
        context = this;
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        init(bundle);
    }
    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onDestroy(){
        if (null != unbinder){
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public abstract int getLayoutId();
    public abstract void init(Bundle bundle);
}

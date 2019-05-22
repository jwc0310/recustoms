package com.andy.recustomviews.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * base activity
 * Created by Andy on 2016/12/16.
 */
public class BaseActivity extends AppCompatActivity {

    public Context context;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        context = this;
    }
}

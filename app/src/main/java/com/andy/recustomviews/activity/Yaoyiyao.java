package com.andy.recustomviews.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andy.recustomviews.R;
import com.andy.recustomviews.util.ShakeUtils;

public class Yaoyiyao extends AppCompatActivity {

    private ShakeUtils shakeUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yaoyiyao);
        shakeUtils = new ShakeUtils(this);
        shakeUtils.setShakeListener(new ShakeUtils.OnShakeListener() {
            @Override
            public void onShake() {

            }
        });

        shakeUtils.onResume();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        shakeUtils.onPause();
    }
}

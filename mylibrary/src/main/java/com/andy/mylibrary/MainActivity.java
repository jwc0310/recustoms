package com.andy.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log("onCreate");
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpTo();
            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        log("onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        log("onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        log("onNewIntent");

        String navigateTo = intent.getStringExtra("extra_navigate_to");
        if (navigateTo != null && navigateTo.length() >= 0) {
            log(navigateTo);
        }

    }

    protected void jumpTo() {
        startActivity(new Intent(this, AnoActivity.class));
    }

    private void log(String string){
        Log.e("MainActivity", string);
    }
}

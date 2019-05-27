package com.andy.materialtest;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class Test1Activity extends BaseActivity {

    private Context context;
    private MultiDirectionSlidingDrawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        context = this;
        drawer = (MultiDirectionSlidingDrawer) findViewById(R.id.drawer);
        findViewById(R.id.test1_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.showAnim();
                Snackbar.make(view, "FAB", Snackbar.LENGTH_SHORT)
                        .setAction("cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //单机事件代表点击消除action后的响应事件
                                Toast.makeText(context, "取消该事件", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
    }


    @Override
    protected void onPause(){
        super.onPause();
        if (drawer != null)
            drawer.glPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (drawer != null)
            drawer.glResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        if (KeyEvent.KEYCODE_BACK == keyCode){
            if (drawer != null && drawer.isOpened()){
                drawer.animateClose();
                return false;
            }
        }
        return super.onKeyDown(keyCode, keyEvent);
    }
}
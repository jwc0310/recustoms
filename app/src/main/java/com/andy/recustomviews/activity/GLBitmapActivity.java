package com.andy.recustomviews.activity;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.andy.recustomviews.R;

import java.util.Random;

public class GLBitmapActivity extends AppCompatActivity {

    private GLSurfaceView glView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glbitmap);
        setTheme(android.R.style.Theme_Translucent_NoTitleBar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_glbitmap);
        glView = (GLSurfaceView) findViewById(R.id.gl_view);
        glView.setRenderer(new MyGLRenderer(this));
        glView.getAlpha();
    }

    @Override
    protected void onPause(){
        super.onPause();
        glView.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        glView.onResume();
    }
}

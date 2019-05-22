package com.andy.recustomviews.activity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.andy.recustomviews.R;
import com.andy.xyfloatingball.floating.WaterWaveView;

public class ViewActivity extends AppCompatActivity{

    Button button;
    RelativeLayout mLayout;
    View view;
    private Context context;


    private ViewGroup.LayoutParams params;
    private WaterWaveView waterWaveView;
    private ImageView iv_frame;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new WaterWaveView(this));
//        setContentView(R.layout.activity_view);
        setContentView(R.layout.activity_view);
        context = this;
        iv_frame = (ImageView) findViewById(R.id.iv_frame);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_frame.setImageResource(R.drawable.frame);
                animationDrawable = (AnimationDrawable) iv_frame.getDrawable();
                animationDrawable.setOneShot(true);
                animationDrawable.start();
            }
        });

        mLayout = (RelativeLayout) findViewById(R.id.layout);

        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float x = event.getX();
                float y = event.getY();
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ImageView imageView = new ImageView(context);
                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                        layoutParams.height = 120;
                        layoutParams.width = 120;
                        imageView.setImageResource(R.drawable.frame);
                        mLayout.addView(imageView);
                        AnimationDrawable animation = (AnimationDrawable) iv_frame.getDrawable();
                        animation.setOneShot(true);
                        animation.start();
                        break;
                }

                return false;
            }
        });


//        waterWaveView.setZOrderMediaOverlay(true);
//        waterWaveView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

//        button = (TestButton) findViewById(R.id.button);
//        mLayout = (TestLinearLayout) findViewById(R.id.layout);
//
//        button.setOnClickListener(this);
//        mLayout.setOnClickListener(this);
//
//        button.setOnTouchListener(this);
//        mLayout.setOnTouchListener(this);
//        getWindow();

//        WindowManager windowManager = getWindowManager();
//
//        DisplayMetrics dm = new DisplayMetrics();
//        windowManager.getDefaultDisplay().getMetrics(dm);
//
//        WaterWaveView waterWaveView = new WaterWaveView(this);
//
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//        params.format = PixelFormat.RGBA_8888;
//        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        params.width = dm.widthPixels;
//        params.height = dm.heightPixels;
//        params.x = 0;
//        params.y = 0;
//
//        waterWaveView.setZOrderMediaOverlay(true);
//        waterWaveView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
//        windowManager.addView(waterWaveView, params);

    }


    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}

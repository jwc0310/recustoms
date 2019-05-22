package com.andy.recustomviews.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by Administrator on 2017/6/6.
 */
public class TestButton extends Button {
    private static final String TAG = "TestButton";
    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        Log.e(TAG, "dispatchTouchEvent-- action="+event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        Log.e(TAG, "onTouchEvent-- action="+event.getAction());
        return super.onTouchEvent(event);
    }
}

package com.andy.xyfloatingball.floating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.xyfloatingball.R;

/**
 * free浮窗
 * Created by Andy on 2016/12/7.
 */
public class FloatWindowFreeView extends LinearLayout {

    /**
     * 记录大悬浮窗的宽度
     */
    public static int viewWidth;
    /**
     * 记录大悬浮窗的高度
     */
    public static int viewHeight;

    private Context context;
    private LinearLayout dialog;
    private TextView view;

    private WindowManager.LayoutParams mParams;

    public FloatWindowFreeView(final Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init(){
        LayoutInflater.from(context).inflate(R.layout.floating_window_free, this);
        dialog = (LinearLayout)findViewById(R.id.free_dialog);
        view = (TextView) findViewById(R.id.free_text);
        viewWidth = dialog.getLayoutParams().width;
        viewHeight = dialog.getLayoutParams().height;
    }

    public void setFree(String free){
        if (view != null){
            view.setText(free+"MB");
        }
    }

    public void show(){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.free_dialog_show);
        dialog.setVisibility(View.VISIBLE);
        dialog.startAnimation(animation);
    }
    public void dimiss(Animation.AnimationListener listener){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.free_dialog_dimiss);
        animation.setAnimationListener(listener);
        dialog.setVisibility(View.INVISIBLE);
        dialog.startAnimation(animation);
    }

    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }
}

package com.andy.xyfloatingball.floating;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.andy.xyfloatingball.R;

/**
 * Created by Administrator on 2017/9/11.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    float left,top;
    Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.cat);
    final float w = bitmap.getWidth();
    final float h = bitmap.getHeight();
    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attributeSet){
        this(context, attributeSet, 0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        setZOrderOnTop(true);
//        setZOrderMediaOverlay(true);
        getHolder().setFormat(PixelFormat.TRANSPARENT);//设置为透明
        //getHolder().setFormat(PixelFormat.TRANSLUCENT);//半透明</span>
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        getHolder().addCallback(this);
        setClickable(false);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        doDraw1();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    private void doDraw() {
        // TODO Auto-generated method stub
        Canvas canvas=getHolder().lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawBitmap(bitmap, left - w/2, top - h/2, null);
        getHolder().unlockCanvasAndPost(canvas);
    }

    private void doDraw1() {

        Log.e("Andy", "w = "+w+", h = "+h);
        // TODO Auto-generated method stub
        Canvas canvas=getHolder().lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        left=event.getX();
        top=event.getY();

        Log.e("Andy", "event = "+event.getAction()+", x = "+left+", y = "+top);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                doDraw();
            case MotionEvent.ACTION_MOVE:
                doDraw();
                break;
            case MotionEvent.ACTION_UP:
                doDraw1();
                break;
            default:
                break;
        }


        return super.onTouchEvent(event);
    }
}
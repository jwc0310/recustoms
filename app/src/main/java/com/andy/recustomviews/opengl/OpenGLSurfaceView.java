package com.andy.recustomviews.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/3/25.
 */
public class OpenGLSurfaceView extends GLSurfaceView{

    public OpenGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}

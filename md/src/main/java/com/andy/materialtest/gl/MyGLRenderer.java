package com.andy.materialtest.gl;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import com.andy.materialtest.gl.GLBitmap;
import com.andy.materialtest.gl.GLUtil;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author Zheng Haibo
 * @date 2014年10月20日 下午2:49:17
 * @Description: TODO
 */
public class MyGLRenderer implements Renderer {
    Context context; // Application's context
    //private Square square;
    private GLBitmap glBitmap;
    private int width = 0;
    private int height = 0;
    private long frameSeq = 0;
    private int viewportOffset = 0;
    private int maxOffset = 400;

    private final float r = 1f;
    float[] lineArray = new float[]{
            0f, (5 * r), 0f,
            0f, 0f, 0f,
    };
    //顶点颜色数组
    private float[] mcolorArray = {
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f
    };

    public MyGLRenderer(Context context) {
        this.context = context;
        glBitmap = new GLBitmap();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0) { // Prevent A Divide By Zero By
            height = 1; // Making Height Equal One
        }
        this.width = width;
        this.height = height;
        gl.glViewport(0, 0, width, height); // Reset The
        // Current
        // Viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select The Projection Matrix
        gl.glLoadIdentity(); // Reset The Projection Matrix

        // Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
                100.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW); // Select The Modelview Matrix
        gl.glLoadIdentity();
    }

    /**
     * 每隔16ms调用一次
     */
    // 控制旋转的角度
    private float rotate;

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        FloatBuffer vertex = GLUtil.getFloatBuffer(lineArray);
        FloatBuffer color = GLUtil.getFloatBuffer(mcolorArray);
//
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, color);
//    	gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, lineArray.length / 3);
//
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // Reset the Modelview Matrix
        gl.glLoadIdentity();
        // the same as moving the camera 5
        // units away
        //旋转
        gl.glTranslatef(0.0f, radius, 0f);
        gl.glRotatef(getRotate(), 0f, 0f, 1f);
        gl.glTranslatef(0.0f, -radius, 0f);
        // move 5 units INTO the screen is
        gl.glTranslatef(0.0f, 0.0f, -5.0f);
        glBitmap.drawLine(gl);
        // 沿着Y轴旋转
        gl.glRotatef(rotate * 5 % 360, 0f, 1f, 0f);
        // square.draw(gl);
        glBitmap.draw(gl);

        rotate += 1;

        if (rotate >= 360 * 5) {
            rotate = 0;
        }
    }

    private final float radius = 3;
    private final float corner = 10;

    private float getRotate() {

        float tmp = rotate / 5 % (4 * corner);

        if (tmp < corner) {

        } else if (tmp < 2 * corner) {
            tmp = 2 * corner - tmp;
        } else if (tmp < 3 * corner) {
            tmp = 2 * corner - tmp;
        } else {
            tmp = tmp - 4 * corner;
        }
        return tmp;
    }

    /**
     * 通过改变gl的视角获取
     *
     * @param gl
     */
    private void changeGLViewport(GL10 gl) {
        System.out.println("time=" + System.currentTimeMillis());
        frameSeq++;
        viewportOffset++;
        // The
        // Current
        if (frameSeq % 100 == 0) {// 每隔100帧，重置
            gl.glViewport(0, 0, width, height);
            viewportOffset = 0;
        } else {
            int k = 4;
            gl.glViewport(-maxOffset + viewportOffset * k, -maxOffset
                    + viewportOffset * k, this.width - viewportOffset * 2 * k
                    + maxOffset * 2, this.height - viewportOffset * 2 * k
                    + maxOffset * 2);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl,
                                 javax.microedition.khronos.egl.EGLConfig arg1) {
        gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
        gl.glClearDepthf(1.0f); // Depth Buffer Setup
        gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
        gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        glBitmap.loadGLTexture(gl, this.context);
    }
}

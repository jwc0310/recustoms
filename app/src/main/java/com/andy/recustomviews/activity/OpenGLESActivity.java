package com.andy.recustomviews.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.WindowManager;

import com.andy.recustomviews.gl.GLUtil;
import com.andy.recustomviews.opengl.IOpenGLDemo;
import com.andy.recustomviews.opengl.OpenGlRenderer;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES10.GL_LINE_SMOOTH_HINT;
import static android.opengl.GLES10.GL_POINT_SMOOTH_HINT;
import static android.opengl.GLES10.glMultMatrixf;

public class OpenGLESActivity extends BaseActivity implements IOpenGLDemo {

    private GLSurfaceView glSurfaceView;
    protected GLBitmap glBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        glSurfaceView = new GLSurfaceView(this);
        glBitmap = new GLBitmap();
        glSurfaceView.setRenderer(new OpenGlRenderer(this, glBitmap, this));
        setContentView(glSurfaceView);
    }



    @Override
    public void onResume(){
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        glSurfaceView.onPause();
    }

    public float line_vertices[] = {
            0f, 0f, 0f, // V1 - bottom left
            0f, 1f, 0.0f, // V2 - top left
    };

    public float line_vertices2[] = {
            0f, 0f, 0f, // V1 - bottom left
            1f, 1f, 0.0f, // V2 - top left
    };

    //顶点颜色数组
    private float[] mcolorArray = {
            1f, 1f, 1f, 0f,
            1f, 1f, 1f, 0f,
            1f, 1f, 1f, 0f
    };


    public void drawLine(GL10 gl, float[] vertices){
        gl.glEnable(GL10.GL_BLEND);
        gl.glEnable(GL10.GL_LINE_SMOOTH);
        gl.glHint (GL10.GL_LINE_SMOOTH, GL10.GL_NICEST);
        gl.glHint(GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);

        //划线
        gl.glLineWidth(0.2f);
        gl.glTranslatef(0, 0, -0.3f);
        FloatBuffer vertex = GLUtil.getFloatBuffer(vertices);
        FloatBuffer color = GLUtil.getFloatBuffer(mcolorArray);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, color);

        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, vertices.length / 3);

        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glTranslatef(0, 0, 0.3f);

        gl.glDisable(GL10.GL_LINE_SMOOTH);
        gl.glDisable(GL10.GL_BLEND);

    }


    @Override
    public void DrawScene(GL10 gl) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        drawLine(gl, line_vertices);
        drawLine(gl, line_vertices2);
    }
}

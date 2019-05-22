package com.andy.recustomviews.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.andy.recustomviews.activity.GLBitmap;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * render
 * Created by Administrator on 2017/3/25.
 */
public class OpenGlRenderer implements GLSurfaceView.Renderer {

    private final IOpenGLDemo openGLDemo;
    private final GLBitmap glBitmap;
    private Context context;

    public OpenGlRenderer(IOpenGLDemo demo, GLBitmap glBitmap, Context context){
        this.openGLDemo = demo;
        this.glBitmap = glBitmap;
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //仅调用一次
        //set backgound color
        // Set the background color to black ( rgba ).
        gl10.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // Enable Smooth Shading, default not really needed.
        gl10.glShadeModel(GL10.GL_SMOOTH);
        // Depth buffer setup.
        gl10.glClearDepthf(1.0f);
        // Enables depth testing.
        gl10.glEnable(GL10.GL_DEPTH_TEST);

        // The type of depth testing to do.
        gl10.glDepthFunc(GL10.GL_LEQUAL);
        // Really nice perspective calculations.
        gl10.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_NICEST);

        glBitmap.loadGLTexture(gl10, this.context);

//        gl10.glEnable(GL10.GL_TEXTURE_2D); // Enable Texture Mapping ( NEW )
    }

    //i:width i1:height
    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        //view的几何形状发生变化，包括横竖屏切换
        // Sets the current view port to the new size.
        gl10.glViewport(0, 0, width, height);
        // Select the projection matrix
        gl10.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl10.glLoadIdentity();
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl10, 45.0f,
                (float) width / (float) height,
                0.1f, 100.0f);
        // Select the modelview matrix
        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl10.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        if (openGLDemo != null ){
            openGLDemo.DrawScene(gl10);
        }
    }

    public static final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    public static final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    public static int loadShader(int type, String shaderCode){
        // 创建一个vertex shader类型(GLES20.GL_VERTEX_SHADER)
        // 或fragment shader类型(GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // 将源码添加到shader并编译之
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}

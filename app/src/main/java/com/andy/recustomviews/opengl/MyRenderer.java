package com.andy.recustomviews.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * render
 * Created by Administrator on 2017/3/25.
 */
public class MyRenderer implements GLSurfaceView.Renderer {

    private Triangle triangle;
    // mMVPMatrix是"Model View Projection Matrix"的缩写
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];//定义投影矩阵变量
    private final float[] mViewMatrix = new float[16];//定义相机视图矩阵变量
    private final float[] mRotationMatrix = new float[16];
    private volatile float mAngle;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //仅调用一次
        //set backgound color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        triangle = new Triangle();
//        square = new Square();
    }

    //i:width i1:height
    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        //view的几何形状发生变化，包括横竖屏切换
        GLES20.glViewport(0, 0, width, height);

//        投影变换的数据是在GLSurfaceView.Renderer 类的 onSurfaceChanged() 方法中计算。
//        下面的例子跟据GLSurfaceView 的宽和高，
//        使用Matrix.frustumM()方法计算出了一个投影变换Matrix：

        float ratio = (float) width / height;
        // 此投影矩阵在onDrawFrame()中将应用到对象的坐标
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        float[] scratch = new float[16];
        //每次view被重绘时被调用
        //重绘背景色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        // 设置相机的位置(视图矩阵)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // 将mProjectionMatrix和mViewMatrix矩阵相乘并赋给mMVPMatrix
//        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // 创建旋转矩阵
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);
//        Matrix.setRotateM(mRotationMatrix, 0 , mAngle, 0, 0, -1.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // 绘制形状
        triangle.draw(scratch);
//        triangle.draw();
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


    public float getmAngle(){
        return mAngle;
    }
    public void setmAngle(float angle){
        mAngle = angle;
    }

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

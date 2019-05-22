package com.andy.recustomviews.activity;

import android.os.Bundle;

import com.andy.recustomviews.gl.GLUtil;
import com.andy.recustomviews.opengl.IOpenGLDemo;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class DrawPoint extends OpenGLESActivity 
  implements IOpenGLDemo {

	private final float r = 10f;
	float[] lineArray = new float[]{
		0f, (5 * r), 0f,
			0f, 0f, 0f,
	};
 	float[] vertexArray = new float[]{
			-0.6f , 0.6f , 0f,

			-0.2f , 0f , 0f ,

			0.2f , 0.6f , 0f ,

			0.6f , 0f , 0f
		};

	//顶点颜色数组
	private float[] mcolorArray = {
			1f, 0f , 0f,0f,
			0f , 1f , 0f ,0f,
			0f , 0f , 1f ,0f
	};
   
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    }
	@Override
	public void DrawScene(GL10 gl) {
		super.DrawScene(gl);

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
//
//
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

		rotate+=1;

		if (rotate >= 360 * 5){
			rotate = 0;
		}
		glBitmap.draw(gl);

	}

	// 控制旋转的角度
	private float rotate;
	private final float radius = 5;
	private final float corner = 15;
	private float getRotate(){

		float tmp = rotate / 5 % (4*corner);
		if (tmp < corner){

		}else if (tmp < 2 * corner){
			tmp = 2 * corner - tmp;
		}else if (tmp < 3 * corner){
			tmp = 2 * corner - tmp;
		}else {
			tmp = tmp - 4 * corner;
		}
		return tmp;
	}
	
	
}

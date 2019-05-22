package com.andy.recustomviews.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Bundle;

import com.andy.recustomviews.R;
import com.andy.recustomviews.gl.GLUtil;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLTexture extends Activity {

	private static final String TAG = "opengldemo";
	
	private GLSurfaceView glSurfaceView;
 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 如果本设备支持OpenGl ES 2.0
		if (IsSupported()) {

			// 先建GLSurfaceView实例
			glSurfaceView = new GLSurfaceView(this);

			// 创建渲染器实例
			MyRenderer mRenderer = new MyRenderer();

			// 设置渲染器
			glSurfaceView.setRenderer(mRenderer);

			// 显示SurfaceView
			setContentView(glSurfaceView);
		}
	}

	private boolean IsSupported() {
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
		boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x2000;

		return supportsEs2;
	}

	public class MyRenderer implements GLSurfaceView.Renderer {
		 // 定义长方形顶点  
	    float[] cubeVertices =   {
			     -0.5f, -0.5f, 0.0f,
	    		 
	    		 0.5f, -0.5f, 0.0f,
	    		 
	    		 -0.5f,  0.5f, 0.0f,
	    		 
	    		 0.5f, 0.5f, 0.0f }; 
       
	   
	    
	     float texCoords[] =  {
	            
	    		 0.0f, 1.0f,  
	    		 
	    		 1.0f, 1.0f, 
	    		 
	    		 0.0f, 0.0f, 
	    		 
	    		 1.0f, 0.0f,  
	      }; 
	    
      //索引数组
	   private short[] indices={  
	            0,1,2,  
	            1, 3, 2 
	      };  	   	   
	    //纹理名称数组
	    int [] textureids = new int[1];
   
	    
	    
	    FloatBuffer VerticesBuffer;  
	    ShortBuffer indexbuffer;

	    FloatBuffer TextureBuffer;  
	    
	    
		private Bitmap mBitmap;
		
	 
	 		    
	  	public MyRenderer() {
			//获取浮点形缓冲数据
	  		VerticesBuffer = GLUtil.getFloatBuffer(cubeVertices);
			 
	  		//获取浮点型索引数据
	  		indexbuffer= GLUtil.getShortBuffer(indices);
	  		
	  		//获取纹理缓冲数组
	  		TextureBuffer= GLUtil.getFloatBuffer(texCoords);
	  		
	  		
	  		mBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.texture_img);
	  		
	   	}
 		// Surface创建的时候调用
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			
	        gl.glClearColor(0, 0, 0, 0);  
 	    	
		}

		// Surface改变的的时候调用
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			 // 设置3D视窗的大小及位置  
	        gl.glViewport(0, 0, width, height);  
	      	}
	        
		// 在Surface上绘制的时候调用
		@Override
		public void onDrawFrame(GL10 gl) { // 清除屏幕缓存和深度缓存  
	        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);  
	        // 启用顶点座标数据  
	        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
	        	// 启动纹理坐标数据
	        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	         
	        //打开纹理
	        gl.glEnable(GL10.GL_TEXTURE_2D); 
	        //创建纹理
	        gl.glGenTextures(1, textureids, 0);  
	        //绑定纹理  
	        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureids[0]);  
	        
	        // 设置纹理参数
	        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,  GL10.GL_NEAREST);  
	        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,   GL10.GL_NEAREST);  
	        
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);

         
	        // 生成纹理  
	        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
	        
	        
	         // 设置顶点的位置数据  
	         gl.glVertexPointer(3, GL10.GL_FLOAT, 0, VerticesBuffer);  
	         //  设置纹理顶点数据
	         gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, TextureBuffer);
 
	          
	         //绘制
	  	     gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexbuffer);  
	  	     //禁止顶点数组
  	         gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);  
	         
  	         //关闭纹理
  	         gl.glDisable(GL10.GL_TEXTURE_2D); 
  	         //关闭纹理坐标数据
  	         gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
  	       
	       }
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (glSurfaceView != null) {
			glSurfaceView.onPause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (glSurfaceView != null) {
			glSurfaceView.onResume();
		}
	}
}

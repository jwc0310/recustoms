package com.andy.recustomviews.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.andy.recustomviews.gl.GLUtil;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLLight extends Activity {

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
		 // 定义立方体的8个顶点  
	    float[] cubeVertices = {  	    	   
	    		//左面 
	    		  -0.5f,0.5f,0.5f,
	    		  -0.5f,-0.5f,0.5f,
	    		  -0.5f,0.5f,-0.5f,
	    		  -0.5f,-0.5f,-0.5f,
	    		
	    		//右面
	    		  0.5f, 0.5f,0.5f,	    		  
	 			  0.5f,-0.5f,0.5f,
	    		  0.5f,-0.5f,-0.5f,
	    		  0.5f,0.5f,-0.5f ,
	    		
	    		//前面
	    		 -0.5f,0.5f,0.5f,
	    		 -0.5f,-0.5f,0.5f,
	    		  0.5f,-0.5f,0.5f,
	    		  0.5f, 0.5f,0.5f,
	    		
	    		//后面 
	    		  0.5f,-0.5f,-0.5f,
	    		  0.5f,0.5f,-0.5f, 
	    		 -0.5f,0.5f,-0.5f,
	    		 -0.5f,-0.5f,-0.5f,
	    		
	    		//上面
	    		 -0.5f,0.5f,0.5f,
	    		  0.5f, 0.5f,0.5f,
	    		  0.5f,0.5f,-0.5f,
	    		  -0.5f,0.5f,-0.5f,
	    		
	    		//下面
	    		  -0.5f,-0.5f,0.5f,
	    		  0.5f,-0.5f,0.5f,
	    		  0.5f,-0.5f,-0.5f,
	    		  -0.5f,-0.5f,-0.5f
	     };  
	   
	   //索引数组
	   private short[] indices={  
	            0,1,2,  
	            0,2,3,  
	           
	            4,5,6,  
	            4,6,7,  
	            
	            8,9,10,  
	            8,10,11,  
	            
	            12,13,14,  
	            12,14,15,  
	            
	            16,17,18,  
	            16,18,19,  
	            
	            20,21,22,  
	            20,22,23,  
	    };  	   
	    //环境光强度
	    float[] amb_light = {1f, 1f, 1f, 1f };  
	    //漫反射光强度
	    float[] diff_light = { 1f, 1f, 1f, 1f };
	    //镜面反射光强度
	    float[] spec_light = {1f, 1f, 1f, 1f };	   
	   
	    //光源位置
	    float[] pos_light = {0f, 0.0f, 1f, 0.4f};
	    
	    //光源方向
	    float[] spot_dir = { 0.0f, 0.0f,  -1f, };
	    
	    
	    //环境光材质
	    float amb_mat[]  = { 0f,  0.3f, 0f, 1f };
	    //漫反射光材质
	    float diff_mat[]  ={ 0f,  0.3f, 0f, 1f };
	    //镜面反射光材质
	    float spec_mat[] = { 0f,  0.3f, 0f, 1f };	    
	    //本身颜色
	    float emi_mat[] = { 0.0f, 0f, 0.0f, 1.0f };
	   
	    //镜面指数 
	    float shini_mat  =  0f; 
	    
	     
	     // 控制旋转的角度  
	    private float rotate;   
		 
	    FloatBuffer VerticesBuffer;  
	    FloatBuffer Colorbuffer;      
		
	    ShortBuffer indexbuffer;
		
	     FloatBuffer amb_light_buffer;
		 FloatBuffer diff_light_buffer;
		 FloatBuffer spec_light_buffer;
		 FloatBuffer pos_light_buffer;
		 FloatBuffer spot_dir_buffer;
		 
		 FloatBuffer amb_mat_buffer;
		 FloatBuffer diff_mat_buffer;
		 FloatBuffer spec_mat_buffer;
		 FloatBuffer emi_mat_buffer;
	 		    
	  	public MyRenderer() {
			//获取浮点形缓冲数据
	  		VerticesBuffer = GLUtil.getFloatBuffer(cubeVertices);
			 
	  		//获取浮点型索引数据
	  		indexbuffer= GLUtil.getShortBuffer(indices);
	  		
	  		//获取浮点型环境光数据
	  		amb_light_buffer= GLUtil.getFloatBuffer(amb_light);
	  		//获取浮点型漫反射光数据
	  		diff_light_buffer= GLUtil.getFloatBuffer(diff_light);
	  		//获取浮点型镜面反射光数据
	  		spec_light_buffer= GLUtil.getFloatBuffer(spec_light);
	  		//获取浮点型光源位置数据
	  		pos_light_buffer= GLUtil.getFloatBuffer(pos_light);
	  		
	  		//获取浮点型光源方向数据
	  		spot_dir_buffer= GLUtil.getFloatBuffer(spot_dir);

	  		//获取浮点型环境光材质数据
	  		amb_mat_buffer= GLUtil.getFloatBuffer(amb_mat);
	  		//获取浮点型漫反射光材质数据
	  		diff_mat_buffer= GLUtil.getFloatBuffer(diff_mat);
	  		//获取浮点型镜面反射光材质数据
	  		spec_mat_buffer= GLUtil.getFloatBuffer(spec_mat);
	  		//获取浮点型本身颜色数据
	  		emi_mat_buffer= GLUtil.getFloatBuffer(emi_mat);
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
	         
	        //打开光源总开关
	        gl.glEnable(GL10.GL_LIGHTING);
	        
	        //打开0号光源
		    gl.glEnable(GL10.GL_LIGHT0); 
	        
		    //设置光源位置
	        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, pos_light_buffer);
	      
	        //设置光源方向
	        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPOT_DIRECTION , spot_dir_buffer);
	       
	        //设置光源种类
	        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT ,  amb_light_buffer );
	        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE , diff_light_buffer );
	        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, spec_light_buffer);
	        
	        
	        
	        //设置聚光强度
	        gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_EXPONENT, 64f);
	        
	        //设置聚光角度
	        gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_CUTOFF, 45f);

	        //设置材质种类 
	        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, amb_mat_buffer);
	        		 
	        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_DIFFUSE, diff_mat_buffer);
	        		 
	        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_SPECULAR, spec_mat_buffer);
	 
	        //设置镜面指数
	        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shini_mat);  
  	       
	         // 重置当前的模型视图矩阵  
	         gl.glLoadIdentity();  
	        	    	         
	         // 沿着Y轴旋转  
	         gl.glRotatef(rotate, 0f, 1f, 0f);  
	       
	         
	         // 设置顶点的位置数据  
	         gl.glVertexPointer(3, GL10.GL_FLOAT, 0, VerticesBuffer);  
	         //绘制
	  	     gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexbuffer);  
	  	     //禁止顶点数组
  	         gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);  
	           
  	        // 旋转角度增加1  
	        rotate+=1;  
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

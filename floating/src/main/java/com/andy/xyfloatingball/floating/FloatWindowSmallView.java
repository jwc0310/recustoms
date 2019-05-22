package com.andy.xyfloatingball.floating;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andy.xyfloatingball.R;

import java.lang.reflect.Field;

public class FloatWindowSmallView extends LinearLayout implements View.OnHoverListener {

	/**
	 * 记录小悬浮窗的宽度
	 */
	public static int viewWidth;

	/**
	 * 记录小悬浮窗的高度
	 */
	public static int viewHeight;

	/**
	 * 记录系统状态栏的高度
	 */
	 private static int statusBarHeight;

	/**
	 * 用于更新小悬浮窗的位置
	 */
	private WindowManager windowManager;

	/**
	 * 小悬浮窗的参数
	 */
	private WindowManager.LayoutParams mParams;

	/**
	 * 记录当前手指位置在屏幕上的横坐标值
	 */
	private float xInScreen;

	/**
	 * 记录当前手指位置在屏幕上的纵坐标值
	 */
	private float yInScreen;

	/**
	 * 记录手指按下时在屏幕上的横坐标的值
	 */
	private float xDownInScreen;

	/**
	 * 记录手指按下时在屏幕上的纵坐标的值
	 */
	private float yDownInScreen;

	/**
	 * 记录手指按下时在小悬浮窗的View上的横坐标的值
	 */
	private float xInView;

	/**
	 * 记录手指按下时在小悬浮窗的View上的纵坐标的值
	 */
	private float yInView;

	private static final int MOVE_LEFT_TIMER = 1;
	private static final int MOVE_RIGHT_TIMER = 2;
	private FloatWindowTimer moveLeftTimer;
	private FloatWindowTimer moveRightTimer;

	/**
	 * 小悬浮窗的布局
	 */
	private LinearLayout view;

	/**
	 * 小火箭
	 */
	private ImageView rocketImg;

	/**
	 * 记录小火箭的宽度
	 */
	private int rocketWidth;

	/**
	 * 记录小火箭的高度
	 */
	private int rocketHeight;

	/**
	 * 记录当前手指是否按下
	 */
	private boolean isPressed;

	/**
	 * 以下为圆球的设置，包括内容颜色等
	 */
	private RoundProgressBar progressBar;

	public void setProgress(int value){
		progressBar.setProgress(value);
	}
	private Context context;


	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if (msg.what == 0x0 ){
				rocketImg.setVisibility(View.GONE);
				MyWindowManager.createFreeWindow(context, msg.obj.toString());
				handler.sendEmptyMessageDelayed(0x1, 2000);
			}else if (msg.what == 0x1 ){
				MyWindowManager.removeFreeWindow(context);
				handler.sendEmptyMessage(0x2);
			}else if (msg.what == 0x2){
				view.setVisibility(View.VISIBLE);
			}
		}
	};

	public FloatWindowSmallView(Context context) {
		super(context);
		this.context = context;
		moveLeftTimer = new FloatWindowTimer(3000, 10, MOVE_LEFT_TIMER);
		moveRightTimer = new FloatWindowTimer(3000, 10, MOVE_RIGHT_TIMER);
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		LayoutInflater.from(context).inflate(R.layout.floating_ball_small, this);
		view = (LinearLayout) findViewById(R.id.small_window_layout);
		view.setOnHoverListener(this);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;
		progressBar = (RoundProgressBar) findViewById(R.id.xyf_progress);
		progressBar.setProgress(Utils.getUsedPercentIntValue(context));

		/** 小火箭 **/
		rocketImg = (ImageView) findViewById(R.id.rocket_img);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			rocketImg.getLayoutParams().width = dip2px(60f, context);
			rocketImg.getLayoutParams().height = dip2px(120f, context);
		}else {
			rocketImg.getLayoutParams().width = dip2px(66f, context);
			rocketImg.getLayoutParams().height = dip2px(132f, context);
		}
		rocketWidth = rocketImg.getLayoutParams().width;
		rocketHeight = rocketImg.getLayoutParams().height;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			rocketImg.getLayoutParams().width = dip2px(66f, context);
			rocketImg.getLayoutParams().height = dip2px(132f, context);
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
			rocketImg.getLayoutParams().width = dip2px(60f, context);
			rocketImg.getLayoutParams().height = dip2px(120f, context);
		}
		rocketWidth = rocketImg.getLayoutParams().width;
		rocketHeight = rocketImg.getLayoutParams().height;
		yInScreen = windowManager.getDefaultDisplay().getHeight() / 2;
		move();
	}

	private int dip2px(float dpValue, Context mContext){
		return (int) (dpValue * mContext.getResources().getDisplayMetrics().density + 0.5);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isPressed = true;
			moveLeftTimer.cancel();
			moveRightTimer.cancel();
			// 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
			xInView = event.getX();
			yInView = event.getY();
			xDownInScreen = event.getRawX();
			yDownInScreen = event.getRawY() - getStatusBarHeight();
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			break;
		case MotionEvent.ACTION_MOVE:
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			// 手指移动的时候更新小悬浮窗的位置
			updateViewStatus();
			updateViewPosition();
			break;
		case MotionEvent.ACTION_UP:
			isPressed = false;
			// 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
			if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
				openBigWindow();
				break;
			}

			if (MyWindowManager.isReadyToLaunch()) {
				launchRocket();
			} else {
				updateViewStatus();
				move();
			}
			break;
		default:
			break;
		}
		return true;
	}

	/** 自动靠边 **/
	private void move(){
		float posx = xInScreen - xInView + viewWidth / 2;
		if (posx <= windowManager.getDefaultDisplay().getWidth() / 2){
			moveLeftTimer.start();
		}
		//自动右移
		else {
			moveRightTimer.start();
		}
	}

	/**
	 * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
	 * 
	 * @param params
	 *            小悬浮窗的参数
	 */
	public void setParams(WindowManager.LayoutParams params) {
		mParams = params;
	}

	/**
	 * 更新小悬浮窗在屏幕中的位置。
	 */
	private void updateViewPosition() {
		mParams.x = (int) (xInScreen - xInView);
		mParams.y = (int) (yInScreen - yInView);
		windowManager.updateViewLayout(this, mParams);
		MyWindowManager.updateLauncher();
	}

	/**
	 * 用于发射小火箭�??
	 */
	private void launchRocket() {
		MyWindowManager.removeLauncher(getContext());

		new LaunchTask().execute();
	}

	/**
	 * 更新View的显示状态，判断是显示悬浮窗还是小火箭�??
	 */
	private void updateViewStatus() {
		if (isPressed && rocketImg.getVisibility() != View.VISIBLE) {
			mParams.width = rocketWidth;
			mParams.height = rocketHeight;
			windowManager.updateViewLayout(this, mParams);
			view.setVisibility(View.GONE);
			rocketImg.setVisibility(View.VISIBLE);
			MyWindowManager.createLauncher(getContext());
		} else if (!isPressed) {
			mParams.width = viewWidth;
			mParams.height = viewHeight;
			windowManager.updateViewLayout(this, mParams);
			view.setVisibility(View.VISIBLE);
			rocketImg.setVisibility(View.GONE);
			MyWindowManager.removeLauncher(getContext());
		}
	}

	/**
	 * 打开大悬浮窗，同时关闭小悬浮窗。
	 */
	private void openBigWindow() {
		MyWindowManager.createBigWindow(getContext());
		MyWindowManager.removeSmallWindow(getContext());
	}

	private void cancelTimers(){
		moveRightTimer.cancel();
		moveLeftTimer.cancel();
	}

	/**
	 * 用于获取状态栏的高度。
	 * 
	 * @return 返回状态栏高度的像素值。
	 */
	private int getStatusBarHeight() {
		if (statusBarHeight == 0) {
			try {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				statusBarHeight = getResources().getDimensionPixelSize(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusBarHeight;
	}

	@Override
	public boolean onHover(View view, MotionEvent motionEvent) {
		int action = motionEvent.getAction();
		switch (action) {
			case MotionEvent.ACTION_HOVER_ENTER:
				if (view.getId() == R.id.small_window_layout) {
					this.view.setBackground(getResources().getDrawable(R.drawable.xyf_bg_hover));
				}
				break;
			case MotionEvent.ACTION_HOVER_EXIT:
				if (view.getId() == R.id.small_window_layout) {
					this.view.setBackground(getResources().getDrawable(R.drawable.xyf_bg));
				}
				break;
			default:
				break;
		}
		return false;
	}

	class FloatWindowTimer extends CountDownTimer {
		private int timerType;

		public FloatWindowTimer(long millisInFuture, long countDownInterval, int timerType) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
			this.timerType = timerType;
		}

		@Override
		public void onFinish() {// 计时完毕时触发

		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			switch (timerType) {
				case MOVE_LEFT_TIMER:
					xInScreen -= 40;
					if (xInScreen <= xInView) {
						xInScreen = xInView;
						moveLeftTimer.cancel();
					}
					updateViewPosition();
					break;
				case MOVE_RIGHT_TIMER:
					xInScreen += 40;
					if (xInScreen >= windowManager.getDefaultDisplay().getWidth() - (viewWidth - xInView)) {
						xInScreen = windowManager.getDefaultDisplay().getWidth() - (viewWidth - xInView);
						moveRightTimer.cancel();
					}
					updateViewPosition();
					break;
			}
		}
	}


	/**
	 * �?始执行发射小火箭的任务�??
	 *
	 * @author guolin
	 */
	class LaunchTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// 在这里对小火箭的位置进行改变，从而产生火箭升空的效果
			int i = 0;
			int j = 5;
			while (mParams.y > -rocketHeight) {
				mParams.y = mParams.y - j;
				if (++i > 5){
					i = 0;
					j+=5;
				}
				publishProgress();
				try {
					Thread.sleep(8);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			windowManager.updateViewLayout(FloatWindowSmallView.this, mParams);
		}

		@Override
		protected void onPostExecute(Void result) {
			rocketImg.setVisibility(View.GONE);
			/** reset small position **/
			mParams.x = (int) (xDownInScreen - xInView);
			mParams.y = (int) (yDownInScreen - yInView);
			mParams.width = viewWidth;
			mParams.height = viewHeight;
			windowManager.updateViewLayout(FloatWindowSmallView.this, mParams);
			final long before = System.currentTimeMillis();
			Utils.freeMemory(context, new RequestCallback<String>() {
				@Override
				public void onSuccess(String response) {
					long after = System.currentTimeMillis();
					Message msg = new Message();
					msg.what = 0x0;
					msg.obj = response;
					if (after - before > 800){
						handler.sendMessage(msg);
					}else {
						handler.sendMessageDelayed(msg, 800);
					}
				}

				@Override
				public void onFailure(int errorCode, String errorReason) {

				}
			});
		}

	}

}

package com.andy.xyfloatingball.floating;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;

public class MyWindowManager {

	/** free **/
	private static FloatWindowFreeView freeWindow;
	/** free params **/
	private static LayoutParams freeParams;

	/** 火箭发射台的实例 **/
	private static RocketLauncher rocketLauncher;
	/** launcher参数 **/
	private static LayoutParams launcherParams;

	/** 小悬浮窗View的实例 **/
	private static FloatWindowSmallView smallWindow;
	/** 小悬浮窗View的参数 **/
	private static LayoutParams smallWindowParams;

	/** 大悬浮窗View的实例 **/
	private static FloatWindowBigView bigWindow;
	/** 大悬浮窗View的参数 **/
	private static LayoutParams bigWindowParams;

	/** 用于控制在屏幕上添加或移除悬浮窗 **/
	private static WindowManager mWindowManager;

	/** 用于获取手机可用内存 **/
	private static ActivityManager mActivityManager;

	/**
	 * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
	 * @param context
	 * 必须为应用程序的Context.
	 */
	public static void createFreeWindow(Context context, String totalFree) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
		int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
		if (freeWindow == null) {
			freeWindow = new FloatWindowFreeView(context);
			freeWindow.setFree(totalFree);
			if (freeParams == null) {
				freeParams = new LayoutParams();
				freeParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
				freeParams.format = PixelFormat.RGBA_8888;
				freeParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
						| LayoutParams.FLAG_NOT_FOCUSABLE;
				freeParams.gravity = Gravity.LEFT | Gravity.TOP;
				freeParams.width = FloatWindowFreeView.viewWidth;
				freeParams.height = FloatWindowFreeView.viewHeight;
			}
			/** 横竖屏切换 **/
			freeParams.x = screenWidth / 2 - freeParams.width / 2;
			freeParams.y = screenHeight / 5;

			freeWindow.setParams(freeParams);
			windowManager.addView(freeWindow, freeParams);
			freeWindow.show();
		}
	}

	/**
	 * 将小悬浮窗从屏幕上移除。
	 * @param context
	 * 必须为应用程序的Context.
	 */
	public static void removeFreeWindow(Context context) {
		if (freeWindow != null) {
			final WindowManager windowManager = getWindowManager(context);
			freeWindow.dimiss(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							windowManager.removeView(freeWindow);
							freeWindow = null;
						}
					}, 10);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}
			});
		}
	}

	private static boolean isLeft = false;
	private static int offset_y = -1;


	private static MySurfaceView waterWaveView;
	private static LayoutParams waterParams;

	/**
	 * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
	 * @param context
	 * 必须为应用程序的Context.
	 */
	public static void createSmallWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
		int screenHeight = context.getResources().getDisplayMetrics().heightPixels;


		if ( waterWaveView == null) {
			waterWaveView = new MySurfaceView(context);
			waterWaveView.setZOrderMediaOverlay(true);
			waterWaveView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
			if (waterParams == null) {
				waterParams = new LayoutParams();
				waterParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
				waterParams.format = PixelFormat.RGBA_8888;
				waterParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
						| LayoutParams.FLAG_NOT_FOCUSABLE;
				waterParams.gravity = Gravity.LEFT | Gravity.TOP;
				waterParams.width = screenWidth;
				waterParams.height = screenHeight;
			}
			/** 横竖屏切换 **/
			waterParams.x = 0;
			waterParams.y = 0;
//			windowManager.addView(waterWaveView, waterParams);
		}

//
//		if (smallWindow == null) {
//			smallWindow = new FloatWindowSmallView(context);
//			if (smallWindowParams == null) {
//				smallWindowParams = new LayoutParams();
//				smallWindowParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
//				smallWindowParams.format = PixelFormat.RGBA_8888;
//				smallWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
//						| LayoutParams.FLAG_NOT_FOCUSABLE;
//				smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
//				smallWindowParams.width = FloatWindowSmallView.viewWidth;
//				smallWindowParams.height = FloatWindowSmallView.viewHeight;
//			}
//			/** 横竖屏切换 **/
//			smallWindowParams.x = isLeft ? 0 : screenWidth;
//			smallWindowParams.y = offset_y == -1 ? screenHeight / 2 : offset_y;
//			smallWindow.setParams(smallWindowParams);
//			windowManager.addView(smallWindow, smallWindowParams);
//		}
	}

	/**
	 * 将小悬浮窗从屏幕上移除。
	 * @param context
	 * 必须为应用程序的Context.
	 */
	public static void removeSmallWindow(Context context) {
		if (smallWindow != null) {
			int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(smallWindow);
			isLeft = smallWindowParams.x < screenWidth / 2;
			offset_y = smallWindowParams.y;
			smallWindow = null;
		}
	}

	/**
	 * 创建一个大悬浮窗。位置为屏幕正中间。
	 * 
	 * @param context
	 * 必须为应用程序的Context.
	 */
	public static void createBigWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();
		if (bigWindow == null) {
			bigWindow = new FloatWindowBigView(context);
			if (bigWindowParams == null) {
				bigWindowParams = new LayoutParams();
				bigWindowParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
				bigWindowParams.format = PixelFormat.RGBA_8888;
				bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				bigWindowParams.width = FloatWindowBigView.viewWidth;
				bigWindowParams.height = FloatWindowBigView.viewHeight;
			}
			/** 横竖屏切换 **/
			bigWindowParams.x = screenWidth / 2 - FloatWindowBigView.viewWidth / 2;
			bigWindowParams.y = screenHeight / 2 - FloatWindowBigView.viewHeight / 2;
			windowManager.addView(bigWindow, bigWindowParams);
		}
	}

	/**
	 * 将大悬浮窗从屏幕上移除。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	public static void removeBigWindow(Context context) {
		if (bigWindow != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(bigWindow);
			bigWindow = null;
		}
	}

	/**
	 * 创建一个火箭发射台，位置为屏幕底部。
	 */
	public static void createLauncher(Context context) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();
		if (rocketLauncher == null) {
			rocketLauncher = new RocketLauncher(context);
			if (launcherParams == null) {
				launcherParams = new LayoutParams();
				launcherParams.type = LayoutParams.TYPE_PHONE;
				launcherParams.format = PixelFormat.RGBA_8888;
				launcherParams.gravity = Gravity.LEFT | Gravity.TOP;
			}
			launcherParams.width = RocketLauncher.width;
			launcherParams.height = RocketLauncher.height;
			/** 横竖屏切换 **/
			launcherParams.x = screenWidth / 2 - RocketLauncher.width / 2;
			launcherParams.y = screenHeight - RocketLauncher.height;
			windowManager.addView(rocketLauncher, launcherParams);
		}
	}

	/**
	 * 将火箭发射台从屏幕上移除。
	 */
	public static void removeLauncher(Context context) {
		if (rocketLauncher != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(rocketLauncher);
			rocketLauncher = null;
		}
	}

	/**
	 * 更新火箭发射台的显示状态。
	 */
	public static void updateLauncher() {
		if (rocketLauncher != null) {
			rocketLauncher.updateLauncherStatus(isReadyToLaunch());
		}
	}

	/**
	 * 判断小火箭是否准备好发射了。
	 *
	 * @return 当火箭被发到发射台上返回true，否则返回false。
	 */
	public static boolean isReadyToLaunch() {
//		if ((smallWindowParams.x > launcherParams.x
//				&& (smallWindowParams.x+ smallWindowParams.width) < (launcherParams.x + launcherParams.width))
//				&& (smallWindowParams.y + smallWindowParams.height > launcherParams.y)) {
//			return true;
//		}
		if (smallWindowParams.x > (launcherParams.x + launcherParams.width - launcherParams.width / 6.4) /2
				&& (smallWindowParams.x + smallWindowParams.width) < (launcherParams.x +launcherParams.width  + launcherParams.width / 6.4) /2
				&& (smallWindowParams.y + smallWindowParams.height > launcherParams.y)){
			return true;
		}

		return false;
	}

	/**
	 * 更新小悬浮窗的TextView上的数据，显示内存使用的百分比。
	 * 
	 * @param context
	 *            可传入应用程序上下文。
	 */
	public static void updateUsedPercent(Context context) {
		if (smallWindow != null) {
			smallWindow.setProgress(Utils.getUsedPercentIntValue(context));
		}
		if (bigWindow != null){
			bigWindow.setProgress(Utils.getUsedPercentIntValue(context));
		}
	}

	/**
	 * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
	 * 
	 * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
	 */
	public static boolean isWindowShowing() {
		return smallWindow != null || bigWindow != null;
	}

	/**
	 * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
	 */
	private static WindowManager getWindowManager(Context context) {
		if (mWindowManager == null) {
			mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		}
		return mWindowManager;
	}

	/**
	 * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回。否则返回当前已创建的ActivityManager。
	 * 
	 * @param context
	 *            可传入应用程序上下文。
	 * @return ActivityManager的实例，用于获取手机可用内存。
	 */
	private static ActivityManager getActivityManager(Context context) {
		if (mActivityManager == null) {
			mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		}
		return mActivityManager;
	}
}

package com.andy.xyfloatingball.floating;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andy.xyfloatingball.R;

public class RocketLauncher extends LinearLayout {

	/**
	 * 记录火箭发射台的宽度
	 */
	public static int width;

	/**
	 * 记录火箭发射台的高度
	 */
	public static int height;

	/**
	 * 火箭发射台的背景图片
	 */
	private ImageView launcherImg;

	public RocketLauncher(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.launcher, this);
		launcherImg = (ImageView) findViewById(R.id.launcher_img);
		launcherImg.getLayoutParams().width = context.getResources().getDisplayMetrics().widthPixels;
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			launcherImg.getLayoutParams().height = context.getResources().getDisplayMetrics().widthPixels / 4;
			launcherImg.setImageResource(R.drawable.launcher_platform_por_fire);
		}else {
			launcherImg.getLayoutParams().height = context.getResources().getDisplayMetrics().widthPixels / 5;
			launcherImg.setImageResource(R.drawable.launcher_platform_fire);

		}
		width = launcherImg.getLayoutParams().width;
		height = launcherImg.getLayoutParams().height;
	}


	/**
	 * 更新火箭发射台的显示状态。如果小火箭被拖到火箭发射台上，就显示发射。
	 */
	public void updateLauncherStatus(boolean isReadyToLaunch) {
		if (isReadyToLaunch) {
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
				launcherImg.setImageResource(R.drawable.launcher_platform_por);
			}else {
				launcherImg.setImageResource(R.drawable.launcher_platform);
			}
		} else {
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
				launcherImg.setImageResource(R.drawable.launcher_platform_por_fire);
			}else {
				launcherImg.setImageResource(R.drawable.launcher_platform_fire);
			}
		}
	}

}

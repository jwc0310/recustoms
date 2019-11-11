package com.andy.xyfloatingball.floating;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.andy.xyfloatingball.R;
import com.andy.xyfloatingball.XYFloatingUtils;
import com.andy.xyfloatingball.floating.RVAdater.OnItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FloatWindowBigView extends LinearLayout implements View.OnClickListener, View.OnHoverListener, OnItemClickListener{

	/** 记录大悬浮窗的宽度 **/
	public static int viewWidth;
	/** 记录大悬浮窗的高度 **/
	public static int viewHeight;

	private Context context;
	private int orientation;

	/**main page**/
	private View view;
	private LinearLayout view_content;
	private RelativeLayout xyf_content_top;
	private LinearLayout top_left, top_right;
	private LinearLayout xyf_content_top_ll;
	private RelativeLayout xyf_weather_rl;
	private TextView xyf_location, xyf_temp, xyf_air;
	private ImageView xyf_weather_pic;

	/** new ball **/
	private RelativeLayout acc_ball_rl;
	private View inner;

	private LinearLayout text_percent;
	private TextView tv_used;

	private LinearLayout acc_complete;

	private LinearLayout release_mb;
	private TextView tv_release;
	private ImageView image_rocket;

	/** hot apps **/
	private RelativeLayout xyf_hot_apps;

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if (msg.what == 0x0){
				tv_release.setText(msg.obj+"");
				runAnimation2(msg.obj+"");
			}else if (msg.what == 0x1){
				acc_complete.setVisibility(View.GONE);
				Animation alpha = AnimationUtils.loadAnimation(context, R.anim.alpha_acc);
				text_percent.setVisibility(View.VISIBLE);
				text_percent.startAnimation(alpha);
				findViewById(R.id.acc_ball_rl).setClickable(true);
			}else if (msg.what == 0x2){
				release_mb.setVisibility(View.GONE);
				showAgain();
			}else if (msg.what == 0x3){  //更新位置
				xyf_location.setText(msg.obj+"");
			}else if (msg.what == 0x4){ //更新天气
				WeatherBean weatherBean = (WeatherBean)msg.obj;
				if (weatherBean == null){
					return;
				}
				xyf_temp.setText(weatherBean.getWeather()+weatherBean.getTempLow()+"~"+weatherBean.getTempHigh()+"℃");
				xyf_air.setText(weatherBean.getPm()+" "+weatherBean.getAirQuality());

				Glide.with(getContext())
						.load(weatherBean.getUrl())
						.placeholder(R.drawable.app_loading_icon)
						.into(xyf_weather_pic);
			}else if (msg.what == 0x5){
				change();
			}
		}
	};

	public FloatWindowBigView(final Context context) {
		super(context);
		this.context = context;
		filter();
		init();
	}
	private List<AppBean.AppInfo> recomList;

	private void filter(){
		recomList = new ArrayList<>();
		recomList.clear();
		for (AppBean.AppInfo bean : Utils.appInfoList){
			if (!Utils.isApkInstalled(context, bean.getPackageName())){
				recomList.add(bean);
			}
		}
	}

	private void init(){
		LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
		view = findViewById(R.id.big_window_layout);
		view_content = (LinearLayout) findViewById(R.id.small_window_layout);

		DisplayMetrics px = context.getResources().getDisplayMetrics();
		orientation = getResources().getConfiguration().orientation;
		view_content.getLayoutParams().width = 480 * px.densityDpi / 192;
		view_content.getLayoutParams().height = 416 * px.densityDpi / 192;

		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;

		/** top **/
		xyf_content_top = (RelativeLayout) findViewById(R.id.xyf_content_top);
		xyf_content_top.getLayoutParams().height = 140 * px.densityDpi / 192;
		xyf_content_top_ll = (LinearLayout) findViewById(R.id.xyf_content_top_rl);
		xyf_content_top_ll.getLayoutParams().height = 100 * px.densityDpi / 192;
		top_left = (LinearLayout) findViewById(R.id.xyf_top_left);
		top_left.setOnClickListener(this);
		top_left.setOnHoverListener(this);
		top_left.getChildAt(0).getLayoutParams().height = 30 * px.densityDpi / 192;
		top_left.getChildAt(0).getLayoutParams().width = 30 * px.densityDpi / 192;
		((TextView)top_left.getChildAt(1)).setTextSize(12 * px.densityDpi / 192);
		top_right = (LinearLayout) findViewById(R.id.xyf_top_right);
		top_right.setOnClickListener(this);
		top_right.setOnHoverListener(this);
		top_right.getChildAt(0).getLayoutParams().height = 30 * px.densityDpi / 192;
		top_right.getChildAt(0).getLayoutParams().width = 30 * px.densityDpi / 192;
		((TextView)top_right.getChildAt(1)).setTextSize(12 * px.densityDpi / 192);

		//new ball
		acc_ball_rl = (RelativeLayout) findViewById(R.id.acc_ball_rl);
		acc_ball_rl.getLayoutParams().width = 120 * px.densityDpi / 192;
		acc_ball_rl.getLayoutParams().height = 120 * px.densityDpi / 192;
		acc_ball_rl.setOnClickListener(this);
		inner = findViewById(R.id.test_inner);
		text_percent = (LinearLayout) findViewById(R.id.text_percent);
		tv_used = (TextView) findViewById(R.id.used_number);
		tv_used.setTextSize(30 * px.densityDpi / 192);
		tv_used.setText(Utils.getUsedPercentIntValue(context)+"");
		((TextView) findViewById(R.id.unit)).setTextSize(20 * px.densityDpi / 192);
		((TextView) findViewById(R.id.content)).setTextSize(10* px.densityDpi / 192);
		text_percent.setVisibility(View.VISIBLE);

		acc_complete = (LinearLayout) findViewById(R.id.acc_complete);
		acc_complete.getChildAt(0).getLayoutParams().width = 50 * px.densityDpi / 192;
		acc_complete.getChildAt(0).getLayoutParams().width = 30 * px.densityDpi / 192;
		((TextView)acc_complete.getChildAt(1)).setTextSize(10 * px.densityDpi / 192);
		((LayoutParams)acc_complete.getChildAt(1).getLayoutParams()).setMargins(0, 10 * px.densityDpi / 192, 0, 0);
		acc_complete.setVisibility(View.GONE);

		release_mb = (LinearLayout) findViewById(R.id.release_mb);
		tv_release = (TextView)findViewById(R.id.release_number);
		tv_release.setTextSize(30 * px.densityDpi / 192);
		((TextView) findViewById(R.id.release_unit)).setTextSize(15 * px.densityDpi / 192);
		((TextView) findViewById(R.id.release_content)).setTextSize(10* px.densityDpi / 192);
		release_mb.setVisibility(View.GONE);

		image_rocket = (ImageView) findViewById(R.id.image_rocket);
		image_rocket.getLayoutParams().width = 45 * px.densityDpi / 192;
		image_rocket.getLayoutParams().height = 90 * px.densityDpi / 192;
		image_rocket.setVisibility(View.GONE);

		/** bottom  天气 **/
		xyf_weather_rl = (RelativeLayout) findViewById(R.id.xyf_weather_rl);
		xyf_weather_rl.getLayoutParams().height = 60 * px.densityDpi / 192;
		xyf_location = (TextView) findViewById(R.id.xyf_location);
		xyf_temp = (TextView) findViewById(R.id.xyf_temp);
		xyf_weather_pic = (ImageView) findViewById(R.id.xyf_weather_pic);
		xyf_weather_pic.getLayoutParams().height = 30 * px.densityDpi / 192;
		xyf_weather_pic.getLayoutParams().width = 30 * px.densityDpi / 192;
		xyf_air = (TextView) findViewById(R.id.xyf_air);
		xyf_location.setTextSize(12 * px.densityDpi / 192);
		xyf_temp.setTextSize(12 * px.densityDpi / 192);
		xyf_air.setTextSize(10 * px.densityDpi / 192);
		//initLocation();
		//initWeather();

		/** middle 中间 **/
		xyf_hot_apps = (RelativeLayout) findViewById(R.id.xyf_hot_apps);
		((TextView)xyf_hot_apps.getChildAt(0)).setTextSize(12 * px.densityDpi / 192);
		TextView change = (TextView) xyf_hot_apps.getChildAt(1);
		change.setTextSize(12 * px.densityDpi / 192);
		change.setOnClickListener(this);
		change.setOnHoverListener(this);
		xyf_hot_apps.getChildAt(2).getLayoutParams().height = 15 * px.densityDpi / 192;
		xyf_hot_apps.getChildAt(2).getLayoutParams().width = 15 * px.densityDpi / 192;
		xyf_hot_apps.getChildAt(2).setOnClickListener(this);
		xyf_hot_apps.getChildAt(2).setOnHoverListener(this);

		/** 事件 **/
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				MyWindowManager.removeBigWindow(context);
				MyWindowManager.createSmallWindow(context);
				return false;
			}
		});
		view_content.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				return true;
			}
		});

		initRecycleView();

	}
	private RecyclerView recyclerView;
	private RVAdater adater;
	private List<AppBean.AppInfo> list;
	private int mark;
	private TextView recom_end;
	private void initRecycleView(){
		list = new CopyOnWriteArrayList<>();
		mark = 0;
		adater = new RVAdater(getContext(), list);
		adater.setOnItemClickListener(this);
		recom_end = (TextView) findViewById(R.id.recom_end);
		recom_end.setVisibility(View.GONE);
		recyclerView = (RecyclerView) findViewById(R.id.recycle);
		recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
		recyclerView.setAdapter(adater);
		getHotGames();
	}
	private void getHotGames(){
		if (recomList.size() != 0){
			handler.sendEmptyMessage(0x5);
			return;
		}
		HttpUtils.getHotGames(0, new RequestCallback<List<AppBean.AppInfo>>() {
			@Override
			public void onSuccess(List<AppBean.AppInfo> response) {
				Utils.appInfoList.clear();
				Utils.appInfoList.addAll(response);
				filter();
				handler.sendEmptyMessage(0x5);
			}

			@Override
			public void onFailure(int errorCode, String errorReason) {

			}
		});
	}

	/** 换一批 **/
	private void change(){
		list.clear();
		if (recomList.size() == 0){
			recom_end.setVisibility(View.VISIBLE);
			recyclerView.setVisibility(View.GONE);
			return;
		}
		if (recomList.size() < 5){
			list.addAll(recomList);
			mark = recomList.size();
		}else {
			for(int i = 0; i < 5; i++){
				if (mark >= recomList.size()){
					mark = 0;
				}
				list.add(recomList.get(mark));
				mark++;
			}
		}
		adater.notifyDataSetChanged();

	}

	private void initWeather(){
		final Message msg = new Message();
		msg.what = 0x4;
		if (Utils.weatherBean != null){
			msg.obj = Utils.weatherBean;
			handler.sendMessage(msg);
		}else {
			Utils.weatherBean = new WeatherBean();
			HttpUtils.getWeatherData(new RequestCallback<String>() {
				@Override
				public void onSuccess(String response) {
					try {
						JSONObject jsonObject = new JSONObject(response);
						Utils.weatherBean.setWeather(jsonObject.optString("weather"));
						Utils.weatherBean.setTempLow(jsonObject.optString("temperature_low"));
						Utils.weatherBean.setTempHigh(jsonObject.optString("temperature_high"));
						Utils.weatherBean.setAirQuality(jsonObject.optString("air_quality"));
						Utils.weatherBean.setPm(jsonObject.optString("aqi"));
						Utils.weatherBean.setUrl(jsonObject.optString("url"));
						msg.obj = Utils.weatherBean;
						handler.sendMessage(msg);
					} catch (JSONException e) {
						e.printStackTrace();
						Utils.weatherBean = null;
					}
				}

				@Override
				public void onFailure(int errorCode, String errorReason) {
					Utils.weatherBean = null;
				}
			});
		}
	}
	//位置
	private void initLocation(){
		if (!Utils.city.equals("")&&Utils.city.length() != 0){
			xyf_location.setText(Utils.city);
			return;
		}
		HttpUtils.asyncGetCityName(new RequestCallback<String>() {
			@Override
			public void onSuccess(final String response) {
				Message msg = new Message();
				msg.what = 0x3;
				msg.obj = response;
				handler.sendMessage(msg);
			}

			@Override
			public void onFailure(int errorCode, String errorReason) {

			}
		});
	}

	private static final int MARKET_MAIN = 0x1;
	private static final int MARKET_MANAGER = 0x2;

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (R.id.xyf_hot_apps_change == id || R.id.xyf_hot_apps_icon == id){
			change();
		}else if (R.id.acc_ball_rl == id){
			acc_ball_rl.setClickable(false);
			runAnimation1();
		}else if (R.id.xyf_top_left == id){
			launchMarket(MARKET_MANAGER);
		}else if (R.id.xyf_top_right == id){
			launchMarket(MARKET_MAIN);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		init();
	}

	/** 启动market 标识 **/
	private void launchMarket(int flag){
		String packageName = "com.andy.market";
		if (Utils.usingApp(context, packageName)){
			return;
		}
		PackageManager packageManager = context.getPackageManager();
		Intent intent = packageManager.getLaunchIntentForPackage(packageName);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
		MyWindowManager.removeBigWindow(context);
		intent.putExtra("launcherPage", flag);
		context.startActivity(intent);
	}

	/** 更新标识 **/
	private boolean canUpdate = true;

	public void setProgress(int value){
		if (canUpdate){
			tv_used.setText(value+"");
		}
	}

	private void runAnimation1(){
		/** percent dimiss, rocket show from bottom, then, change background and rolate**/
		text_percent.setVisibility(View.GONE);
		Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.text_scale);
		scaleAnimation.setInterpolator(new AccelerateInterpolator());
		text_percent.startAnimation(scaleAnimation);
		image_rocket.setVisibility(View.VISIBLE);
		Animation fromBottomAnimation = AnimationUtils.loadAnimation(context, R.anim.from_bottom);
		fromBottomAnimation.setStartOffset(310);
		fromBottomAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				inner.setBackground(getResources().getDrawable(R.drawable.xyf_rolate));
				Animation rolate2 = AnimationUtils.loadAnimation(context, R.anim.dashed_rolate);
				rolate2.setInterpolator(new LinearInterpolator());
				rolate2.setStartOffset(10);
				inner.startAnimation(rolate2);
				final long before = System.currentTimeMillis();
				Utils.freeMemory(context, new RequestCallback<String>() {
					@Override
					public void onSuccess(String response) {
						long after = System.currentTimeMillis();
						Message msg = new Message();
						msg.what = 0x0;
						msg.obj = response;
						handler.sendMessageDelayed(msg, (after - before > 1200)?0:1200);
					}

					@Override
					public void onFailure(int errorCode, String errorReason) {

					}
				});
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		image_rocket.startAnimation(fromBottomAnimation);

	}

	private void runAnimation2(final String free){
		/** rocket dimiss,  change background, then, check count**/
		image_rocket.setVisibility(View.GONE);
		image_rocket.clearAnimation();
		Animation toUpAnimation = AnimationUtils.loadAnimation(context, R.anim.to_up);
		toUpAnimation.setStartOffset(20);
		toUpAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				inner.clearAnimation();
				inner.setBackground(getResources().getDrawable(R.drawable.xy_flow_bg_in));
				if (!free.equals("0")) {
					Animation animation1 = new ScaleAnimation(1.3f, 1.0f, 1.3f, 1.0f,
							Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
					animation1.setInterpolator(new AnticipateOvershootInterpolator());
					Animation animation2 = new AlphaAnimation(0.0f, 1.0f);
					AnimationSet animationSet = new AnimationSet(true);
					animationSet.addAnimation(animation1);
					animationSet.addAnimation(animation2);
					animationSet.setDuration(300);
					animationSet.setStartOffset(5);
					animationSet.setAnimationListener(new Animation.AnimationListener() {
						@Override
						public void onAnimationStart(Animation animation) {

						}

						@Override
						public void onAnimationEnd(Animation animation) {
							handler.sendEmptyMessageDelayed(0x2, 1000);

						}

						@Override
						public void onAnimationRepeat(Animation animation) {

						}
					});
					release_mb.setVisibility(View.VISIBLE);
					release_mb.startAnimation(animationSet);
				} else {
					showAgain();
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		image_rocket.startAnimation(toUpAnimation);
	}

	private void showAgain(){
		acc_complete.setVisibility(View.VISIBLE);
		Animation alpha = AnimationUtils.loadAnimation(context, R.anim.alpha_acc);
		alpha.setStartOffset(10);
		alpha.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				handler.sendEmptyMessageDelayed(0x1, 1000);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		acc_complete.startAnimation(alpha);
	}

	@Override
	public boolean onHover(View view, MotionEvent motionEvent) {
		int action = motionEvent.getAction();
		switch (action) {
			case MotionEvent.ACTION_HOVER_ENTER:
				if (view.getId() == R.id.xyf_top_left) {
					top_left.getChildAt(0).setBackgroundResource(R.drawable.xyf_manager_hover);
					((TextView)top_left.getChildAt(1)).setTextColor(getResources().getColor(R.color.text_hover));
				}else if (view.getId() == R.id.xyf_top_right){
					top_right.getChildAt(0).setBackgroundResource(R.drawable.xyf_market_hover);
					((TextView)top_right.getChildAt(1)).setTextColor(getResources().getColor(R.color.text_hover));
				}else if (view.getId() == R.id.xyf_hot_apps_icon || view.getId() == R.id.xyf_hot_apps_change){
					((ImageView)xyf_hot_apps.getChildAt(2)).setImageResource(R.drawable.xyf_change_hover);
					((TextView)xyf_hot_apps.getChildAt(1)).setTextColor(getResources().getColor(R.color.text_hover_2));
				}
				break;
			case MotionEvent.ACTION_HOVER_EXIT:
				if (view.getId() == R.id.xyf_top_left) {
					top_left.getChildAt(0).setBackgroundResource(R.drawable.xyf_manager);
					((TextView)top_left.getChildAt(1)).setTextColor(getResources().getColor(R.color.white));
				}else if (view.getId() == R.id.xyf_top_right){
					top_right.getChildAt(0).setBackgroundResource(R.drawable.xyf_market);
					((TextView)top_right.getChildAt(1)).setTextColor(getResources().getColor(R.color.white));
				}else if (view.getId() == R.id.xyf_hot_apps_icon || view.getId() == R.id.xyf_hot_apps_change){
					((TextView)xyf_hot_apps.getChildAt(1)).setTextColor(getResources().getColor(R.color.xyf_font_color_gray));
					((ImageView)xyf_hot_apps.getChildAt(2)).setImageResource(R.drawable.xyf_change);
				}
				break;
			default:
				break;
		}
		return false;
	}

	@Override
	public void onItemClick(View v, int position) {
		download(v, position);
		if (XYFloatingUtils.downloadInterface != null){
			XYFloatingUtils.downloadInterface.download(list.get(position), "floating");
		}
	}


	/** 下载 **/
	private synchronized void download(View v, final int position){
		/** []分别为：src坐标, target坐标, 悬浮窗坐标  **/
		int[] loc_window = new int[2];
		v.getLocationInWindow(loc_window);
		int[] loc_target = new int[2];
		acc_ball_rl.getLocationInWindow(loc_target);

		Point startPosition = new Point(loc_window[0], loc_window[1]);	//src
		Point endPosition = new Point(loc_target[0], loc_target[1] - v.getHeight() * 3 / 4);	//target

		//create animation image view
		final MoveImageView moveImageView = new MoveImageView(context);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(v.getWidth(), v.getHeight());
		moveImageView.setLayoutParams(lp);
		moveImageView.setStartPosition(startPosition);
		moveImageView.setEndPosition(endPosition);

		Glide.with(context)
				.load(list.get(position).getIconUrl())
				.placeholder(R.drawable.app_loading_icon)
				.into(moveImageView);

		ViewGroup rootView = (ViewGroup) view;
		rootView.addView(moveImageView);
		//add to parent view
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				//start animation
				moveImageView.startBeizerAnimation();
				if (recomList.size() > 5) {
					list.add(recomList.get(mark %= recomList.size()));
					if (mark < 5){  //匹配再次循环
						mark ++;
					}
				}
				if (position < list.size()){
					AppBean.AppInfo bean = list.remove(position);
					recomList.remove(bean);
					adater.notifyDataSetChanged();
				}

				if (recomList.size() == 0){
					recom_end.setVisibility(View.VISIBLE);
					recyclerView.setVisibility(View.GONE);
				}
			}
		}, 500);

	}
}

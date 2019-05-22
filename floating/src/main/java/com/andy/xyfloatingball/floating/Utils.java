package com.andy.xyfloatingball.floating;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * utils about memory and other utils
 * Created by Andy on 2016/11/25.
 */
public class Utils {
    /** 城市 **/
    public static String city = "";
    /** 推荐下载应用 **/
    public static List<AppBean.AppInfo> appInfoList = new ArrayList<>();
    public static int index = 0;
    /** 天气状况 **/
    public static WeatherBean weatherBean;
    public static void getWeatherBean(){
        weatherBean = new WeatherBean();
        HttpUtils.getWeatherData(new RequestCallback<String>() {
            @Override
            public void onSuccess(String response) {
                try {
                    Log.e("Andy", response);
                    JSONObject jsonObject = new JSONObject(response);
                    weatherBean.setWeather(jsonObject.optString("weather"));
                    weatherBean.setTempLow(jsonObject.optString("temperature_low"));
                    weatherBean.setTempHigh(jsonObject.optString("temperature_high"));
                    weatherBean.setAirQuality(jsonObject.optString("air_quality"));
                    weatherBean.setPm(jsonObject.optString("aqi"));
                    weatherBean.setUrl(jsonObject.optString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    weatherBean = null;
                }
            }

            @Override
            public void onFailure(int errorCode, String errorReason) {
                    weatherBean = null;
            }
        });
    }

    static public String getandyChannel() {
        String value = "";
        String defaultvalue = "7c8a454b";
        String key = "andy.channel";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, defaultvalue));
            if (!value.matches("([A-Z]|[a-z]|[0-9]|[:]){0,}")) {
                value = defaultvalue;
            }
        } catch (Exception e) {
        }
        return value;
    }

    /**
     * 获取相应的32位md5字符串
     */
    public final static String get32MD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }

    /**
     * getprop 值
     */
    static public String getandyLaLo(String laorlo) {
        String value = "";
        String defaultvalue = "123";
        String key = laorlo;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, defaultvalue));
        } catch (Exception e) {
        }
        return value;
    }

    public static boolean isApkInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            if (info != null) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return false;
    }


    /**
     * 启动应用
     */
    public static Boolean openApk(Context context, String package_name) {
        File package_file = new File("/data/data/" + package_name);
        if (package_file.exists()) {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(package_name);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else {
            Toast.makeText(context, "你确定有安装我吗？", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 卸载应用
     */
    public static void unInstall(Context context, String package_name){
        Uri packageURI = Uri.parse("package:"+package_name);
        Intent intent = new Intent(Intent. ACTION_DELETE, packageURI);
        context.startActivity(intent);
    }
    /**
     * 判断当前显示应用
     */
    public static boolean usingApp(Context context, String packageName){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String topPackageName = "";
        if (Build.VERSION.SDK_INT > 20){
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            if (null != tasks && tasks.size() > 0) {
                topPackageName = tasks.get(0).processName;
            }
        }else {
            List<ActivityManager.RunningTaskInfo> taskInfos = am.getRunningTasks(1);
            if (null != taskInfos || taskInfos.size() > 0){
                topPackageName = taskInfos.get(0).topActivity.getPackageName();
            }
        }
        if (topPackageName.equals(packageName)){
            return true;
        }

        return false;
    }

    private static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    //show main page or setting pag
    public static boolean show_ball = true;

    /**
     * 得到最近使用过的app
     *
     * 核心方法，加载最近启动的应用程序 注意：这里我们取出的最近任务为 MAX_RECENT_TASKS +
     * 1个，因为有可能最近任务中包好Launcher2。 这样可以保证我们展示出来的 最近任务 为 MAX_RECENT_TASKS 个
     * 通过以下步骤，可以获得近期任务列表，并将其存放在了appInfos这个list中，接下来就是展示这个list的工作了。
     */
    public static List<HashMap<String, Object>> getHistoryApps(Context ctx, int appNumber) {
        List<HashMap<String, Object>> appInfos = new ArrayList<>();
        int MAX_RECENT_TASKS = appNumber; // allow for some discards
        int repeatCount = appNumber;// 保证上面两个值相等,设定存放的程序个数

        /* 每次加载必须清空list中的内容 */
        appInfos.clear();

        // 得到包管理器和activity管理器
        final Context context = ctx.getApplicationContext();
        final PackageManager pm = context.getPackageManager();
        final ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        // 从ActivityManager中取出用户最近launch过的 MAX_RECENT_TASKS + 1 个，以从早到晚的时间排序，
        // 注意这个 0x0002,它的值在launcher中是用ActivityManager.RECENT_IGNORE_UNAVAILABLE
        // 但是这是一个隐藏域，因此我把它的值直接拷贝到这里
        final List<ActivityManager.RecentTaskInfo> recentTasks;
        if (Build.VERSION.SDK_INT > 20){
            List<ActivityManager.AppTask> taskList = am.getAppTasks();
            recentTasks = new ArrayList<>();
            for (ActivityManager.AppTask task : taskList){
                recentTasks.add(task.getTaskInfo());
            }
        }else {
            recentTasks = am.getRecentTasks(MAX_RECENT_TASKS + 1, 0x0002);
        }


        // 这个activity的信息是我们的launcher
        ActivityInfo homeInfo = new Intent(Intent.ACTION_MAIN).addCategory(
                Intent.CATEGORY_HOME).resolveActivityInfo(pm, 0);
        int numTasks = recentTasks.size();
        for (int i = 0; i < numTasks && (i < MAX_RECENT_TASKS); i++) {
            HashMap<String, Object> singleAppInfo = new HashMap<>();// 单个启动过的应用程序的信息
            final ActivityManager.RecentTaskInfo info = recentTasks.get(i);

            Intent intent = new Intent(info.baseIntent);
            if (info.origActivity != null) {
                intent.setComponent(info.origActivity);
            }
            /**
             * 如果找到是launcher，直接continue，后面的appInfos.add操作就不会发生了
             */
            if (homeInfo != null) {
                if (homeInfo.packageName.equals(intent.getComponent()
                        .getPackageName())
                        && homeInfo.name.equals(intent.getComponent()
                        .getClassName())) {
                    MAX_RECENT_TASKS = MAX_RECENT_TASKS + 1;
                    continue;
                }
            }
            // 设置intent的启动方式为 创建新task()【并不一定会创建】
            intent.setFlags((intent.getFlags() & ~Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            // 获取指定应用程序activity的信息(按我的理解是：某一个应用程序的最后一个在前台出现过的activity。)
            final ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
            if (resolveInfo != null) {
                final ActivityInfo activityInfo = resolveInfo.activityInfo;
                final String title = activityInfo.loadLabel(pm).toString();
                Drawable icon = activityInfo.loadIcon(pm);

                if (title != null && title.length() > 0 && icon != null) {
                    singleAppInfo.put("title", title);
                    singleAppInfo.put("icon", icon);
                    singleAppInfo.put("tag", intent);
                    singleAppInfo.put("packageName", activityInfo.packageName);
                    appInfos.add(singleAppInfo);
                }
            }
        }
        MAX_RECENT_TASKS = repeatCount;
        return appInfos;
    }

    /**
     * 计算已使用内存的百分比，并返回。
     *
     * @param context
     *            可传入应用程序上下文。
     * @return 已使用内存的百分比，以字符串形式返回。
     */
    public static String getUsedPercentValue(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
            long availableSize = getAvailableMemory(context) / 1024;
            int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
            return percent + "%";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "悬浮窗";
    }

    public static int getUsedPercentIntValue(Context context){
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
            long availableSize = getAvailableMemory(context) / 1024;
            return (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 50;
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context
     *            可传入应用程序上下文。
     * @return 当前可用内存。
     */
    private static long getAvailableMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem;
    }

    //获取可用内存大小
    private static long getAvailMemory(Context context) {
        // 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存
        //return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
        //Log.d("Andy", "可用内存---->>>" + mi.availMem / (1024 * 1024));
        return mi.availMem / (1024 * 1024);
    }

    public static void freeMemory(final Context context, final RequestCallback callback){

        new Thread(new Runnable() {
            @Override
            public void run() {
                ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
                List<ActivityManager.RunningServiceInfo> serviceInfos = am.getRunningServices(100);

                long beforeMem = getAvailMemory(context);

                int count = 0;
                if (infoList != null) {
                    for (int i = 0; i < infoList.size(); i++){
                        ActivityManager.RunningAppProcessInfo appProcessInfo = infoList.get(i);
                        // 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
                        // 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
                        if (appProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE && !appProcessInfo.processName.equals("com.andy.xyfloatingball")){
                            String[] pkgList = appProcessInfo.pkgList;
                            for (int j = 0; j < pkgList.length; j++){
                                //Log.d("Andy", "It will be killed, package name : " + pkgList[j]);
                                am.killBackgroundProcesses(pkgList[j]);
                                count++;
                            }
                        }
                    }
                }

                long afterMem = getAvailMemory(context);
                callback.onSuccess((int)(afterMem - beforeMem)+"");

            }
        }).start();

    }
}

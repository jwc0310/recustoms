package com.andy.recustomviews.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * 获取设备信息的工具类
 * Created by Andy on 2016/7/13.
 */
public class DevicesInfoUtils {

    /**
     * 屏宽、高、密度
     */
    private static int screenWidth = -1;
    private static int screenHeight = -1;
    private static float screenDensity = -1;

    static Pattern ipPattern = Pattern.compile("((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)(\\.((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)){3}");

    /**
     * 屏宽
     */
    public static int getScreenWidth(Context context){
        getDisplayMetrics(context);
        return screenWidth;
    }

    /**
     * 屏高
     */
    public static int getScreenHeight(Context context) {
        getDisplayMetrics(context);
        return screenHeight;
    }

    /**
     * 屏密度
     */
    public static float getScreenDensity(Context context){
        if (screenDensity == -1){
            getDisplayMetrics(context);
        }
        return screenDensity;
    }

    /**
     * 屏宽
     */
    public static int dip2px(float dpValue, Context mContext){
        if ( screenDensity == -1){
            getDisplayMetrics(mContext);
        }
        return (int) (dpValue * screenDensity + 0.5);
    }

    private static void getDisplayMetrics(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        screenDensity = displayMetrics.density;
    }

    /**
     * ip地址 手机卡上网10.31.175.64 wifi上网fe80::7451:baff:fe3a:ae38%p2p0
     *
     * @return
     */
    private static String intoIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

    public static String getIP(Context ctx) {

        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()) {
            WifiInfo wifiInfo = wifi.getConnectionInfo();
            return intoIp(wifiInfo.getIpAddress());

        } else {

            try {
                boolean bool;
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        String str = inetAddress.getHostAddress().toString();
                        if (!inetAddress.isLoopbackAddress()) {
                            bool = ipPattern.matcher(str).matches();
                            if (bool){
                                return str;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                return "127.0.0.1";
            }
        }
        return "127.0.0.1";
    }

    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * uuid
     * @param ctx
     * @return
     */
    public static String getAndroidId(Context ctx){
        if (ctx == null){
            return "";
        }
        return Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获得wlan的mac地址
     */
    public static String getWlanMac(Context ctx) {
        String m_szWLANMAC = "00:12:34:56:78:90";
        if (ctx == null){
            return m_szWLANMAC;
        }
        WifiManager wm = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
        return  m_szWLANMAC;
    }

    /**
     * 获得IMEI, IMSI
     * @param ctx
     * @return
     */
    public static String getIMEI(Context ctx) {
        String imei = "";
        imei = ((TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

        if (imei.equals("")){
            return "未知";
        }
        return imei;
    }
    public static String getIMSI(Context ctx){
        String imsi = "";
        imsi = ((TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();

        if (imsi.equals("")){
            return "未知";
        }
        return imsi;

    }

    public static void getSimInfo(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
//        XYStatistics.IMEI = tm.getDeviceId();// 获取智能设备唯一编号
        String tmp = tm.getSubscriberId();
        if (tmp == null || tmp.equals("") || tmp.length() == 0){
            return;
        }
//        XYStatistics.IMSI = tmp;

        if (tmp.startsWith("46000") || tmp.startsWith("46002") || tmp.startsWith("46007")){
//            XYStatistics.SIM_TYPE = "中国移动";
        }else if (tmp.startsWith("46001")){
//            XYStatistics.SIM_TYPE = "中国联通";
        }else if (tmp.startsWith("46003")){
//            XYStatistics.SIM_TYPE = "中国电信";
        }else {
//            XYStatistics.SIM_TYPE = "未知";
        }

    }

    /**
     * Build信息
     */
    public static void getBuildInfo(){
        try {
//            XYStatistics.MOBILE_BRAND = Build.BRAND;
//            XYStatistics.MOBILE_OS = Build.VERSION.RELEASE;
        }catch(Exception e){

        }
    }

    /**
     * 机型
     *
     * @return
     */
    public static String getBuildBrand() {
        try {
            return Build.BRAND;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 机型版本
     *
     * @return
     */
    public static String getBuildModel() {
        try {
            return Build.MODEL;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 安卓系统版本
     * @return
     */
    public static String getOsVersion() {
        try {
            return Build.VERSION.RELEASE;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 分辨率宽高 720*1280
     * @param ctx
     * @return
     */
    public static String getResolution(Context ctx) {

        getDisplayMetrics(ctx);

        return screenWidth + " x " + screenHeight;
    }

    public static String getDeviceInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("主板： "+ Build.BOARD+"\n");
        sb.append("系统启动程序版本号： " + Build.BOOTLOADER+"\n");
        sb.append("系统定制商：" + Build.BRAND+"\n");
        sb.append("cpu指令集： " + Build.CPU_ABI+"\n");
        sb.append("cpu指令集2 "+ Build.CPU_ABI2+"\n");
        sb.append("设置参数： "+ Build.DEVICE+"\n");
        sb.append("显示屏参数：" + Build.DISPLAY+"\n");
        sb.append("无线电固件版本：" + Build.getRadioVersion()+"\n");
        sb.append("硬件识别码：" + Build.FINGERPRINT+"\n");
        sb.append("硬件名称：" + Build.HARDWARE+"\n");
        sb.append("HOST: " + Build.HOST+"\n");
        sb.append("修订版本列表：" + Build.ID+"\n");
        sb.append("硬件制造商：" + Build.MANUFACTURER+"\n");
        sb.append("版本：" + Build.MODEL+"\n");
        sb.append("硬件序列号：" + Build.SERIAL+"\n");
        sb.append("手机制造商：" + Build.PRODUCT+"\n");
        sb.append("描述Build的标签：" + Build.TAGS+"\n");
        sb.append("TIME: " + Build.TIME+"\n");
        sb.append("builder类型：" + Build.TYPE+"\n");
        sb.append("USER: " + Build.USER+"\n");
        return sb.toString();
    }

    /**
     * 判断手机是否有SD卡。
     *
     * @return 有SD卡返回true，没有返回false。
     */
    public static boolean hasSDCard() {

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
//            XYStatistics.HAVE_SD = 1;
            return true;
        }
//        XYStatistics.HAVE_SD = 0;
        return false;

    }

    /**
     * 版本名
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * 版本号
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int tmp = getPackageInfo(context).versionCode;
//        XYStatistics.APP_VERSION = tmp+"";
        return tmp;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

//            XYStatistics.PACKAGE_NAME = pi.packageName;
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * <功能简述> 应用缓存目录
     *
     * @param context
     * @return
     */
    public static File getDefaultDownloadDir(Activity context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return context.getExternalCacheDir();
        }
        return context.getCacheDir();
    }

    public static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if (item.isDirectory()) {
                    deleteFilesByDirectory(item);
                } else {
                    item.delete();
                }
            }
        }
    }

    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

}

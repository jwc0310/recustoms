package com.andy.recustomviews.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.andy.recustomviews.constant.Constant;
import com.andy.recustomviews.proj_2.GreenDaoManager;
import com.andy.xyfloatingball.XYFloatingUtils;
import com.andy.xyfloatingball.floating.AppBean;
import com.andy.xyfloatingball.floating.DownloadInterface;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ReApplication extends MultiDexApplication {

    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Constant.channel = getChannel(this);
        XYFloatingUtils.init(mContext, new DownloadInterface() {
            @Override
            public void download(AppBean.AppInfo appInfo, String module) {
                Log.e("Andy", appInfo.toString());
            }
        });
        GreenDaoManager.getInstance();
    }

    public static Context getContext(){
        return mContext;
    }

    private String getChannel0(Context context){
        try{
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("channel");
        }catch (PackageManager.NameNotFoundException ignored){

        }
        return "";
    }

    /**
     * 获得游戏的渠道Channel
     *
     * @param context
     * @return
     */
    public static String getChannel(Context context) {
        String channel = "";
        final String start_flag = "META-INF/channel_";
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.contains(start_flag)) {
                    channel = entryName.replace(start_flag, "");
                    break;
                }
            }
        } catch (IOException e) {
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                }
            }
        }
        if (channel == null || channel.length() <= 0) {
            channel = "7c8a454a";// 读不到渠道号就默认官方渠道
        }
        return channel;
    }
}
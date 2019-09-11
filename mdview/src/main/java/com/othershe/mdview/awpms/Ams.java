package com.othershe.mdview.awpms;

import android.app.ActivityManager;
import android.content.Context;

import com.othershe.mdview.MdApplication;
import com.othershe.mdview.util.Logger;

import java.util.List;

public class Ams {

    private static final String TAG = "ams";

    public static void getRunningAppProcess() {
        ActivityManager activityManager = (ActivityManager) MdApplication.getMdApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            ActivityManager.RunningAppProcessInfo info = runningAppProcesses.get(i);
            Logger.getInstance().e(TAG, info.processName);
        }
    }


}

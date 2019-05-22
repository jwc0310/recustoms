package com.andy.xyfloatingball;

import android.content.Context;
import android.content.Intent;

import com.andy.xyfloatingball.floating.DownloadInterface;
import com.andy.xyfloatingball.floating.FloatWindowService;

/**
 * Created by Administrator on 2017/1/14.
 */
public class XYFloatingUtils {
    public static DownloadInterface downloadInterface;

    public static void init(Context context, DownloadInterface downloadInterface){
        context.startService(new Intent(context, FloatWindowService.class));
        XYFloatingUtils.downloadInterface = downloadInterface;
    }
}

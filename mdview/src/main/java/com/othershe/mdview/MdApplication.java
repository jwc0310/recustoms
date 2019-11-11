package com.othershe.mdview;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

public class MdApplication extends Application {


    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    public static Context getMdApplicationContext() {
        return context;
    }


}

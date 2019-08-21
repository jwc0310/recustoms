package com.othershe.mdview;

import android.app.Application;
import android.content.Context;

public class MdApplication extends Application {


    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getMdApplicationContext() {
        return context;
    }


}

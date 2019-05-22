package com;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/10/9.
 */

public class GlideApplication extends Application {

    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}

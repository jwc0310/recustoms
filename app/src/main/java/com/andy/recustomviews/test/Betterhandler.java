package com.andy.recustomviews.test;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/12/16.
 */
class BetterRunnable implements Runnable {
    @Override
    public void run() {

    }
}

class BetterHandler extends Handler {

    private String TAG = BetterHandler.class.getSimpleName();

    private WeakReference<Activity> activityWeakReference;

    public BetterHandler(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (activityWeakReference.get() != null) {
            Log.i(TAG,"handle message");
        }
    }
}

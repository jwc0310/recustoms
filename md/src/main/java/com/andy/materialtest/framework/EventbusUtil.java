package com.andy.materialtest.framework;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

public class EventbusUtil {

    private static EventbusUtil instance;

    public static EventbusUtil getInstance() {

        if (instance == null) {
            synchronized (EventbusUtil.class) {
                if (instance == null) {
                    instance = new EventbusUtil();
                }
            }
        }

        return instance;
    }


    public void register(Context context) {
        EventBus.getDefault().register(context);
    }

    /** 防止内存泄漏 **/
    public void unregister(Context context) {
        EventBus.getDefault().unregister(this);
    }



}

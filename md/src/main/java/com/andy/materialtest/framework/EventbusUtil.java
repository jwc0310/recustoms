package com.andy.materialtest.framework;

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


}

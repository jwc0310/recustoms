package com.othershe.mdview.util;

import android.util.Log;

public class Logger {

    private boolean debuggable = true;

    private static Logger instance;

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }

        return instance;
    }

    public void e(String tag, String content) {
        if (!debuggable)
            return;

        Log.e(tag, content);
    }

}

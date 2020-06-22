package com.dev.phonemanager;

import android.app.Activity;

public class DevManager {

    private static DevManager instance;
    private Activity activity;

    public static DevManager getInstance() {
        if (instance == null) {
            instance = new DevManager();
        }

        return instance;
    }

    private DevManager() {
    }

    public void init(Activity activity) {
        this.activity = activity;
    }
}

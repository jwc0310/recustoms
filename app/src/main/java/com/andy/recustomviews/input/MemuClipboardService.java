package com.andy.recustomviews.input;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ClipboardManager;
import android.os.IBinder;

public class MemuClipboardService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String text = intent.getStringExtra("text");
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(text);
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}

package com.andy.recustomviews.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("networkChange", "intent1 = " + intent.getAction());
        Toast.makeText(context, "intent1 = " + intent.getAction(), Toast.LENGTH_SHORT).show();
    }
}

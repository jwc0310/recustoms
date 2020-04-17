package com.othershe.mdview.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.othershe.mdview.uis.MainActivity;

/**
 * Created by Administrator on 2018/5/15.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static NetworkChangeReceiver myReceiver;

    public synchronized static NetworkChangeReceiver getReceiver() {
        if (myReceiver == null) {
            myReceiver = new NetworkChangeReceiver();
        }
        return myReceiver;
    }

    public static boolean isNetworkAvaliable(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] infos = mgr.getAllNetworkInfo();
        if (null != infos) {
            for (int i = 0; i < infos.length; i++) {
                if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public void bootUp(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

//        Log.e("networkChange", "action = " + intent.getAction());
//        if (isNetworkAvaliable(context)) {
//            Toast.makeText(context, "network res = true", Toast.LENGTH_SHORT).show();
//            bootUp(context);
//            Log.e("networkChange", "network res = true");
//            return;
//        }

        if (Build.VERSION.SDK_INT > 22) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
                Toast.makeText(context, "wifi res = true", Toast.LENGTH_SHORT).show();
                bootUp(context);
                Log.e("networkChange", "wifi res = true");
            } else {
                Toast.makeText(context, "wifi res = false", Toast.LENGTH_SHORT).show();
                Log.e("networkChange", "wifi res = false");
            }
        }
    }
}

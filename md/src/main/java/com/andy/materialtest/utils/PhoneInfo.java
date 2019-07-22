package com.andy.materialtest.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import static android.content.Context.TELEPHONY_SERVICE;

public class PhoneInfo {

    public static void getPhoneInfo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(TELEPHONY_SERVICE);// 取得相关系统服务

        String simOperatorName = telephonyManager.getSimOperatorName();
        String imei = telephonyManager.getDeviceId();       //取出 IMEI
        String imeiAPI26 = telephonyManager.getImei();       //取出 IMEI 需要 api26以上
        String tel = telephonyManager.getLine1Number();     //取出 MSISDN，很可能为空
        String imsi = telephonyManager.getSubscriberId();     //取出 IMSI
        String icc = telephonyManager.getSimSerialNumber();  //取出 ICCID

        Log.d("Q_M", "运行商名字--" + simOperatorName);
        Log.d("Q_M", "IMEI--" + imei);
        Log.d("Q_M", "IMEI_API26--" + imeiAPI26);
        Log.d("Q_M", "IMSI--" + imsi);
        Log.d("Q_M", "ICCID--" + icc);
    }

}

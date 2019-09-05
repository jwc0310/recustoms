package com.othershe.mdview.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Xyutil {

    public static void checkFunc() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileReader reader = new FileReader("/proc/version");
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String tmp;
                    while((tmp = bufferedReader.readLine()) != null) {
                        Log.e("xyutil", "tmp = " + tmp);
                    }
                    bufferedReader.close();

                    if (new File("/data/data/com.tencent.tinput").exists()) {
                        Log.e("xyutil", "tinput exist");
                    } else {
                        Log.e("xyutil", "tinput not exist");
                    }

                    if (new File("/dev/virtpipe-render").exists()) {
                        Log.e("xyutil", "render exist");
                    } else {
                        Log.e("xyutil", "render not exist");
                    }

                    if (new File("/dev/virtpipe-common").exists()) {
                        Log.e("xyutil", "common exist");
                    } else {
                        Log.e("xyutil", "common not exist");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }


}

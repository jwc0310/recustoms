package com.othershe.mdview.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {

    private static volatile BitmapUtil instance;

    public static BitmapUtil getInstance() {
        if (instance == null) {
            synchronized (BitmapUtil.class) {
                if (instance == null) {
                    instance = new BitmapUtil();
                }
            }
        }

        return instance;
    }


    public void saveBitmap(Bitmap bitmap, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            fileName = "tmp.png";
        }
        File f = new File(Environment.getExternalStorageDirectory() + "/Download/tmp", fileName);
        if (f.exists()) {
            f.delete();
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

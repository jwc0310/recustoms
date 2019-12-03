package com.othershe.mdview.util;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapHelper {

    private static volatile BitmapHelper instance;

    public static BitmapHelper getInstance() {
        if (instance == null) {
            synchronized (BitmapHelper.class) {
                if (instance == null) {
                    instance = new BitmapHelper();
                }
            }
        }

        return instance;
    }

    /**
     * Save bitmap to local path
     * @param bitmap Bitmap you want to save
     * @param fileName The location you want to save
     */
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

    /**
     * Generate a new bitmap (width x height pixel) with the input bitmap scaled
     * to fix and clipped to an inscribed circle
     * @param input Bitmap to resize and clip
     * @param width Width of the output
     * @param height Height of the output
     * @return A new bitmap for you ro see
     */
    public Bitmap createCircularClip(Bitmap input, int width, int height) {
        if (input == null) return null;

        final int inWidth = input.getWidth();
        final int inHeight = input.getHeight();

        final Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        paint.setShader(new BitmapShader(input, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setAntiAlias(true);

        final RectF srcRect = new RectF(0, 0, inWidth, inHeight);
        final RectF dstRect = new RectF(0, 0, width, height);
        final Matrix m = new Matrix();
        m.setRectToRect(srcRect, dstRect, Matrix.ScaleToFit.CENTER);
        canvas.setMatrix(m);
        canvas.drawCircle(inWidth / 2, inHeight /2 , inWidth / 2, paint);
        return output;
    }




}

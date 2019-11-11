package com.andy.recustomviews.tools;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class Shotter {


    private static void saveBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e("screen", "bitmap == null");
            return;
        }
        String mLocalUrl;

        try {

            mLocalUrl = "/sdcard/Download/screenshot2/" + SystemClock.currentThreadTimeMillis() + ".png";
            File fileImage = new File(mLocalUrl);

            if (!fileImage.exists()) {
                fileImage.createNewFile();
            }

            FileOutputStream out = new FileOutputStream(fileImage);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //截屏方法  1/2
    public static void screenShot() {

        int w = Resources.getSystem().getDisplayMetrics().widthPixels;
        int h = Resources.getSystem().getDisplayMetrics().heightPixels;

        String surfaceClass;
        if (Build.VERSION.SDK_INT <= 17) {
            surfaceClass = "android.view.Surface";
        } else {
            surfaceClass = "android.view.SurfaceControl";
        }

        try {
            Class localClass = Class.forName(surfaceClass);
            Class[] arrayOfClass = new Class[2];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;

            Method localMethod = localClass.getMethod("screenshot", arrayOfClass);

            Object[] arrayOfObject = new Object[2];
            arrayOfObject[0] = Integer.valueOf(w);
            arrayOfObject[1] = Integer.valueOf(h);
            Bitmap b = (Bitmap) localMethod.invoke(null, arrayOfObject);

            saveBitmap(b);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }


    private ImageReader mImageReader;

    public static MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;

    private String mLocalUrl = "";

    private OnShotListener mOnShotListener;

    @SuppressLint("NewApi")
    public Shotter() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mImageReader = ImageReader.newInstance(getScreenWidth(), getScreenHeight(), PixelFormat.RGBA_8888, // 此处必须和下面
                    // buffer处理一致的格式
                    // ，RGB_565在一些机器上出现兼容问题。
                    1);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void virtualDisplay() {

        mVirtualDisplay = mMediaProjection.createVirtualDisplay("screen-mirror", getScreenWidth(), getScreenHeight(),
                Resources.getSystem().getDisplayMetrics().densityDpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mImageReader.getSurface(), null, null);

    }

    public void startScreenShot(OnShotListener onShotListener, String loc_url) {
        mLocalUrl = loc_url;
        startScreenShot(onShotListener);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void startScreenShot(OnShotListener onShotListener) {

        mOnShotListener = onShotListener;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Handler handler = new Handler();

            Log.e("screencap", "handler post");
            virtualDisplay();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Log.e("screencap", "create virtual display");

                    Image image = mImageReader.acquireLatestImage();

                    //AsyncTaskCompat.executeParallel(new SaveTask(), image);
                    SaveTask task = new SaveTask();
                    task.execute(image);
                }
            }, 1100);

        }

    }

    public class SaveTask extends AsyncTask<Image, Void, Bitmap> {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected Bitmap doInBackground(Image... params) {

            if (params == null || params.length < 1 || params[0] == null) {

                return null;
            }

            Image image = params[0];

            int width = image.getWidth();
            int height = image.getHeight();
            final Image.Plane[] planes = image.getPlanes();
            final ByteBuffer buffer = planes[0].getBuffer();
            // 每个像素的间距
            int pixelStride = planes[0].getPixelStride();
            // 总的间距
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - pixelStride * width;
            Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);// 虽然这个色彩比较费内存但是
            // 兼容性更好
            bitmap.copyPixelsFromBuffer(buffer);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
            image.close();
            File fileImage = null;
            if (bitmap != null) {
                try {

                    if (TextUtils.isEmpty(mLocalUrl)) {
                        // 获取内置SD卡路径
//                        mLocalUrl =
//                                getContext().getExternalFilesDir("screenshot").getAbsoluteFile()
//                                        +
//                                        "/"
//                                        +
//                                        SystemClock.currentThreadTimeMillis() + ".png";
//
                        mLocalUrl = "/sdcard/Download/screenshot/" + SystemClock.currentThreadTimeMillis() + ".png";
                    }
                    fileImage = new File(mLocalUrl);

                    if (!fileImage.exists()) {
                        fileImage.createNewFile();
                    }
                    FileOutputStream out = new FileOutputStream(fileImage);
                    if (out != null) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    fileImage = null;
                } catch (IOException e) {
                    e.printStackTrace();
                    fileImage = null;
                }
            }

            if (fileImage != null) {
                return bitmap;
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }

            if (mVirtualDisplay != null) {
                mVirtualDisplay.release();
            }

            if (mOnShotListener != null) {
                mOnShotListener.onFinish();
            }

        }
    }

    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    // a call back listener
    public interface OnShotListener {
        void onFinish();
    }
}
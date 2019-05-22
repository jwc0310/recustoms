package com.andy.recustomviews.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/5/20.
 */
public class BitmapUtils {

    public static boolean saveBmpToSd(String dir, Bitmap bm, String filename,
                                      int quantity, boolean recyle) {
        boolean ret = true;
        if (bm == null) {
            return false;}
        File dirPath = new File(dir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        if (!dir.endsWith(File.separator)) {
            dir += File.separator;
        }
        File file = new File(dir + filename);
        OutputStream outStream = null;
        try {
//            file.createNewFile();
            outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, quantity, outStream);
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
        } finally {
            try {
                if (outStream != null) outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (recyle && !bm.isRecycled()) {
                bm.recycle();
                bm = null;
            }
        }
        return ret;
    }

    /**
     * 旋转图片
     * @param angle 旋转角度
     * @param bitmap 要处理的Bitmap
     * @return 处理后的Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap)
    {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (resizedBitmap != bitmap && bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
        return resizedBitmap;
    }

    /**
     * 圆
     * @param bm
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bm){
        if (bm == null){
            return null;
        }

        int width = bm.getWidth();
        int height = bm.getHeight();

        float roundPx; //半径
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;

        if (width <= height){
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;

            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        }else {
            roundPx = height;
            float clip = (width - height) / 2;
            left = clip;
            top = 0;
            right = width - clip;
            bottom = height;
            width = height;

            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bm, src, dst, paint);
        if (!bm.isRecycled()){
            bm.recycle();
            bm = null;
        }
        return output;
    }


    /**
     * 图片转圆角
     * @param bm
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bm, int pixels){
        if (bm == null){
            return null;
        }
        Bitmap output = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bm.getWidth(), bm.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawBitmap(bm, rect, rect, paint);
        if (!bm.isRecycled()){
            bm.recycle();
        }
        return output;
    }

    /**
     * 按照一定的宽高比例裁剪图片
     * @param bm
     * @param num1  长边比例  宽
     * @param num2  短边比例    高
     * @param isRecycled    是否回收原图片
     * @return
     */
    public static Bitmap imageCrop(Bitmap bm, int num1, int num2, boolean isRecycled){
        if (bm == null){
            return null;
        }
        int width = bm.getWidth();
        int height = bm.getHeight();

        int retX, retY;
        int nw, nh;
        nw = num1;
        nh = num2;

        if (width - num1 > 0){
            retX = (width - num1) / 2;
        }else {
            retX = 0;
        }

        if (height - num2 > 0){
            retY = (height - num2) / 2;
        }else {
            retY = 0;
        }

        Bitmap bitmap = Bitmap.createBitmap(bm, retX, retY, nw, nh, null, false);
        if (isRecycled && bm != null && !bm.equals(bitmap) && !bm.isRecycled()){
            bm.recycle();
            bm = null;
        }
        return bitmap;

    }


    /**
     *  缩放图片
     * @param bm    要放缩的图片
     * @param newWidth  宽度
     * @param newHeight 高度
     * @return
     */
    public static Bitmap scaleImage(Bitmap bm, int newWidth, int newHeight){
        if (bm == null){
            return null;
        }

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float)newWidth) / width;
        float scaleHeight = ((float)newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        if (!bm.isRecycled()){
//            bm.recycle();
//            bm = null;
        }
        return newbm;
    }


    /**
     * 通过资源id转化为Bitmap
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitmapById(Context context, int resId){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, options);
    }

}

package com.andy.recustomviews.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/12/26.
 */
public class AboutBitmap{

    /** Drawable -> Bitmap **/
    public static Bitmap drawablt2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),

                drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;
    }

    /** res -> Bitmap **/
    public static Bitmap res2Bitmap(Context context, int resId){
        Resources resources = context.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(resources, resId);
        return bmp;
    }

    /** Bitmap -> byte[] **/
    public static byte[] bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /** byte[] -> Bitmap **/
    private Bitmap bytes2Bimap(byte[] b){
        if(b.length!=0){
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }else{
            return null;
        }
    }

    /** Bitmap -> file **/
    public static boolean saveBitmap2file(Bitmap bmp,String filename){

        Bitmap.CompressFormat format= Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;

        try {
            stream = new FileOutputStream("/sdcard/" + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }

    /** Bitmap -> round corner **/
    public Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap roundCornerBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundCornerBitmap);
        int color = 0xff424242;// int color = 0xff424242;
        Paint paint = new Paint();
        paint.setColor(color);
        // 防止锯齿
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        float roundPx = pixels;
        // 相当于清屏
        canvas.drawARGB(0, 0, 0, 0);
        // 先画了一个带圆角的矩形
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 再把原来的bitmap画到现在的bitmap！！！注意这个理解
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return roundCornerBitmap;
    }

    /** scale pic **/
    public void scalePic(Context context, int resId){
        // 图片源

        Bitmap bm = BitmapFactory.decodeStream(context.getResources()
                .openRawResource(resId));

        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();

        // 设置想要的大小
        int newWidth = 320;
        int newHeight = 480;

        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // 设置图片的旋转角度
        // matrix.postRotate(-30);
        // 设置图片的倾斜
        // matrix.postSkew(0.1f, 0.1f);
        // 将图片大小压缩
        // 压缩后图片的宽和高以及kB大小均会变化
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,

                true);

        // 放在画布上
        Canvas canvas = new Canvas();
        Paint paint = new Paint();
        canvas.drawBitmap(newbm, 0, 0, paint);
    }

    /** File -> Bitmap **/
    public static void file2Bitmap(){
        Bitmap bt = BitmapFactory.decodeFile("/sdcard/myImage/" + "head.jpg");
    }
}

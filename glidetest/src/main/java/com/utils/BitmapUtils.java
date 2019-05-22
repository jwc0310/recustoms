package com.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/10/9.
 * Bitmap：

 * Bitmap是Android系统中的图像处理的最重要类之一。用它可以获取图像文件信息，进行图像剪切、旋转、缩放等操作，
 * 并可以指定格式保存图像文件。
 * 重要函数:
 * public void recycle() // 回收位图占用的内存空间，把位图标记为Dead
 * public final boolean isRecycled() //判断位图内存是否已释放
 * public final int getWidth()//获取位图的宽度
 * public final int getHeight()//获取位图的高度
 * public final boolean isMutable()//图片是否可修改
 * public int getScaledWidth(Canvas canvas)//获取指定密度转换后的图像的宽度
 * public int getScaledHeight(Canvas canvas)//获取指定密度转换后的图像的高度
 * public boolean compress(CompressFormat format, int quality, OutputStream stream)//按指定的图片格式以及画质，将图片转换为输出流。
                format：Bitmap.CompressFormat.PNG或Bitmap.CompressFormat.JPEG
                quality：画质，0-100.0表示最低画质压缩，100以最高画质压缩。对于PNG等无损格式的图片，会忽略此项设置。
 * public static Bitmap createBitmap(Bitmap src) //以src为原图生成不可变得新图像
 * public static Bitmap createScaledBitmap(Bitmap src, int dstWidth, int dstHeight, boolean filter)//以src为原图，创建新的图像，指定新图像的高宽以及是否可变。
 * public static Bitmap createBitmap(int width, int height, Config config)——创建指定格式、大小的位图
 * public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height)以source为原图，创建新的图片，指定起始坐标以及新图像的高宽。

 * BitmapFactory工厂类：

 * Option 参数类：
 * public boolean inJustDecodeBounds//如果设置为true，不获取图片，不分配内存，但会返回图片的高度宽度信息。
 * public int inSampleSize//图片缩放的倍数
 * public int outWidth//获取图片的宽度值
 * public int outHeight//获取图片的高度值
 * public int inDensity//用于位图的像素压缩比
 * public int inTargetDensity//用于目标位图的像素压缩比（要生成的位图）
 * public byte[] inTempStorage //创建临时文件，将图片存储
 * public boolean inScaled//设置为true时进行图片压缩，从inDensity到inTargetDensity
 * public boolean inDither //如果为true,解码器尝试抖动解码
 * public Bitmap.Config inPreferredConfig //设置解码器
 * public String outMimeType //设置解码图像
 * public boolean inPurgeable//当存储Pixel的内存空间在系统内存不足时是否可以被回收
 * public boolean inInputShareable //inPurgeable为true情况下才生效，是否可以共享一个InputStream
 * public boolean inPreferQualityOverSpeed  //为true则优先保证Bitmap质量其次是解码速度
 * public boolean inMutable //配置Bitmap是否可以更改，比如：在Bitmap上隔几个像素加一条线段
 * public int inScreenDensity //当前屏幕的像素密度

 * 工厂方法:
 * public static Bitmap decodeFile(String pathName, Options opts) //从文件读取图片
 * public static Bitmap decodeFile(String pathName)
 * public static Bitmap decodeStream(InputStream is) //从输入流读取图片
 * public static Bitmap decodeStream(InputStream is, Rect outPadding, Options opts)
 * public static Bitmap decodeResource(Resources res, int id) //从资源文件读取图片
 * public static Bitmap decodeResource(Resources res, int id, Options opts)
 * public static Bitmap decodeByteArray(byte[] data, int offset, int length) //从数组读取图片
 * public static Bitmap decodeByteArray(byte[] data, int offset, int length, Options opts)
 * public static Bitmap decodeFileDescriptor(FileDescriptor fd)//从文件读取文件 与decodeFile不同的是这个直接调用JNI函数进行读取 效率比较高
 * public static Bitmap decodeFileDescriptor(FileDescriptor fd, Rect outPadding, Options opts)

 * Bitmap.Config inPreferredConfig :
 * 枚举变量 （位图位数越高代表其可以存储的颜色信息越多，图像越逼真，占用内存越大）
 * public static final Bitmap.Config ALPHA_8 //代表8位Alpha位图        每个像素占用1byte内存
 * public static final Bitmap.Config ARGB_4444 //代表16位ARGB位图  每个像素占用2byte内存
 * public static final Bitmap.Config ARGB_8888 //代表32位ARGB位图  每个像素占用4byte内存
 * public static final Bitmap.Config RGB_565 //代表8位RGB位图          每个像素占用2byte内存
 * Android中一张图片（BitMap）占用的内存主要和以下几个因数有关：图片长度，图片宽度，单位像素占用的字节数。一张图片（BitMap）占用的内存=图片长度*图片宽度*单位像素占用的字节数
 */

public class BitmapUtils {

    /**从本地文件中获取Bitmap**/

    /**
     * 获取缩放后的本地图片 1
     *
     * @param filePath 文件路径
     * @param width    宽
     * @param height   高
     * @return
     */
    public static Bitmap readBitmapFromFile(String filePath, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 获取缩放后的本地图片 2（效率高于1）
     *
     * @param filePath 文件路径
     * @param width    宽
     * @param height   高
     * @return
     */
    public static Bitmap readBitmapFromFileDescriptor(String filePath, int width, int height) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);
            float srcWidth = options.outWidth;
            float srcHeight = options.outHeight;
            int inSampleSize = 1;

            if (srcHeight > height || srcWidth > width) {
                if (srcWidth > srcHeight) {
                    inSampleSize = Math.round(srcHeight / height);
                } else {
                    inSampleSize = Math.round(srcWidth / width);
                }
            }

            options.inJustDecodeBounds = false;
            options.inSampleSize = inSampleSize;

            return BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**从流中获取Bitmap**/

    /**
     * 获取缩放后的本地图片
     *
     * @param ins    输入流
     * @param width  宽
     * @param height 高
     * @return
     */
    public static Bitmap readBitmapFromInputStream(InputStream ins, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ins, null, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeStream(ins, null, options);
    }

    /**
     * 从资源文件获取，获得放缩后的bitmap(耗费资源，建议使用下边的方式)
     * @param resources
     * @param resourcesId
     * @param width
     * @param height
     * @return
     */
    public static Bitmap readBitmapFromResource(Resources resources, int resourcesId, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resourcesId, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeResource(resources, resourcesId, options);
    }

    public static Bitmap readBitmapFromResource2(Resources resources, int resourcesId, int width, int height) {
        InputStream ins = resources.openRawResource(resourcesId);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ins, null, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeStream(ins, null, options);
    }

    /**从二进制读取图片**/
    public static Bitmap readBitmapFromByteArray(byte[] data, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    /**从assets文件读取图片**/
    /**
     * 获取缩放后的本地图片
     *
     * @param filePath 文件路径
     * @return
     */
    public static Bitmap readBitmapFromAssetsFile(Context context, String filePath) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(filePath);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**图片保存到文件**/
    public static void writeBitmapToFile(String filePath, Bitmap b, int quality) {
        try {
            File desFile = new File(filePath);
            FileOutputStream fos = new FileOutputStream(desFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            b.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**图片压缩**/
    private static Bitmap compressImage(Bitmap image) {
        if (image == null) {
            return null;
        }
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream isBm = new ByteArrayInputStream(bytes);
            Bitmap bitmap = BitmapFactory.decodeStream(isBm);
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**图片缩放**/
    /**
     * 根据scale生成一张图片
     *
     * @param bitmap
     * @param scale  等比缩放值
     * @return
     */
    public static Bitmap bitmapScale(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); // 长和宽放大缩小的比例
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**获取图片旋转角度**/
    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return角度
     */
    private static int readPictureDegree(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**图片旋转一定角度**/
    private static Bitmap rotateBitmap(Bitmap b, float rotateDegree) {
        if (b == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
        return rotaBitmap;
    }

    /**图片转二进制**/
    public byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**Bitmap转Drawable**/
    public static Drawable bitmap2Drawable(Resources resources, Bitmap bm) {
        Drawable drawable = new BitmapDrawable(resources, bm);
        return drawable;
    }

    /**Drawable转Bitmap**/
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }


}

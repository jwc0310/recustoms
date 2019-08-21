package com.othershe.mdview.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.othershe.mdview.R;

import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * Glide 图片框架的使用
 */

public class PicsUtil {

    private static PicsUtil instance;

    public static PicsUtil getInstance() {
        if (instance == null) {
            synchronized (PicsUtil.class) {
                if (instance == null) {
                    instance = new PicsUtil();
                }
            }
        }
        return instance;
    }

    private static class LoadListener implements RequestListener<Drawable> {

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            return false;
        }
    }

    public void loadPic(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public void loadPic(Activity activity, String url, ImageView imageView) {
        Glide.with(activity)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public void loadPic(Fragment fragment, String url, ImageView imageView) {
        Glide.with(fragment)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    //load with listener

    //加载圆形图片
    public void loadRoundPic(Activity activity, String url, ImageView imageView) {
        RequestOptions options = RequestOptions.bitmapTransform(new CircleCrop());
        loadPicWithOptions(activity, url, imageView, options);
    }

    public void loadRoundPic(Context context, String url, ImageView imageView) {
        RequestOptions options = RequestOptions.bitmapTransform(new CircleCrop());
        loadPicWithOptions(context, url, imageView, options);
    }

    public void loadRoundPic(Fragment fragment, String url, ImageView imageView) {
        RequestOptions options = RequestOptions.bitmapTransform(new CircleCrop());
        loadPicWithOptions(fragment, url, imageView, options);
    }

    //设置圆角图片  圆角角度
    public void loadRoundCornerPic(Context context, String url, ImageView imageView) {
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        loadPicWithOptions(context, url, imageView, options);
    }

    //模糊过滤
    public void loadBlurPic(Activity activity, String url, ImageView imageView) {
        RequestOptions options = RequestOptions.bitmapTransform(new BlurTransformation());
        loadPicWithOptions(activity, url, imageView, options);
    }

    public void loadBlurPic(Context context, String url, ImageView imageView) {
        RequestOptions options = RequestOptions.bitmapTransform(new BlurTransformation());
        loadPicWithOptions(context, url, imageView, options);
    }

    //加载制定option的图片
    private void loadPicWithOptions(Activity activity, String url, ImageView imageView, RequestOptions options) {
        Glide.with(activity)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .into(imageView);
    }

    private void loadPicWithOptions(Context context, String url, ImageView imageView, RequestOptions options) {
        Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .into(imageView);
    }

    private void loadPicWithOptions(Fragment fragment, String url, ImageView imageView, RequestOptions options) {
        Glide.with(fragment)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .into(imageView);
    }


    public void loadPicWithPlaceHolder(Fragment fragment, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)              //加载成功之前占位图
                .error(R.mipmap.ic_launcher)                    //加载错误之后的错误图
                .override(400,400)                  //指定图片的尺寸
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                .fitCenter()
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的宽高都大于等于ImageView的宽度，然后截取中间的显示。）
                .centerCrop()
                .circleCrop()//指定图片的缩放类型为circleCrop （圆形）
                .skipMemoryCache(true)                           //跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)       //缓存所有版本的图像
                .diskCacheStrategy(DiskCacheStrategy.NONE)         //跳过磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.DATA)      //只缓存原来分辨率的图片
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)      //只缓存最终的图片
                ;

        Glide.with(fragment)
                .load(url)
                .apply(options)
                .into(imageView);

    }

    private void otherLoadPic(Context context, String url, ImageView imageView) {
//        Glide.with(context).load(url)
//                //模糊
//                .bitmapTransform(new BlurTransformation(this))
//                //圆角
//                .bitmapTransform(new RoundedCornersTransformation(this, 24, 0, RoundedCornersTransformation.CornerType.ALL))
//                //遮盖
//                .bitmapTransform(new MaskTransformation(this, R.mipmap.ic_launcher))
//                //灰度
//                .bitmapTransform(new GrayscaleTransformation(this))
//                //圆形
//                .bitmapTransform(new CropCircleTransformation(this))
//                .into(imageView);

    }


    public Bitmap getBitmap(Activity activity, String url) {
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(activity)
                    .asBitmap()
                    .load(url)
                    .into(100, 100)
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}

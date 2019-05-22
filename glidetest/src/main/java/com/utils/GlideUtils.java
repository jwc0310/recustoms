package com.utils;

/**
 * Created by Administrator on 2017/10/9.
 */

/**
 *
 *

 Glide简单使用
 1.）添加引用 build.gradle 中添加配置
 compile 'com.github.bumptech.glide:glide:3.7.0'

 2.）设置绑定生命周期
 我们可以更加高效的使用Glide提供的方式进行绑定，这样可以更好的让加载图片的请求的生命周期动态管理起来

 Glide.with(Context context);// 绑定Context
 Glide.with(Activity activity);// 绑定Activity
 Glide.with(FragmentActivity activity);// 绑定FragmentActivity
 Glide.with(Fragment fragment);// 绑定Fragment

 3. ）简单的加载图片实例
 Glide.with(this)
        .load(imageUrl)
        .into(imageView);

 4.）设置加载中以及加载失败图片
 api里面对placeholder()、error()函数中有多态实现 用的时候可以具体的熟悉一下

 Glide.with(this)
        .load(imageUrl)
        .placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)
        .into(imageView);

 5.）设置跳过内存缓存

 Glide.with(this)
        .load(imageUrl)
        .skipMemoryCache(true)
        .into(imageView);

 6.）设置下载优先级

 Glide.with(this)
        .load(imageUrl)
        .priority(Priority.NORMAL)
        .into(imageView);

 7.）设置缓存策略

 Glide.with(this)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView);
 策略解说：
 all:缓存源资源和转换后的资源
 none:不作任何磁盘缓存
 source:缓存源资源
 result：缓存转换后的资源

 8.）设置加载动画
 api也提供了几个常用的动画：比如crossFade()

 Glide.with(this)
        .load(imageUrl)
        .animate(R.anim.item_alpha_in)
        .into(imageView);

 R.anim.item_alpha_in
 <?xml version="1.0" encoding="utf-8"?>
 <set xmlns:android="http://schemas.android.com/apk/res/android">
     <alpha
        android:duration="500"
        android:fromAlpha="0.0"
        android:toAlpha="1.0"/>
 </set>

 9.）设置缩略图支持
 这样会先加载缩略图 然后在加载全图

 Glide.with(this)
        .load(imageUrl)
        .thumbnail(0.1f)
        .into(imageView);

 10.）设置加载尺寸

 Glide.with(this)
        .load(imageUrl)
        .override(800, 800)
        .into(imageView);

 11.）设置动态转换

 Glide.with(this)
        .load(imageUrl)
        .centerCrop()
        .into(imageView);

 api提供了比如：
    centerCrop()、fitCenter()等函数也可以通过自定义Transformation，举例说明：比如一个人圆角转化器

 public class GlideRoundTransform extends BitmapTransformation {
     private float radius = 0f;
     public GlideRoundTransform(Context context) {
        this(context, 4);
     }

     public GlideRoundTransform(Context context, int dp) {
         super(context);
         this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
     }

     @Override
     protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
     }

     private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

         Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
         if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
         }

         Canvas canvas = new Canvas(result);
         Paint paint = new Paint();
         paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
         paint.setAntiAlias(true);
         RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
         canvas.drawRoundRect(rectF, radius, radius, paint);
         return result;
     }

     @Override
     public String getId() {
        return getClass().getName() + Math.round(radius);
     }
 }

 具体使用
 Glide.with(this)
     .load(imageUrl)
     .transform(new GlideRoundTransform(this))
     .into(imageView);


 12.）设置要加载的内容

 项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排，该如何实现目标下

 Glide.with(this).load(imageUrl).centerCrop().into(new SimpleTarget<GlideDrawable>() {
 @Override
 public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
 imageView.setImageDrawable(resource);
 }
 });

 13 .）设置监听请求接口

 Glide.with(this).load(imageUrl).listener(new RequestListener<String, GlideDrawable>() {
 @Override
 public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
 return false;
 }

 @Override
 public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
 //imageView.setImageDrawable(resource);
 return false;
 }
 }).into(imageView);

 设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘

 15.)设置动态GIF加载方式

 Glide.with(this).load(imageUrl).asBitmap().into(imageView);//显示gif静态图片
 Glide.with(this).load(imageUrl).asGif().into(imageView);//显示gif动态图片

 16.）缓存的动态清理

 Glide.get(this).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
 Glide.get(this).clearMemory();//清理内存缓存  可以在UI主线程中进行


 *
 */



/**--------------------------进阶-------------------------------------**/

import android.content.Context;

import com.GlideApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;

import java.io.File;

/**
 GlideModule使用：

 GlideModule 是一个抽象方法，全局改变 Glide 行为的一个方式，通过全局GlideModule 配置Glide，用GlideBuilder设置选项，用Glide注册ModelLoader等。

 1.）自定义一个GlideModule

 复制代码
 public class MyGlideModule implements GlideModule {
@Override public void applyOptions(Context context, GlideBuilder builder) {
// Apply options to the builder here.
}

@Override public void registerComponents(Context context, Glide glide) {
// register ModelLoaders here.
}
}
 复制代码
 2.）AndroidManifest.xml注册

 复制代码
 <manifest ...>
 <!-- ... permissions -->
 <application ...>
 <meta-data
 android:name="com.mypackage.MyGlideModule"
 android:value="GlideModule" />
 <!-- ... activities and other components -->
 </application>
 </manifest>
 复制代码
 3.)添加混淆处理

 -keepnames class com.mypackage.MyGlideModule
 # or more generally:
 #-keep public class * implements com.bumptech.glide.module.GlideModule
 4.)多个GlideModule冲突问题

 GlideModule不能指定调用顺序，所以应该避免不同的GlideModule之间有冲突的选项设置，可以考虑将所有的设置都放到一个GlideModule里面，或者排除掉某个manifest文件的某个Module，代码如下：

 <meta-data android:name=”com.mypackage.MyGlideModule” tools:node=”remove” />
 GlideBuilder设置选项：

 1.）设置Glide内存缓存大小

 int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
 int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
 //设置内存缓存大小
 builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
 获取默认的内存使用计算函数

 MemorySizeCalculator calculator = new MemorySizeCalculator(context);
 int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
 int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
 2.）设置Glide磁盘缓存大小

 File cacheDir = context.getExternalCacheDir();//指定的是数据的缓存地址
 int diskCacheSize = 1024 * 1024 * 30;//最多可以缓存多少字节的数据
 //设置磁盘缓存大小
 builder.setDiskCache(new DiskLruCacheFactory(cacheDir.getPath(), "glide", diskCacheSize));
 也可以通过如下两种方式

 //存放在data/data/xxxx/cache/
 builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "glide", diskCacheSize));
 //存放在外置文件浏览器
 builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "glide", diskCacheSize));
 3.)设置图片解码格式

 //设置图片解码格式
 builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
 默认格式RGB_565使用内存是ARGB_8888的一半，但是图片质量就没那么高了，而且不支持透明度

 4.）设置缓存内存大小

 //设置BitmapPool缓存内存大小
 builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));
 5.)设置一个用来检索cache中没有的Resource的ExecutorService

 为了使缩略图请求正确工作，实现类必须把请求根据Priority优先级排好序。

 builder.setDiskCacheService(ExecutorService service);
 builder.setResizeService(ExecutorService service);
 使用ModelLoader自定义数据源：

 例如我们使用了七牛云存储，要根据不同的要求请求不同尺寸不同质量的图片，这时我们就可以使用自定义数据源

 1.)定义处理URL接口

 public interface IDataModel {

 String buildDataModelUrl(int width, int height);

 }
 2.）实现处理URL接口

 JpgDataModel
 复制代码
 public class JpgDataModel implements IDataModel {
 private String dataModelUrl;

 public JpgDataModel(String dataModelUrl) {
 this.dataModelUrl = dataModelUrl;
 }

 @Override
 public String buildDataModelUrl(int width, int height) {
 //http://78re52.com1.z0.glb.clouddn.com/resource/gogopher.jpg?imageView2/1/w/200/h/200/format/jpg
 return String.format("%s?imageView2/1/w/%d/h/%d/format/jpg", dataModelUrl, width, height);
 }
 }
 复制代码
 WebpDataModel
 复制代码
 public class WebpDataModel implements IDataModel {
 private String dataModelUrl;

 public WebpDataModel(String dataModelUrl) {
 this.dataModelUrl = dataModelUrl;
 }

 @Override
 public String buildDataModelUrl(int width, int height) {
 //http://78re52.com1.z0.glb.clouddn.com/resource/gogopher.jpg?imageView2/1/w/200/h/200/format/webp
 return String.format("%s?imageView2/1/w/%d/h/%d/format/webp", dataModelUrl, width, height);
 }
 }
 复制代码
 3.）实现ModelLoader

 复制代码
 public class MyDataLoader extends BaseGlideUrlLoader<IDataModel> {
 public MyDataLoader(Context context) {
 super(context);
 }

 public MyDataLoader(ModelLoader<GlideUrl, InputStream> urlLoader) {
 super(urlLoader, null);
 }

 @Override
 protected String getUrl(IDataModel model, int width, int height) {
 return model.buildDataModelUrl(width, height);
 }

 /**
public static class Factory implements ModelLoaderFactory<IDataModel, InputStream> {

    @Override
    public ModelLoader<IDataModel, InputStream> build(Context context, GenericLoaderFactory factories) {
        return new MyDataLoader(factories.buildModelLoader(GlideUrl.class, InputStream.class));
    }

    @Override
    public void teardown() {
    }
}
}
        复制代码
        4.）根据不同的要求采用不同的策略加载图片

        //加载jpg图片
        Glide.with(this).using(new MyDataLoader(this)).load(new JpgDataModel(imageUrl)).into(imageView);
        //加载webp图片
        Glide.with(this).using(new MyDataLoader(this)).load(new WebpDataModel(imageUrl)).into(imageView);
        5.)如何跳过.using()

        复制代码
public class MyGlideModule implements GlideModule {
    ...
    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(IDataModel.class, InputStream.class,
                new MyUrlLoader.Factory());
    }
}
    复制代码
    上面的实现跳过using()

//加载jpg图片
   Glide.with(this).load(new JpgDataModel(imageUrl)).into(imageView);
           //加载webp图片
           Glide.with(this).load(new WebpDataModel(imageUrl)).into(imageView);
           使用signature()实现自定义cacheKey：

           Glide 以 url、viewwidth、viewheight、屏幕的分辨率等做为联合key，官方api中没有提供删除图片缓存的函数，官方提供了signature()方法，将一个附加的数据加入到缓存key当中，多媒体存储数据，可用MediaStoreSignature类作为标识符，会将文件的修改时间、mimeType等信息作为cacheKey的一部分，通过改变key来实现图片失效相当于软删除。

           1.）使用StringSignature

           Glide.with(this).load(yourFileDataModel).signature(new StringSignature("1.0.0")).into(imageView);
           2.)使用MediaStoreSignature

           Glide.with(this) .load(mediaStoreUri).signature(new MediaStoreSignature(mimeType, dateModified, orientation)).into(view);
           3.)使用自定义Signature

           复制代码
public class IntegerVersionSignature implements Key {
    private int currentVersion;
    public IntegerVersionSignature(int currentVersion) {
        this.currentVersion = currentVersion;
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof IntegerVersionSignature) {
            IntegerVersionSignature other = (IntegerVersionSignature) o;
            return currentVersion = other.currentVersion;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return currentVersion;
    }
    @Override
    public void updateDiskCacheKey(MessageDigest md) {
        messageDigest.update(ByteBuffer.allocate(Integer.SIZE)
                .putInt(signature).array());
    }
}
 **/

public class GlideUtils {

    public static void init(Context context){

        GlideBuilder builder = new GlideBuilder(context);

        /**内存缓存**/
        //获取系统分配给应用的总内存大小
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        //设置图片内存缓存占用八分之一
        builder.setMemoryCache(new LruResourceCache(maxMemory/8));

        //获取默认的内存使用计算函数
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        /**磁盘缓存**/
        //数据缓存地址
        File cacheDir = context.getExternalCacheDir();
        int diskCacheSize = 1024*1024*30;//最多可缓存多少字节
        //设置磁盘缓存大小
        builder.setDiskCache(new DiskLruCacheFactory(cacheDir.getPath(), "glide", diskCacheSize));

        //也可以通过如下两种方式
        //存放在data/data/xxxx/cache/
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "glide", diskCacheSize));
        //存放在外置文件浏览器
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "glide", diskCacheSize));

        /**设置图片解码格式**/
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        /**设置缓存内存大小**/
        builder.setBitmapPool(new LruBitmapPool(maxMemory/8));

    }

}

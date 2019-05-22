package com.andy.recustomviews.tools;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016/12/29.
 */
public class OkhttpUtils {

    private static OkHttpClient okHttpClient;

    private static OkHttpClient getInstance(Context context){
        if (okHttpClient == null){
            File sdcache = context.getExternalCacheDir();
            int cacheSize = 10*1024*1024;
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize))
                    .build();
            return okHttpClient;
        }

        return okHttpClient;
    }

    /** get **/
    private static void getAsyncHttp(Context context, String url, Callback callback) {
        Request.Builder builder = new Request.Builder().url(url);
        builder.method("GET", null);
        Request request = builder.build();
        Call call = getInstance(context).newCall(request);
        call.enqueue(callback);
    }

    /** post **/
    private static void postAsyncHttp(Context context, String url, RequestBody body, Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = getInstance(context).newCall(request);
        call.enqueue(callback);
    }


    public static void accessBaidu(Context context, Callback callback){
        String baidu_url = "http://tracking.lenzmx.com/click?mb_pl=android&mb_nt=cb10925&mb_campid=net_lineage2_kr";
        getAsyncHttp(context, baidu_url, callback);
    }

    public static void postUrl(Context context, String key, String value, Callback callback){
        String baidu_url = "http://www.baidu.com";
        RequestBody body = new FormBody.Builder()
                .add(key, value)
                .build();
        postAsyncHttp(context, baidu_url, body, callback);
    }

}

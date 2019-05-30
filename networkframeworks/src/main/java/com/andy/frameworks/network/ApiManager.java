package com.andy.frameworks.network;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static ApiManager manager;
    private static Retrofit retrofit;

    ApiManager() {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                //请求体定制：统一添加token参数
                if (original.body() instanceof FormBody) {
                    FormBody.Builder newFormBody = new FormBody.Builder();
                    requestBuilder.method(original.method(), newFormBody.build());
                }
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        }).build();

        //创建Retrofit对象
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new com.google.gson.Gson()))
                .build();
    }

    //单例模式获取 ApiManager实例
    public static ApiManager getInstance() {
        if (manager == null) {
            synchronized (ApiManager.class) {
                if (manager == null) {
                    manager = new ApiManager();
                }
            }
        }

        return manager;
    }

    //创建Service实例
    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }

}

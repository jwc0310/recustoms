package com.andy.recustomviews.network;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiAccess {

    /**
     *  每个server_host 对应一个apiAccess实例
     *  也可以使用release 和 debug 版本
     */
    /** andy server **/
    private static final String SERVER_URL = "http://www.andy.com.cn/";
    private static  ApiAccess apiAccess = null;
    public static  ApiAccess getInstance(){
        if (apiAccess == null){
            apiAccess = new ApiAccess(SERVER_URL);
        }
        return apiAccess;
    }

    /** weather server **/
    private static final String HOST_WEATHER_URL = "http://www.weather.com.cn/data/sk/101010100.html";
    private static ApiAccess weatherApi = null;
    public static ApiAccess getWeatherInstance(){
        if (weatherApi == null){
            weatherApi = new ApiAccess(HOST_WEATHER_URL);
        }
        return weatherApi;
    }


    private ApiService apiService;
    public ApiService getApiService(){
        return apiService;
    }

    private ApiAccess(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }



    public static void getVersion(){
        getInstance().getApiService().getVersion2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        
                    }
                });
    }
}

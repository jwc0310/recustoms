package com.andy.recustomviews.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

public interface ApiService {

    @GET("new_market/update/ph/version.xml")
    Call<ResponseBody> getVersion();

    @GET("new_market/update/ph/version.xml")
    Observable<ResponseBody> getVersion2();

}

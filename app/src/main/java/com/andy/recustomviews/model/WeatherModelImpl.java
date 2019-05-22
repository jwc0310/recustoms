package com.andy.recustomviews.model;

import com.andy.recustomviews.interfaces.OnRequestListener;
import com.andy.recustomviews.network.ApiAccess;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/3/25.
 */
public class WeatherModelImpl implements IWeatherModel {
    @Override
    public void getWeater(String cityNumber, final OnRequestListener onRequestListener) {
        Call<ResponseBody> call =  ApiAccess.getInstance().getApiService().getVersion();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    String res = null;
                    try {
                        res = response.body().string();
                        onRequestListener.onSuccess(res);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    onRequestListener.onFailure(0x1);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onRequestListener.onFailure(0x0);
            }
        });
    }
}

package com.andy.recustomviews.model;

import com.andy.recustomviews.interfaces.OnRequestListener;

/**
 * Created by Administrator on 2017/3/24.
 */
public interface IWeatherModel {
    void getWeater(String cityNumber, OnRequestListener onRequestListener);
}

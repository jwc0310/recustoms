package com.andy.recustomviews.interfaces;

/**
 * Created by Administrator on 2017/3/25.
 */
public interface OnRequestListener<T> {
    void onSuccess(T data);
    void onFailure(int code);
}

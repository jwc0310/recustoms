package com.andy.recustomviews.MVP.module;

/**
 * Created by Administrator on 2017/6/8.
 */
public interface IModuleUserLogin {
    void login(String username, String password, OnLoginListener listener);
}

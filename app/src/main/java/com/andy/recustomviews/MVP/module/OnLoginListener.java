package com.andy.recustomviews.MVP.module;

import com.andy.recustomviews.MVP.bean.LoginUser;

/**
 * Created by zhy on 15/6/19.
 */
public interface OnLoginListener {
    void loginSuccess(LoginUser user);

    void loginFailed();
}

package com.andy.recustomviews.MVP.view;

import com.andy.recustomviews.MVP.base.IBaseView;
import com.andy.recustomviews.MVP.bean.LoginUser;

public interface IUserLoginView extends IBaseView {

    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void toMainActivity(LoginUser user);

    void showFailedError();

}

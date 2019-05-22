package com.andy.recustomviews.MVP.view;

import com.andy.recustomviews.MVP.bean.LoginUser;

/**
 * Created by Administrator on 2017/6/8.
 */
public interface IBaseView {

    void showLoading();

    void hideLoading();

    interface IUserLoginView extends IBaseView{

        String getUserName();

        String getPassword();

        void clearUserName();

        void clearPassword();

        void toMainActivity(LoginUser user);

        void showFailedError();
    }

}

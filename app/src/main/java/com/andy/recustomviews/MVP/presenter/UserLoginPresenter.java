package com.andy.recustomviews.MVP.presenter;

import android.os.Handler;

import com.andy.recustomviews.MVP.bean.LoginUser;
import com.andy.recustomviews.MVP.module.IModuleUserLogin;
import com.andy.recustomviews.MVP.module.ModuleUserLoginImpl;
import com.andy.recustomviews.MVP.module.OnLoginListener;
import com.andy.recustomviews.MVP.view.IBaseView;

/**
 * Created by Administrator on 2017/6/8.
 */
public class UserLoginPresenter {

    private IModuleUserLogin userLoginModuel;
    private IBaseView.IUserLoginView userLoginView;
    private Handler mHandler = new Handler();

    public UserLoginPresenter(IBaseView.IUserLoginView userLoginView) {
        this.userLoginView = userLoginView;
        userLoginModuel = new ModuleUserLoginImpl();
    }

    public void login(){
        userLoginView.showLoading();
        userLoginModuel.login(userLoginView.getUserName(), userLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final LoginUser user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.toMainActivity(user);
                        userLoginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.showFailedError();
                        userLoginView.hideLoading();
                    }
                });
            }
        });

    }

    public void clear(){
        userLoginView.clearUserName();
        userLoginView.clearPassword();;
    }
}

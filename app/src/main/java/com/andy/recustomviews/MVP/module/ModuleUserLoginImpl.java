package com.andy.recustomviews.MVP.module;

import com.andy.recustomviews.MVP.bean.LoginUser;

/**
 * Created by Administrator on 2017/6/8.
 */
public class ModuleUserLoginImpl implements IModuleUserLogin {

    @Override
    public void login(final String username, final String password, final OnLoginListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                if ("cjw".equals(username) && "123".equals(password)){
                    LoginUser user = new LoginUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    listener.loginSuccess(user);
                }else {
                    listener.loginFailed();
                }
            }
        }).start();
    }
}

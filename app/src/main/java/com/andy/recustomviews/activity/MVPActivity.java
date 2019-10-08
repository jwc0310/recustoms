package com.andy.recustomviews.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andy.recustomviews.MVP.bean.LoginUser;
import com.andy.recustomviews.MVP.presenter.UserLoginPresenter;
import com.andy.recustomviews.MVP.view.IUserLoginView;
import com.andy.recustomviews.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MVPActivity extends Base2Activity implements IUserLoginView {

    @BindView(R.id.id_et_username)
    EditText idEtUsername;
    @BindView(R.id.id_et_password)
    EditText idEtPassword;
    @BindView(R.id.id_btn_login)
    Button idBtnLogin;
    @BindView(R.id.id_btn_clear)
    Button idBtnClear;
    @BindView(R.id.id_pb_loading)
    ProgressBar idPbLoading;

    private UserLoginPresenter userLoginPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    public void init(Bundle bundle) {
        userLoginPresenter = new UserLoginPresenter(this);
    }

    @OnClick({R.id.id_btn_login, R.id.id_btn_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_btn_login:
                userLoginPresenter.login();
                break;
            case R.id.id_btn_clear:
                userLoginPresenter.clear();
                break;
        }
    }

    @Override
    public String getUserName() {
        return idEtUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return idEtPassword.getText().toString();
    }

    @Override
    public void clearUserName() {
        idEtUsername.setText("");
    }

    @Override
    public void clearPassword() {
        idEtPassword.setText("");
    }

    @Override
    public void toMainActivity(LoginUser user) {
        Toast.makeText(this, user.getUsername() +
                " login success , to MainActivity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailedError() {
        Toast.makeText(this,
                "login failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (idPbLoading.getVisibility() != View.VISIBLE){
            idPbLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (idPbLoading.getVisibility() != View.GONE){
            idPbLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }
}

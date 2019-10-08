package com.andy.recustomviews.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.andy.recustomviews.MVP.contract.RxJavaContract;
import com.andy.recustomviews.MVP.presenter.RxJavaPresenter;
import com.andy.recustomviews.R;

import butterknife.BindView;
import butterknife.OnClick;

public class Rxjava23Activity extends Base2Activity implements RxJavaContract.View {

    RxJavaPresenter presenter = new RxJavaPresenter();

    @BindView(R.id.create)
    Button create;

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.rxjava_23;
    }

    @Override
    public void init(Bundle bundle) {
        if (!presenter.isViewAttached())
            presenter.attachView(this);
    }

    @OnClick(R.id.create)
    public void onViewClicked() {
        presenter.click();
    }

    @Override
    public void setEnableFalse() {
        if (create == null)
            return;
        create.setEnabled(false);
        create.setTextColor(Color.GRAY);
    }

    @Override
    public void updateButtonContent(Long counter) {
        if (create == null)
            return;
            create.setText(counter + "秒后重发");
    }

    @Override
    public void setEnableTrue() {
        if (create == null)
            return;
        create.setEnabled(true);
        create.setTextColor(Color.RED);
        create.setText("发送验证码");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}

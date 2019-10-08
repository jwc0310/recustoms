package com.andy.recustomviews.MVP.presenter;

import com.andy.recustomviews.MVP.base.BasePresenter;
import com.andy.recustomviews.MVP.contract.RxJavaContract;
import com.andy.recustomviews.rxjava2.Rxjava2Test;

public class RxJavaPresenter extends BasePresenter<RxJavaContract.View> implements RxJavaContract.Presenter {

    @Override
    public void click() {
        Rxjava2Test.simulateSendSMS(mView);
    }
}

package com.andy.recustomviews.MVP.presenter;

import com.andy.recustomviews.MVP.base.BasePresenter;
import com.andy.recustomviews.MVP.contract.RxJavaContract;
import com.andy.recustomviews.rxjava2.Rxjava2Test;

import io.reactivex.disposables.CompositeDisposable;

public class RxJavaPresenter extends BasePresenter<RxJavaContract.View> implements RxJavaContract.Presenter {

    private CompositeDisposable compositeDisposable;

    public RxJavaPresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void click() {
        Rxjava2Test.simulateSendSMS(compositeDisposable, mView);
    }

    @Override
    public void unSubscribe() {
    }

    @Override
    public void detachView() {
        super.detachView();
        compositeDisposable.dispose();
    }


}

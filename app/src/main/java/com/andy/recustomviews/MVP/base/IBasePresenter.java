package com.andy.recustomviews.MVP.base;

public interface IBasePresenter<V extends IBaseView> {
    void attachView(V view);
}

package com.othershe.mdview.bases;

public interface IBasePresenter <V extends IBaseView> {
    void attachView(V view);
}

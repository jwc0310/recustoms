package com.andy.recustomviews.MVP.base;

public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    protected V mView;

    public BasePresenter() {

        //可以初始化 module数据
    }

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */

    public void detachView() {
        this.mView = null;
    }

    /**
     * View是否绑定
     *
     * @return
     */
    public boolean isViewAttached() {
        return mView != null;
    }


}

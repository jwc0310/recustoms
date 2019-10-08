package com.andy.recustomviews.MVP.contract;

import com.andy.recustomviews.MVP.base.IBasePresenter;
import com.andy.recustomviews.MVP.base.IBaseView;

public class RxJavaContract {

    public interface View extends IBaseView {
        void setEnableFalse();
        void updateButtonContent(Long counter);
        void setEnableTrue();

    }

    public interface Presenter extends IBasePresenter<View> {
        void click();
        void unSubscribe();
    }

}

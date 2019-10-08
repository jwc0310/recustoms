package com.othershe.mdview.MVP.contract;

import com.othershe.mdview.bases.IBasePresenter;
import com.othershe.mdview.bases.IBaseView;

public class ViewPagerContract {

    public interface View extends IBaseView {
        void onSuccess();
    }

    public interface Presenter extends IBasePresenter<View> {
        void initData();
    }

}

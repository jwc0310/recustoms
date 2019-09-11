package com.othershe.mdview.MVP.contract;

import com.othershe.mdview.bases.IBasePresenter;
import com.othershe.mdview.bases.IBaseView;

import java.util.List;

public class EnhanceContact {

    public interface View extends IBaseView {
        void onSuccess(List<String> list);
    }

    public interface Presenter extends IBasePresenter<View> {
        void initData();
    }

}

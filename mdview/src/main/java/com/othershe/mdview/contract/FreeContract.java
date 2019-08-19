package com.othershe.mdview.contract;

import com.othershe.mdview.bases.IBaseView;

public class FreeContract {

    public interface View extends IBaseView {
        void onSuccess();
    }

    public interface Presenter {
        void execute(String comamnd);
    }

}

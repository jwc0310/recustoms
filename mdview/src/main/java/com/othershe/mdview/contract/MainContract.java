package com.othershe.mdview.contract;

import com.othershe.mdview.bases.IBaseView;
import com.othershe.mdview.bean.UpdateOp;

import java.util.List;

public class MainContract {

    public interface View extends IBaseView {
        void onSuccess(List<UpdateOp> beans);
    }

    public interface Presenter {
        void getData(String type);
        void setEmu(UpdateOp updateOp, String type);
    }

}

package com.othershe.mdview.MVP.presenter;

import com.othershe.mdview.MVP.contract.EnhanceContact;
import com.othershe.mdview.bases.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class EnhancePresenter extends BasePresenter<EnhanceContact.View> implements EnhanceContact.Presenter {

    @Override
    public void initData() {
        List<String> tmp = new ArrayList<>();
        for (int i = 0 ; i < 20; i++) {
            tmp.add("mTabLayout test" + i);
        }
        mView.onSuccess(tmp);
    }
}

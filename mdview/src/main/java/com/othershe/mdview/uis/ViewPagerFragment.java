package com.othershe.mdview.uis;

import com.othershe.mdview.MVP.contract.ViewPagerContract;
import com.othershe.mdview.MVP.presenter.ViewPagerPresenter;
import com.othershe.mdview.R;
import com.othershe.mdview.bases.MVPBaseFragment;

public class ViewPagerFragment extends MVPBaseFragment<ViewPagerPresenter> implements ViewPagerContract.View {
    @Override
    protected void initPresenter() {
        presenter = new ViewPagerPresenter();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_viewpager;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onSuccess() {

    }
}

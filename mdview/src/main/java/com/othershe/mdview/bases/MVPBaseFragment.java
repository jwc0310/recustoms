package com.othershe.mdview.bases;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class MVPBaseFragment<T extends BasePresenter> extends BaseFragment implements IBaseView {

    protected T presenter;

    /**
     * 初始化mPresenter
     */
    protected abstract void initPresenter();

    @Override
    protected void init() {
        initPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    public MVPBaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}

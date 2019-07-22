package com.andy.materialtest.bases;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected AppCompatActivity mActivity;
    //添加loading
    private View root;
    protected Unbinder unbinder;

    public abstract int layoutId();
    public abstract void initView(View view);
    public abstract void initData();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (AppCompatActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == root){
            root = inflater.inflate(layoutId(), null);
            unbinder = ButterKnife.bind(this, root);
            initView(root);
            initData();
        }

        ViewGroup parent = (ViewGroup)root.getParent();
        if (parent != null){
            parent.removeView(root);
        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy(){

        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

}

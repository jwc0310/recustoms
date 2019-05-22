package com.andy.recustomviews.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected AppCompatActivity mActivity;
    protected View root;
    private Unbinder unbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity =  (AppCompatActivity) activity;
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle bundle){
        if (null == root){
            root = inflater.inflate(getLayoutId(), null);
            unbinder = ButterKnife.bind(this, root);
            init(bundle);
        }

        ViewGroup parent = (ViewGroup) root.getParent();
        if (null != parent){
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
        if ( null != unbinder ){
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public abstract int getLayoutId();
    public abstract void init(Bundle bundle);

}

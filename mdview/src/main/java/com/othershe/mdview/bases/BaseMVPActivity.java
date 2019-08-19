package com.othershe.mdview.bases;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 所有Activity的base类
 * Created by Andy on 2016/7/1.
 */
public abstract class BaseMVPActivity<T extends BasePresenter> extends AppCompatActivity {

    protected AppCompatActivity mContext;
    private Unbinder unbinder;
    private T presenter;

    public abstract int addBaseView();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(addBaseView());
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        if (unbinder != null)
            unbinder.unbind();
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }
}

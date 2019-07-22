package com.andy.materialtest.uis;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.andy.materialtest.R;
import com.andy.materialtest.bases.BaseActivity;

public class Test4Activity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    public int contentViewResId() {
        return R.layout.activity_test4;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

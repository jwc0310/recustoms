package com.andy.recustomviews.proj_1;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.andy.recustomviews.R;
import com.andy.recustomviews.activity.Base2Activity;
import com.andy.recustomviews.fragment.BaseFragment;
import com.andy.recustomviews.proj_1.fragment.MainFragment;
import com.andy.recustomviews.tools.FragmentUtils;

import butterknife.BindView;

public class MainActivity_proj1 extends Base2Activity {

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private BaseFragment main;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_activity_proj1;
    }

    @Override
    public void init(Bundle bundle) {
        main = new MainFragment();
        FragmentUtils.switchFragment(this, R.id.fragment_container,main);
    }
}

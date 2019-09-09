package com.othershe.mdview.uis;

import android.support.design.widget.TabLayout;

import com.othershe.mdview.MVP.contract.EnhanceContact;
import com.othershe.mdview.MVP.presenter.EnhancePresenter;
import com.othershe.mdview.R;
import com.othershe.mdview.bases.MVPBaseFragment;
import com.othershe.mdview.util.Logger;
import com.othershe.mdview.view.EnhanceTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * EnhanceTabLayoutFragment  固定长度 indicator
 */

public class EnhanceTabLayoutFragment extends MVPBaseFragment<EnhancePresenter> implements EnhanceContact.View {


    private static final String TAG = "EnhanceTabLayoutFragment";

    @BindView(R.id.enhance_tab_layout)
    EnhanceTabLayout enhanceTabLayout;

    private List<String> mTitls =new ArrayList<>();

    @Override
    protected int layoutId() {
        return R.layout.fragment_enhance_tab_layout;
    }

    @Override
    protected void initView() {

        enhanceTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Logger.getInstance().e(TAG, "onTabSelected");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        presenter.initData();
    }


    @Override
    protected void initPresenter() {
        presenter = new EnhancePresenter();
    }

    @Override
    public void onSuccess(List<String> list) {
        mTitls.clear();
        mTitls.addAll(list);
        for (int i = 0; i < mTitls.size(); i++) {
            enhanceTabLayout.addTab(mTitls.get(i));
        }
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

}

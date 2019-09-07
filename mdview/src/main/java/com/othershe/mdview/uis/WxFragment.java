package com.othershe.mdview.uis;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.othershe.mdview.R;
import com.othershe.mdview.bases.BaseFragment;
import com.othershe.mdview.util.Logger;
import com.othershe.mdview.view.TabView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 模仿微信 下边栏
 * <p>
 * ViewPager
 * PageTransformer属性、clipChildren属性、clipToPadding属性
 * <p>
 * <p>
 * clipChildren属性 表示是否限制子控件在该容器所在的范围内
 */


public class WxFragment extends BaseFragment {

    private static final String TAG = "WxFragment";

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tab_weixin)
    TabView tabWeixin;
    @BindView(R.id.tab_contact)
    TabView tabContact;
    @BindView(R.id.tab_find)
    TabView tabFind;
    @BindView(R.id.tab_profile)
    TabView tabProfile;

    MyPaperAdapter adapter;
    private List<String> names;
    private List<TabView> mTabViews = new ArrayList<>();

    @Override
    protected int layoutId() {
        return R.layout.fragment_wx;
    }

    @Override
    protected void initView() {
        names = new ArrayList<>();
        names.add("微信");
        names.add("通讯录");
        names.add("发现");
        names.add("我");
        adapter = new MyPaperAdapter(names);

        mTabViews.add(tabWeixin);
        mTabViews.add(tabContact);
        mTabViews.add(tabFind);
        mTabViews.add(tabProfile);

        // 设置ViewPager两页之间的距离
        viewPager.setPageMargin(0);
        // 设置预加载的页数，我们知道默认情况下这个参数为1，也就是左右各预加载一页，但是我们这里要让左右各预加载两页
        viewPager.setOffscreenPageLimit(names.size() -1);
        viewPager.setAdapter(adapter);
        //viewPager.setPageTransformer(false, new ScaleTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * @param i 滑动的时候，position总是代表左边的View， position+1总是代表右边的View
             * @param v 左边View位移的比例
             * @param i1 左边View位移的像素
             */

            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Logger.getInstance().e(TAG, "onPageScrolled: " + i + ", " + v + ", " + i1);
                mTabViews.get(i).setXPercentage(1 - v);

                if (v > 0) {
                    mTabViews.get(i + 1).setXPercentage(v);
                }

            }

            @Override
            public void onPageSelected(int i) {
                Logger.getInstance().e(TAG, "onPageSelected: " + i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Logger.getInstance().e(TAG, "onPageScrollStateChanged: " + i);
            }
        });

    }

    @OnClick({R.id.tab_weixin, R.id.tab_contact, R.id.tab_find, R.id.tab_profile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_weixin:
                changeTab(0);
                break;
            case R.id.tab_contact:
                changeTab(1);
                break;
            case R.id.tab_find:
                changeTab(2);
                break;
            case R.id.tab_profile:
                changeTab(3);
                break;
        }
    }

    private void updateCurrentTab(int index) {
        for (int i = 0; i < mTabViews.size(); i++) {
            if (index == i) {
                mTabViews.get(i).setXPercentage(1);
            } else {
                mTabViews.get(i).setXPercentage(0);
            }
        }
    }

    private void changeTab(int index) {
        if (viewPager.getCurrentItem() == index)
            return;
        //设置 smoothscroll false  否则 中间的都变化一遍
        viewPager.setCurrentItem(index, false);
        updateCurrentTab(index);
    }

    static class MyPaperAdapter extends PagerAdapter {

        private List<String> names;

        public MyPaperAdapter(List<String> names) {
            this.names = names;
        }

        @Override
        public int getCount() {
            return names.size();
        }

        //判断是否page view与 instantiateItem(ViewGroup, int)返回的object的key 是否相同，以提供给其他的函数使用
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_wx, null);
            TextView tv1 = view.findViewById(R.id.item_content);
            tv1.setText(names.get(position) == null ? "" : names.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}

package com.othershe.mdview.uis;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.othershe.mdview.MVP.contract.ViewPagerContract;
import com.othershe.mdview.MVP.presenter.ViewPagerPresenter;
import com.othershe.mdview.R;
import com.othershe.mdview.bases.MVPBaseFragment;
import com.othershe.mdview.util.Logger;
import com.othershe.mdview.view.transformers.AlphaTransformer;
import com.othershe.mdview.view.transformers.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ViewPager2Fragment extends MVPBaseFragment<ViewPagerPresenter> implements ViewPagerContract.View {

    @BindView(R.id.viewPager_viewPager)
    ViewPager viewPagerViewPager;

    @Override
    protected void initPresenter() {
        presenter = new ViewPagerPresenter();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_viewpager2;
    }

    @Override
    protected void initView() {
        viewPagerViewPager.setOffscreenPageLimit(3);
        viewPagerViewPager.setPageMargin(getActivity().getResources().getDimensionPixelSize(R.dimen.viewpager_page_margin));
        List<Drawable> colors = new ArrayList<>();
        colors.add(getResources().getDrawable(R.drawable.blue));
        colors.add(getResources().getDrawable(R.drawable.blue_light));
        colors.add(getResources().getDrawable(R.drawable.green));
        colors.add(getResources().getDrawable(R.drawable.red));
        colors.add(getResources().getDrawable(R.drawable.red2));
        colors.add(getResources().getDrawable(R.drawable.yellow));

        MyVpAdater adater = new MyVpAdater(getActivity(), colors);
        viewPagerViewPager.setAdapter(adater);
//        viewPagerViewPager.setPageTransformer(false, new AlphaTransformer());
        viewPagerViewPager.setPageTransformer(false, new ScaleTransformer());

        //adater.notifyDataSetChanged();


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


    public class MyVpAdater extends PagerAdapter {
        private List<Drawable> list;
        private Context context;
        private LayoutInflater inflater;

        public MyVpAdater(Context context, List<Drawable> list) {
            this.context = context;
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.item_pager, null);
            Logger.getInstance().e("viewpager", position +", " +list.get(position) + " ");

            LinearLayout linearLayout1 = (LinearLayout) linearLayout.findViewById(R.id.item_viewpager_parent);
            linearLayout1.setBackground(list.get(position));

            TextView textView = (TextView) linearLayout.findViewById(R.id.item_viewpager_index);
            textView.setText(position + "");

            container.addView(linearLayout);
            return linearLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

package com.othershe.mdview.util;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

public class TabLayoutUtil {

    /**
     *
     * 反射的方式修改tablayout indicator 宽度
     *
     * tabView的宽度
     *
     * @param tabLayout tablayout
     * @param padding  设置padding
     *
     * 注意事项：不能设置indicator高度
     *          TabView中 textView属性名可能不一样
     *          最短只能是tab内容的宽度
     */
    public static void setTabWidth(final TabLayout tabLayout, final int padding) {

        tabLayout.post(new Runnable() {
            @Override
            public void run() {

                try {
                    //拿到tablayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int len = mTabStrip.getChildCount();
                    for (int i = 0; i < len; i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性, tab的字数不固定一定用反射取mTextView
                        Field mTextViewFiled = tabView.getClass().getDeclaredField("textView");
                        mTextViewFiled.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewFiled.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);
                        //想要效果 字体多宽 线就多款， 所以测量mTextView的宽度

                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距 不能使用padding
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = padding;
                        params.rightMargin = padding;

                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}

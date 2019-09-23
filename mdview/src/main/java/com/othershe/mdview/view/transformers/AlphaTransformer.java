package com.othershe.mdview.view.transformers;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class AlphaTransformer implements ViewPager.PageTransformer {


    private final float MINALPHA = 0.5f;


    /**
     *
     * position 取值特点
     * 页面 0 ~ 1
     * 第一个页面position页面变化 [0, -1]
     * 第二个页面position页面变化 [1, 0]
     *
     * @param view page
     * @param v position
     */
    @Override
    public void transformPage(@NonNull View view, float v) {

        if (v < -1 || v > 1)
            view.setAlpha(MINALPHA);
        else {
            //不透明 -> 半透明
            if (v < 0) {
                view.setAlpha(MINALPHA + (1 + v) * (1 - MINALPHA));
            } else {
                view.setAlpha(MINALPHA + (1 - v) * (1 - MINALPHA));
            }
        }

    }
}

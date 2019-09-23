package com.othershe.mdview.view.transformers;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ScaleTransformer implements ViewPager.PageTransformer {


    private static final float MIN_SCALE = 0.7f;

    /**
     *
     * v [-1, 1]
     *
     *
     *
     * @param view page
     * @param v position
     */
    @Override
    public void transformPage(@NonNull View view, float v) {

        if (v < -1 || v > 1) {
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        } else {
            if (v < 0) {
                view.setScaleY(MIN_SCALE + (1 + v) * (1 - MIN_SCALE));
                view.setScaleX(MIN_SCALE + (1 + v) * (1 - MIN_SCALE));
            } else {
                view.setScaleX(MIN_SCALE + (1 - v) * (1 - MIN_SCALE));
                view.setScaleY(MIN_SCALE + (1 - v) * (1 - MIN_SCALE));
            }
        }

    }


}

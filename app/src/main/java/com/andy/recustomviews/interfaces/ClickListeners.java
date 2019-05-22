package com.andy.recustomviews.interfaces;

import android.view.View;

/**
 * Created by Administrator on 2016/12/26.
 */
public interface ClickListeners {
    boolean onLongClickListener(View view, int position);
    void onClickListener(View view, int position);
}

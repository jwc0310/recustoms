package com.andy.recustomviews.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class LinearLayoutSubClass extends LinearLayout {
    private Scroller mScroller;
    private boolean flag=true;
    private int offsetY;
    private int duration;
    public LinearLayoutSubClass(Context context) {
        super(context);
    }

    public LinearLayoutSubClass(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller=new Scroller(context);
        duration=5000;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), offsetY);
            invalidate();
        }
    }

    public void beginScroll() {
        if (flag) {
            offsetY = 0;
            int startX = mScroller.getCurrX();
            int startY = mScroller.getCurrY();
            int dx = -500;
            int dy = 0;
            mScroller.startScroll(startX, startY, dx, dy, duration);
            flag = false;
        } else {
            offsetY = 0;
            int startX = mScroller.getCurrX();
            int startY = mScroller.getCurrY();
            int dx = -startX;
            int dy = 0;
            mScroller.startScroll(startX, startY, dx, dy, duration);
            flag = true;
        }
        invalidate();
    }
}
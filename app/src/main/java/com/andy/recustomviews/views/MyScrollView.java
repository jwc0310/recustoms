package com.andy.recustomviews.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 *
 * Created by Andy on 2016/12/23.
 */
public class MyScrollView extends ScrollView {

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public interface ScrollViewListener {
        void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy);
        void scrollBottom(int updown); // 0 bottom, 1 top

    }
    private ScrollViewListener scrollViewListener;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt){
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null){
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
            if (t + getHeight() >= computeVerticalScrollRange()){
                scrollViewListener.scrollBottom(0);
            }

            if ( t <= 0){
                scrollViewListener.scrollBottom(1);
            }
        }
    }

    @Override
    public void onFinishInflate(){
        super.onFinishInflate();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
    }
}

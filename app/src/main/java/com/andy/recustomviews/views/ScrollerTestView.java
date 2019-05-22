package com.andy.recustomviews.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.andy.recustomviews.R;

/**
 * Scroller
 * Created by Andy on 2016/12/16.
 */
public class ScrollerTestView extends LinearLayout implements View.OnClickListener {
    private String TAG = ScrollerTestView.class.getSimpleName();

    private Button scroller_to, scroller_by, scroller_reset;
    private View pic;
    private LinearLayoutSubClass llsc;

    public ScrollerTestView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_scroller_test, this);
        pic = findViewById(R.id.scroller_image);
        findViewById(R.id.scroller_fling).setOnClickListener(this);
        findViewById(R.id.scroller_to).setOnClickListener(this);
        findViewById(R.id.scroller_by).setOnClickListener(this);
        findViewById(R.id.scroller_reset).setOnClickListener(this);
        llsc = (LinearLayoutSubClass) findViewById(R.id.llsc);
    }

    public ScrollerTestView(Context context, AttributeSet attrs) {
        this(context);
    }

    public ScrollerTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scroller_by:
                break;
            case R.id.scroller_to:
                break;
            case R.id.scroller_reset:
                break;
            case R.id.scroller_fling:
                llsc.beginScroll();
                break;
        }
    }


    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
                                           int parentHeightMeasureSpec, int heightUsed) {
        final MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        int mPaddingLeft = child.getPaddingLeft();
        int mPaddingRight = child.getPaddingRight();
        int mPaddingTop = child.getPaddingTop();
        int mPaddingBottom = child.getPaddingBottom();
        final int childWidthMeasureSpec =
                getChildMeasureSpec(parentWidthMeasureSpec, mPaddingLeft + mPaddingRight +
                        lp.leftMargin + lp.rightMargin + widthUsed, lp.width);
        final int childHeightMeasureSpec =
                getChildMeasureSpec(parentHeightMeasureSpec, mPaddingTop + mPaddingBottom +
                        lp.topMargin + lp.bottomMargin + heightUsed, lp.height);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }
}

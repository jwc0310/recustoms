package com.andy.recustomviews.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class LauncherViewGroup extends ViewGroup {

    private final String TAG = this.getClass().getSimpleName();

    private int width;
    private int scrollX;
    private float downX;
    private float moveX;
    private float lastX;
    private int touchSlop;
    private int leftLimit;
    private int rightLimit;
    private Context mContext;
    private Scroller mScroller;

    public LauncherViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        width = getResources().getDisplayMetrics().widthPixels;
        mScroller = new Scroller(mContext);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(mContext);
        touchSlop = viewConfiguration.getScaledTouchSlop();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int x=mScroller.getCurrX();
            int y=mScroller.getCurrY();
            scrollTo(x, y);
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        Log.e(TAG, "onMeasure() getChildCount = "+ widthMeasureSpec);
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                int left = i * childView.getMeasuredWidth();
                int top = 0;
                int right = (i + 1) * childView.getMeasuredWidth();
                int bottom = childView.getMeasuredHeight();
                childView.layout(left, top, right, bottom);
            }
            leftLimit = getChildAt(0).getLeft();
            rightLimit = getChildAt(childCount - 1).getRight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getRawX();
                lastX = downX;
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = ev.getRawX();
                float moveDistance = Math.abs(moveX - lastX);
                lastX = moveX;
                if (moveDistance > touchSlop) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "down");
                return true;
                //break;
            case MotionEvent.ACTION_MOVE:
                moveX = ev.getRawX();
                int moveDistanceX = (int) (lastX - moveX);
                scrollX = getScrollX();
                if (scrollX + moveDistanceX < leftLimit) {
                    scrollTo(leftLimit, 0);
                    return true;
                }

                if (scrollX + moveDistanceX + width > rightLimit) {
                    scrollTo(rightLimit - width, 0);
                    return true;
                }
                scrollBy(moveDistanceX, 0);
                lastX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                scrollX = getScrollX();
                int index = (scrollX + width / 2) / width;
                int distanceX = width * index - scrollX;
                mScroller.startScroll(scrollX, 0, distanceX, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(ev);
    }
}
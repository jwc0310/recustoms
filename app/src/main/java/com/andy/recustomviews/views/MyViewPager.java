package com.andy.recustomviews.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import com.andy.recustomviews.R;

public class MyViewPager extends ViewGroup{

    private Context context;
    private int[] images = {
            R.mipmap.bg1,
            R.mipmap.bg2,
            R.mipmap.bg3,
            R.mipmap.bg4,
    };
    GestureDetector gestureDetector;
    Scroller scroller;

    public MyViewPager(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).layout(i * getWidth(), t, (i + 1) * getWidth(), b);
        }
    }

    int scrollX;
    int position;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将触摸事件传递手势识别器
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(event);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.e("ACTION_MOVE", "scrollX=" + getScrollX());
                    scrollX = getScrollX();
                    //滑动的距离+屏幕一半， 除以屏幕， 就是当前图片的pos
                    //如果滑动距离>屏幕一半 position+1
                    position = (scrollX + getWidth() / 2) /getWidth();

                    if (position >= images.length) {
                        position = images.length - 1;
                    }

                    if (position < 0) {
                        position = 0;
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    //绝对滑动，直接划到制定的x,y位置 较迟钝
                    //scrollTo(position * getWidth(), 0);
                    scroller.startScroll(scrollX, 0, -(scrollX - position * getWidth()), 0);
                    invalidate();
                    break;
            }

            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    private void init() {
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(images[i]);
            addView(imageView);
        }

        scroller = new Scroller(getContext());

        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //相对滑动：X方向滑动多少距离，view就跟着滑动多少距离
                if (getScrollX() + distanceX > (images.length - 1) * getWidth()) {
                    distanceX = (images.length - 1) * getWidth() - getScrollX();
                } else if (getScrollX() + distanceX <= 0) {
                    distanceX = getScrollX();
                }
                scrollBy((int)distanceX, 0);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }


        });
    }
}

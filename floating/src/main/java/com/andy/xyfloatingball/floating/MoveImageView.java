package com.andy.xyfloatingball.floating;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.andy.xyfloatingball.R;

/**
 * Created by Administrator on 2017/1/13.
 */
public class MoveImageView extends ImageView implements ValueAnimator.AnimatorListener, ValueAnimator.AnimatorUpdateListener{

    protected Context context;
    protected Point startPosition;
    protected Point endPosition;

    public MoveImageView(Context context) {
        this(context, null);
    }

    public MoveImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setImageResource(R.drawable.app_loading_icon);
        setVisibility(INVISIBLE);
    }

    public void setStartPosition(Point startPosition){
        this.startPosition = new Point(startPosition.x, startPosition.y);
    }

    public void setEndPosition(Point endPosition){
        this.endPosition = new Point(endPosition.x + getWidth() / 2, endPosition.y);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        Point point = (Point) valueAnimator.getAnimatedValue();
        setX(point.x);
        setY(point.y);
    }

//    public void setControlPoint(Point controlPoint){
//        this.controlPoint = controlPoint;
//    }
//    private Point controlPoint;

    public void startBeizerAnimation() {
        if (startPosition == null || endPosition == null) return;
        Point controlPoint = new Point((startPosition.x + endPosition.x)/ 2, endPosition.y - getHeight() * 2);
        BezierEvaluator bezierEvaluator = new BezierEvaluator(controlPoint);
        ValueAnimator anim = ValueAnimator.ofObject(bezierEvaluator, startPosition, endPosition);
        anim.addUpdateListener(this);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewGroup viewGroup = (ViewGroup) getParent();
                viewGroup.removeView(MoveImageView.this);
            }
        });
        anim.setInterpolator(new AccelerateDecelerateInterpolator());

        ValueAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 0.3f);
        ValueAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 0.3f);
//        ValueAnimator rolate = ObjectAnimator.ofFloat(this, "rotation", 0, 720);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(this);
        animatorSet.play(anim).with(scaleX).with(scaleY);
        animatorSet.setDuration(800);
        animatorSet.start();
    }

    @Override
    public void onAnimationStart(Animator animator) {
        setVisibility(VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animator animator) {
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    public class BezierEvaluator implements TypeEvaluator<Point> {

        private Point controllPoint;

        public BezierEvaluator(Point controllPoint) {
            this.controllPoint = controllPoint;
        }

        @Override
        public Point evaluate(float t, Point startValue, Point endValue) {
            int x = (int) ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * controllPoint.x + t * t * endValue.x);
            int y = (int) ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * controllPoint.y + t * t * endValue.y);
            return new Point(x, y);
        }
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
}

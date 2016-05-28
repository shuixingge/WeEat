package com.bupt.weeat.view.customView;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import com.bupt.weeat.R;


public class CircleAnimView extends View {
    private int radius;
    public static final float RADIUS = 70f;
    Context context;

    private Point currentPoint;

    private Paint mPaint;

    private Paint textPaint;
    private boolean revealStart;

    public CircleAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
        this.context=context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(getWidth() / 2, getHeight() / 2);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
        if (revealStart){
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
        canvas.drawText("邮", x-RADIUS/3, y+RADIUS/5, textPaint);

    }

    private void startAnimation() {
        int startX = getWidth() / 2;
        int startY = getHeight() / 2;

        Point startPoint = new Point(startX, startY);
        Point endPoint = new Point(getWidth() / 2, getHeight() - 3* RADIUS);
        AnimatorSet set = new AnimatorSet();
        ValueAnimator anim1 = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim1.setDuration(2000);
        anim1.setInterpolator(new AccelerateDecelerateInterpolator());
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });

        ValueAnimator anim2 = ValueAnimator.ofObject(new PointEvaluator(), endPoint, startPoint);
        anim2.setInterpolator(new AccelerateDecelerateInterpolator());
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }


        });


        anim2.setDuration(1000);
        anim2.setStartDelay(3000);

        set.play(anim2).after(anim1);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                revealStart=true;
                startReveal();
            }
        });
    }
    public  void startReveal() {
        setVisibility(VISIBLE);
        //计算对角线 sqrt 开平方,pow 计算x的n次方
        int maxRadius = (int) (Math.sqrt(Math.pow(getHeight(), 2)+ Math.pow(getWidth(), 2)));
        ObjectAnimator revealAnimator = ObjectAnimator.ofInt(this, "radius", 70,maxRadius).setDuration(300);
        revealAnimator.setInterpolator(new AccelerateInterpolator());
        revealAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (null != callback) {
                    callback.onRevealEnd();
                }
            }
        });
        revealAnimator.start();
    }
    public void setRadius(int radius) {
        this.radius = radius;
        //Notice 调用invalidate 之后 onDraw才会被调用
        invalidate();
    }
    public int getRadius() {
        return radius;
    }
    public void setCallback(Callback callback) {
        this.callback = callback;
    }
    private Callback callback;
    public interface Callback{
        void onRevealEnd();
    }
}
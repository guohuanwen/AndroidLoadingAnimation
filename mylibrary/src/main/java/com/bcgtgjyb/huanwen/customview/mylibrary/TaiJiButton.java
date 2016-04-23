package com.bcgtgjyb.huanwen.customview.mylibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by huanwen on 2015/9/1.
 */
public class TaiJiButton extends View {
    private final String TAG = "TaiJiButton";
    private float rolate = 360;
    private Paint mWhitePaint;
    private Paint mBlackPaint;
    private Paint backgroundPaint;
    private Paint arcPaint;
    private Rect mRect;
    private RectF backRectF;
    private ValueAnimator animator;
    private ValueAnimator smallAnimator;
    private ValueAnimator lineToArcAnimator;
    private ValueAnimator arcToLineAnimator;
    private boolean init = false;
    private float ratio = 0;
    private boolean stopAnimator = false;
    private float raduis = 0;
    private int velocity = 1000;
    private int color1 = Color.BLACK;
    private int color2 = Color.GRAY;
    private float R = 0;
    private float raduisX1 = 0;
    private float raduisY1 = 0;
    private float raduisX2 = 0;
    private float raduisY2 = 0;
    private RectF rectF;
    private RectF rectF2;
    private boolean loading = false;


    public TaiJiButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mWhitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBlackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect = new Rect();
        backRectF = new RectF();
        rectF = new RectF();
        rectF2 = new RectF();
    }

    /**
     * set the animation start
     */
    public void startLoad() {
        loading = true;
        stopAnimator = false;
        setLineToArcAnimator();
        setAppearAnimator();
        loading();
    }

    /**
     * set the animation stop
     */
    public void stopLoad() {
        loading = false;
        this.stopAnimator = true;
        setArcToLineAnimator();
        setDisAppearAnimator();
    }

    public boolean isLoading(){
        return loading;
    }

    /**
     * set the velocity of animation
     *
     * @param velocity
     */
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    /**
     * set the view's color
     *
     * @param color1
     * @param color2
     */
    public void setColor(int color1, int color2) {
        this.color1 = color1;
        this.color2 = color2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //初始化
        if (!init) {
            mWhitePaint.setColor(color2);
            mBlackPaint.setColor(color1);
            R = getWidth() / 2 - 5;
            raduisX1 = getWidth() / 2 + R / 2;
            raduisY1 = getHeight() / 2;
            raduisX2 = getWidth() / 2 - R / 2;
            raduisY2 = getHeight() / 2;
            init=true;
        }
        //旋转刷新
        if (animator != null && animator.isRunning()) {
            float ratio = (Float) animator.getAnimatedValue();
            canvas.rotate(rolate * ratio, getWidth() / 2, getHeight() / 2);
            invalidate();
        }

        //画出背景半圆
        backRectF.set(getWidth() / 2 - R, getHeight() / 2 - R, getWidth() / 2 + R, getHeight() / 2 + R);
        mRect.set(getWidth(), getHeight(), 0, 0);
        canvas.drawArc(backRectF, 0, 180, true, mWhitePaint);
        canvas.drawArc(backRectF, 0, -180, true, mBlackPaint);


//        Log.d(TAG, "onDraw() returned: " + ratio+"  "+(getHeight() / 2 - ratio * R / 2)+"   "+(getHeight() / 2 + ratio * R / 2));
        //椭圆渐变
        rectF.set(getWidth() / 2 - R, getHeight() / 2 - ratio *R / 2, getWidth() / 2, getHeight() / 2 + ratio *R / 2);
        rectF2.set(getWidth() / 2, getHeight() / 2 - ratio * R / 2, getWidth() / 2 + R, getHeight() / 2 + ratio * R / 2);
        canvas.drawArc(rectF, 0, -180, true, mWhitePaint);
        canvas.drawArc(rectF2, 0, 180, true, mBlackPaint);
        if ((lineToArcAnimator != null && lineToArcAnimator.isRunning())) {
            ratio = (Float) lineToArcAnimator.getAnimatedValue();
            invalidate();
        }
        if (arcToLineAnimator != null && arcToLineAnimator.isRunning()) {
            ratio = (Float) arcToLineAnimator.getAnimatedValue();
            invalidate();
        }

        //圆心放大
        canvas.drawCircle(raduisX1, raduisY1, raduis * R / 5, mWhitePaint);
        canvas.drawCircle(raduisX2, raduisY2, raduis * R / 5, mBlackPaint);
        if (smallAnimator != null && smallAnimator.isRunning()) {
            raduis = (float) smallAnimator.getAnimatedValue();
            invalidate();
        }
    }

    private void loading() {
        if (animator == null) {
            loadAnimator();
        }else {
            animator.start();
        }
        if (!stopAnimator) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    loading();
                }
            }, animator.getDuration());
        }
    }

    private void loadAnimator() {
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(velocity);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        invalidate();
    }

    private void setLineToArcAnimator() {
        Log.i(TAG, "setLineToArcAnimator ");
        lineToArcAnimator = ValueAnimator.ofFloat(0f, 1f);
        lineToArcAnimator.setDuration(velocity);
        lineToArcAnimator.setInterpolator(new LinearInterpolator());
        lineToArcAnimator.start();
        invalidate();
    }

    private void setArcToLineAnimator() {
        Log.i(TAG, "setArcToLineAnimator ");
        arcToLineAnimator = ValueAnimator.ofFloat(1f, 0f);
        arcToLineAnimator.setDuration(velocity);
        arcToLineAnimator.setInterpolator(new LinearInterpolator());
        arcToLineAnimator.start();
        invalidate();
    }

    private void setAppearAnimator() {
        smallAnimator = ValueAnimator.ofFloat(0f, 1f);
        smallAnimator.setDuration(velocity);
        smallAnimator.start();
        invalidate();
    }

    private void setDisAppearAnimator() {
        smallAnimator = ValueAnimator.ofFloat(1f, 0f);
        smallAnimator.setDuration(velocity);
        smallAnimator.start();
        invalidate();
    }
}

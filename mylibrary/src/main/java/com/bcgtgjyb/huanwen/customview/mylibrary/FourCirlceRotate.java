package com.bcgtgjyb.huanwen.customview.mylibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by guohuanwen on 2015/10/6.
 */
public class FourCirlceRotate extends View {
    private String TAG = "FourCirlceRotate";
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private Paint paint4;
    private Paint paint5;
    private RectF rectF1;
    private RectF rectF2;
    private RectF rectF3;
    private RectF rectF4;
    private float[] c1;
    private float[] c2;
    private float[] c3;
    private float[] c4;
    private ValueAnimator valueAnimatorX1;
    private ValueAnimator valueAnimatorY1;
    private ValueAnimator valueAnimatorX2;
    private ValueAnimator valueAnimatorY2;
    private int delay = 500;
    private int time = 0;
    private boolean show1 = false;
    private boolean show2 = false;
    private boolean show3 = false;
    private boolean show4 = false;

    public FourCirlceRotate(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint1 = new Paint();
        paint2 = new Paint();
        paint3 = new Paint();
        paint4 = new Paint();
        paint5 = new Paint();
        paint1.setColor(Color.parseColor("#ff0099cc"));
        paint2.setColor(Color.parseColor("#ff669900"));
        paint3.setColor(Color.parseColor("#ffcc0000"));
        paint4.setColor(Color.parseColor("#ffaa66cc"));
        paint5.setColor(Color.parseColor("#ffffbb33"));
        rectF1 = new RectF();
        rectF2 = new RectF();
        rectF3 = new RectF();
        rectF4 = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        rectF1.set(0, 0, getWidth() / 2, getHeight() / 2);
//        rectF2.set(getWidth() / 2, 0, 0, getHeight() / 2);
//        rectF3.set(getWidth() / 2, getHeight() / 2, getWidth() / 2, 0);
//        rectF4.set(getWidth() / 2, getHeight() / 2, 0, 0);
//        valueAnimatorX=makeXValueAnimation(getWidth()/4,getWidth()-getWidth()/4,getWidth()-getWidth()/4,getWidth()/4);
//        valueAnimatorY = makeYValueAnimation(getHeight() / 4, getHeight(), getHeight() - getHeight()/4,getHeight()-getHeight()/4);
        if (null == valueAnimatorX1) {
            valueAnimatorX1 = makeXValueAnimation(new float[]{getWidth() / 4, getWidth() - getWidth() / 4}, 0);
            valueAnimatorY1 = makeYValueAnimation(new float[]{getHeight() / 4, getHeight() - getHeight() / 4}, delay);
            valueAnimatorX2 = makeXValueAnimation(new float[]{getWidth() - getWidth() / 4, getWidth() / 4}, delay * 2);
            valueAnimatorY2 = makeYValueAnimation(new float[]{getHeight() - getHeight() / 4, getHeight() / 4}, delay * 3);
        }

        c2 = new float[]{getWidth() - getWidth() / 4, getHeight() / 4};
        c3 = new float[]{getWidth() - getWidth() / 4, getHeight() - getHeight() / 4};
        c4 = new float[]{getWidth() / 4, getHeight() - getHeight() / 4};
        float x = getWidth() / 4;
        float y = getHeight() / 4;
        if (valueAnimatorX1.isRunning()) {
            x = (float) valueAnimatorX1.getAnimatedValue();
            y = getHeight() / 4;
            time = 1;
        } else if (valueAnimatorY1.isRunning()) {
            x = getWidth() - getWidth() / 4;
            y = (float) valueAnimatorY1.getAnimatedValue();
            time = 2;
        } else if (valueAnimatorX2.isRunning()) {
            x = (float) valueAnimatorX2.getAnimatedValue();
            y = getHeight() - getHeight() / 4;
            time = 3;
        } else if (valueAnimatorY2.isRunning()) {
            x = getWidth() / 4;
            y = (float) valueAnimatorY2.getAnimatedValue();
            time = 4;
        }
        c1 = new float[]{x, y};



        switch (time) {
            case 1:
                if (!show1) {
                    canvas.drawCircle(getWidth() / 4, getHeight() / 4, getWidth() / 6, paint5);
                    show1 = true;
                } else {

                }
                break;
            case 2:
                if (!show2) {
                    canvas.drawCircle(getWidth() / 4, getHeight() / 4, getWidth() / 6, paint5);
                    canvas.drawCircle(c2[0], c2[1], getWidth() / 6, paint2);
                    show2 = true;
                }
                break;
            case 3:
                if (!show3) {
                    canvas.drawCircle(getWidth() / 4, getHeight() / 4, getWidth() / 6, paint5);
                    canvas.drawCircle(c2[0], c2[1], getWidth() / 6, paint2);
                    canvas.drawCircle(c3[0], c3[1], getWidth() / 6, paint3);
                    show3 = true;
                }
                break;
            case 4:
                if (!show4) {
                    canvas.drawCircle(getWidth() / 4, getHeight() / 4, getWidth() / 6, paint5);
                    canvas.drawCircle(c2[0], c2[1], getWidth() / 6, paint2);
                    canvas.drawCircle(c3[0], c3[1], getWidth() / 6, paint3);
                    canvas.drawCircle(c4[0], c4[1], getWidth() / 6, paint4);
                    show4 = true;
                }
                break;
        }
        canvas.drawCircle(c1[0], c1[1], getWidth() / 6, paint1);
//        Log.d(TAG, "onDraw() returned: " + x+"   "+y);


        if (valueAnimatorX1.isStarted() || valueAnimatorY1.isStarted() || valueAnimatorY2.isStarted() || valueAnimatorX2.isStarted()) {
//            Log.d(TAG, "onDraw() returned: " + valueAnimatorY1);
            invalidate();
        }
    }

    private ValueAnimator makeXValueAnimation(float[] value, int delay) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(value[0], value[1]);
        valueAnimator.setDuration(this.delay);
        valueAnimator.setStartDelay(delay);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
        return valueAnimator;
    }

    private ValueAnimator makeYValueAnimation(float[] value, int delay) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(value[0], value[1]);
        valueAnimator.setDuration(this.delay);
        valueAnimator.setStartDelay(delay);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
        return valueAnimator;
    }
}

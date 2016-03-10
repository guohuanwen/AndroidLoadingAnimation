package com.bcgtgjyb.huanwen.customview.mylibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by guohuanwen on 2015/10/6.
 */
public class PointToLineRotate extends View {
    private Paint paint;
    private float pi = (float) Math.PI;
    private float startAngle = 0;
    private float angle = 0;
    private float addAngle = 0;
    private ValueAnimator endValue;
    private ValueAnimator startValue;
    private int width = 10;

    public PointToLineRotate(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画线
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);
        paint.setColor(Color.parseColor("#ffaa66cc"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF();
        rectF.set(width / 2, width / 2, getWidth() - width / 2, getHeight() - width / 2);
        if (null == startValue) {
            loading();
        }
        angle = -(float) endValue.getAnimatedValue();
        startAngle = (float) startValue.getAnimatedValue();
        canvas.drawArc(rectF, startAngle, angle, false, paint);

        if (endValue.isRunning() || startValue.isRunning()) {
            invalidate();
        }
    }

    private void loading() {
        if (startValue == null) {
            startValue = makeValueAnimator(0, 360);
        }else {
            startValue.start();
        }
        if (endValue == null) {
            endValue = makeEndValueAnimator(30, 60, 120, 90, 60, 30);
        }else {
            endValue.start();
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                loading();
                invalidate();
            }
        }, startValue.getDuration());
    }


    private ValueAnimator makeValueAnimator(float... value) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(value);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.start();
        return valueAnimator;
    }

    private ValueAnimator makeEndValueAnimator(float... value) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(value);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.start();
        return valueAnimator;
    }


    private float circleR;

    private ValueAnimator makeCircleData(float[] startCoordinate, float[] RCoordinate, int delay) {
        float x1 = startCoordinate[0];
        float y1 = startCoordinate[1];
        float x0 = RCoordinate[0];
        float y0 = RCoordinate[1];
//        Log.i(TAG, "getCircleData x y: " + x1+"  ,"+y1+"  x0  "+x0+ " y0  "+y0);
        circleR = (float) Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
        float param = (float) (Math.abs(y1 - y0) / circleR);
        if (param < -1.0) {
            param = -1.0f;
        } else if (param > 1.0) {
            param = 1.0f;
        }
        float a = (float) Math.asin(param);
        if (x1 >= x0 && y1 >= y0) {
            a = a;
        } else if (x1 < x0 && y1 >= y0) {
            a = pi - a;
        } else if (x1 < x0 && y1 < y0) {
            a = a + pi;
        } else {
            a = 2 * pi - a;
        }
        ValueAnimator circleAnimator = ValueAnimator.ofFloat(a, a + 2 * pi);
        circleAnimator.setDuration(1500);
        circleAnimator.setInterpolator(new LinearInterpolator());
        circleAnimator.setStartDelay(delay);
        circleAnimator.start();
        return circleAnimator;
    }
}

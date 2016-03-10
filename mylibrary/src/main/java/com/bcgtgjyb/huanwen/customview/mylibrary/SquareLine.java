package com.bcgtgjyb.huanwen.customview.mylibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by bigwen on 2015/12/31.
 */
public class SquareLine extends View {
    private Context mContext;
    private Paint paint;
    private ValueAnimator valueA;
    private int delayTime=500;
    private boolean initView = false;

    public SquareLine(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SquareLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#259b24"));
        paint.setStrokeWidth(10);

    }
    float f = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!initView){
            start();
            initView=true;
        }
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int length = width/9;

        if (valueA.isRunning()) {
            f = (float) valueA.getAnimatedValue();
        }
        canvas.drawLines(new float[]{yValue(f, length), height / 2, length * 4 + yValue(f, length), height / 2}, paint);
        canvas.drawLines(new float[]{yValue(f, length) - width, height / 2, length * 4 + yValue(f, length) - width, height / 2}, paint);
        if (valueA.isRunning()) {
            invalidate();
        }
    }

    private void start(){
        if (valueA == null) {
            valueA = getValueAnimator();
        }else {
            valueA.start();
        }
        invalidate();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
                invalidate();
            }
        }, valueA.getDuration());
    }

    private ValueAnimator getValueAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.setDuration(delayTime);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
        invalidate();
        return valueAnimator;
    }

    private float yValue(float x, int length) {
        float s = length / getMeasuredWidth();
        float allLength = getMeasuredWidth();
        return allLength/ 1 * x;
    }
}

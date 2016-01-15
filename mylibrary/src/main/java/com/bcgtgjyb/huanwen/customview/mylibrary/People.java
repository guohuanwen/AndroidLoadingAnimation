package com.bcgtgjyb.huanwen.customview.mylibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by bigwen on 2015/12/31.
 * 思考中，一个人的行走动画
 */
public class People extends View {
    private Paint paint;
    private int width = 0;
    private int height = 0;
    private boolean init = false;
    private int leg_left1 = 0;
    private int leg_left2 = 0;
    private int leg_right1 = 0;
    private int leg_right2 = 0;
    private ValueAnimator leg_animator;
    private float pi = (float) Math.PI;
    private float legRadius = pi / 4;
    private float[] leg_center_cirlcle;

    public People(Context context) {
        super(context);
        init();
    }

    public People(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!init) {
            init = true;
            width = getWidth();
            height = getHeight();
            leg_left1 = 1 / 3 * width * 1 / 2;
            leg_left2 = leg_left1;
            leg_right1 = leg_left1;
            leg_right2 = leg_left1;
            leg_center_cirlcle = new float[]{width/2,width*2/3};
            start();
        }
        float[] end = getCircleXY(leg_center_cirlcle, leg_left1, legRadius);
        canvas.drawLine(leg_center_cirlcle[0], leg_center_cirlcle[1], end[0], end[1], paint);

        if (leg_animator.isRunning()) {
            legRadius = (float) leg_animator.getAnimatedValue();
            invalidate();
        }


    }

    private float[] getCircleXY(float[] floats, float R, float audio) {
        float x = (float) Math.cos(audio) * R + floats[0];
        float y = (float) Math.sin(audio) * R + floats[1];
        return new float[]{x, y};
    }

    private void start() {
        leg_animator = ValueAnimator.ofFloat(pi / 4, pi * 3 / 4);
        leg_animator.setDuration(2000);
        leg_animator.setInterpolator(new LinearInterpolator());
        leg_animator.start();
    }
}

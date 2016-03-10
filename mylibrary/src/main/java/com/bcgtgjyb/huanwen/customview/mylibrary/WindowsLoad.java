package com.bcgtgjyb.huanwen.customview.mylibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by guohuanwen on 2015/10/5.
 */
public class WindowsLoad extends View {
    private float pi = (float) Math.PI;
    private String TAG = "WindowsLoad";
    private Paint paint;
    private int R;
    private float circleR;
    private ValueAnimator circleAnimator1;
    private ValueAnimator circleAnimator2;
    private ValueAnimator circleAnimator3;
    private boolean init = true;
    float x1, x2, x3, y1, y2, y3;

    public WindowsLoad(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#ffff4444"));
        R = 10;
    }


    float[] circleCentre;
    float[] start1;
    float[] start2;
    float[] start3;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //初始化
        if (init) {
            circleCentre = new float[]{getWidth() / 2, getHeight() / 2};
            start1 = new float[]{getWidth() / 2, R};
            start2 = onCiecleCoordinate(-0.5f, start1, circleCentre);
            start3 = onCiecleCoordinate(-0.5f, start2, circleCentre);
            init = false;
            loading();
        }
//        canvas.drawCircle(start1[0], start1[1], R, paint);
//        Log.d(TAG, "onDraw() returned: "+ start1[0]+"   "+start1[1] +"   "+ start2[0]+"   "+start2[1]);

        //第一个点初始位置
        if (!circleAnimator1.isRunning()) {
            canvas.drawCircle(start1[0], start1[1], R, paint);
        }
        //第二个点初始位置
        if (!circleAnimator2.isRunning()) {
            canvas.drawCircle(start2[0], start2[1], R, paint);
        }
        //第三个点初始位置
        if (!circleAnimator3.isRunning()) {
            canvas.drawCircle(start3[0], start3[1], R, paint);
        }

        if (circleAnimator1.isRunning()) {
            x1 = (float) (circleCentre[0] + circleR * Math.cos((float) circleAnimator1.getAnimatedValue()));
            y1 = (float) (circleCentre[1] + circleR * Math.sin((float) circleAnimator1.getAnimatedValue()));
            canvas.drawCircle(x1, y1, R, paint);
        }
        if (circleAnimator2.isRunning()) {
            x2 = (float) (circleCentre[0] + circleR * Math.cos((float) circleAnimator2.getAnimatedValue()));
            y2 = (float) (circleCentre[1] + circleR * Math.sin((float) circleAnimator2.getAnimatedValue()));
            canvas.drawCircle(x2, y2, R, paint);
        }
        if (circleAnimator3.isRunning()) {
            x3 = (float) (circleCentre[0] + circleR * Math.cos((float) circleAnimator3.getAnimatedValue()));
            y3 = (float) (circleCentre[1] + circleR * Math.sin((float) circleAnimator3.getAnimatedValue()));
            canvas.drawCircle(x3, y3, R, paint);
        }
        if (circleAnimator1.isRunning() || circleAnimator2.isRunning() || circleAnimator3.isRunning()) {
            invalidate();
        }
    }


    private void loading() {
        circleAnimator1 = getCircleData(start1, circleCentre, 0);
        circleAnimator1.start();
        circleAnimator2 = getCircleData(start2, circleCentre, 300);
        circleAnimator2.start();
        circleAnimator3 = getCircleData(start3, circleCentre, 600);
        circleAnimator3.start();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                loading();
                invalidate();
            }
        }, circleAnimator3.getDuration() + 600);
    }

    private SlowToQuick slowToQuick = new SlowToQuick();

    private ValueAnimator getCircleData(float[] startCoordinate, float[] RCoordinate, int delay) {
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
        circleAnimator.setInterpolator(slowToQuick);
        circleAnimator.setStartDelay(delay);
        return circleAnimator;
    }

    //获取同一个圆上，间隔固定角度的点坐标
    private float[] onCiecleCoordinate(float angle, float[] start, float[] center) {
        float x1 = start[0];
        float y1 = start[1];
        float x0 = center[0];
        float y0 = center[1];
        float R = (float) Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
        float param = (float) (Math.abs(y1 - y0) / R);
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
        float x = (float) (center[0] + R * Math.cos(a + angle));
        float y = (float) (center[1] + R * Math.sin(a + angle));
        return new float[]{x, y};
    }

    class SlowToQuick implements Interpolator {
        @Override
        public float getInterpolation(float input) {
            return input * input;
        }
    }
}

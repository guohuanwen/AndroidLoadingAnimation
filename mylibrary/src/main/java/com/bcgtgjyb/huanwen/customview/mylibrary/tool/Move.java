package com.bcgtgjyb.huanwen.customview.mylibrary.tool;

import android.graphics.RectF;
import android.util.Log;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by bigwen on 2016/2/29.
 */
public class Move {

    private String TAG = Move.class.getName();
    //默认1秒
    private static final int TIME = 1000;
    private ValueAnimator vAX = null;
    private ValueAnimator vAY = null;
    public BWCallback bwCallback;


    public Move() {
    }

    public RectF xy(RectF rectF, int x, int y) {
        return xy(rectF, x, y, TIME);
    }


    public float[] xy(float coodinateX, float coodinateY, int x, int y, int time) {
        float[] xy = new float[2];
        if (vAX == null) {
            vAX = BWValueAnimator.getValueAnimator(x, time);
            vAX.start();
        }
        if (vAY == null) {
            vAY = BWValueAnimator.getValueAnimator(y, time);
            vAY.start();
        }
        if (!vAX.isRunning()) {
            float[] endXY = new float[2];
            endXY[0] = coodinateX + x;
            endXY[1] = coodinateY + y;
            vAX = null;
            vAY = null;
            if (bwCallback != null) bwCallback.onFinish();
            return endXY;
        }
        float xx = (float) vAX.getAnimatedValue();
        float yy = (float) vAY.getAnimatedValue();
        Log.i(TAG, "xy " + xx + "   " + yy);
        xy[0] = coodinateX + xx;
        xy[1] = coodinateY + yy;
        return xy;
    }

    public RectF xy(RectF rectF, int x, int y, int time) {
        if (vAX == null) {
            vAX = BWValueAnimator.getValueAnimator(x, time);
            vAX.start();
        }
        if (vAY == null) {
            vAY = BWValueAnimator.getValueAnimator(y, time);
            vAY.start();
        }
        if (!vAX.isRunning()) {
            RectF f = new RectF(0, 0, 0, 0);
            if (bwCallback != null) bwCallback.onFinish(circleVA);
            return f;
        }
        float xx = (float) vAX.getAnimatedValue();
        float yy = (float) vAY.getAnimatedValue();
        rectF.left = rectF.left + xx;
        rectF.right = rectF.right + xx;
        rectF.top = rectF.top + yy;
        rectF.bottom = rectF.bottom + yy;
        return rectF;
    }


    private ValueAnimator circleVA = null;
    private static final int CIRCLEDEGREE = 360;

    public float[] circleMove(int radioX, int radioY, int x, int y, int time, int degree) {
        float r = (float) Math.sqrt((radioX - x) * (radioX - x) + (radioY - y) * (radioY - y));
        float nowDegree = (float) Math.atan((y - radioY) / (x - radioX)) * 360;
        if (circleVA == null) {
            circleVA = BWValueAnimator.getValueAnimator(degree, time);
        }
        if (!circleVA.isRunning()) {
            float[] end = new float[2];
            float endDegree = nowDegree + degree;
            end[0] = (float) (r * Math.sin(endDegree / CIRCLEDEGREE));
            end[1] = (float) (r * Math.cos(endDegree / CIRCLEDEGREE));
            if (bwCallback != null) bwCallback.onFinish(circleVA);
            return end;
        } else {
            float[] end = new float[2];
            float endDegree = (float) circleVA.getAnimatedValue() + degree;
            end[0] = (float) (r * Math.sin(endDegree / CIRCLEDEGREE));
            end[1] = (float) (r * Math.cos(endDegree / CIRCLEDEGREE));
            return end;
        }
    }

    public void setCallback(BWCallback callback) {
        this.bwCallback = callback;
    }

    public boolean isRunning() {
        if (circleVA != null && circleVA.isRunning()) {
            return true;
        }
        if (vAX != null && vAX.isRunning()) {
            return true;
        }
        if (vAY != null && vAY.isRunning()) {
            return true;
        }
        return false;
    }

    public void clear() {
        circleVA = null;
        vAY = null;
        vAX = null;
    }
}

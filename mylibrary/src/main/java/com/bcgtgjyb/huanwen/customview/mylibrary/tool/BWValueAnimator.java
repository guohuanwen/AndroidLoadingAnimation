package com.bcgtgjyb.huanwen.customview.mylibrary.tool;

import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by bigwen on 2016/3/1.
 */
public class BWValueAnimator {
    public static ValueAnimator getValueAnimator(float param,int time){
        ValueAnimator valueX = ValueAnimator.ofFloat(0,param);
        valueX.setInterpolator(new LinearInterpolator());
        valueX.setDuration(time);
        return valueX;
    }

    public static ValueAnimator getValueAnimator(float start,float end, int time){
        ValueAnimator valueX = ValueAnimator.ofFloat(start,end);
        valueX.setInterpolator(new LinearInterpolator());
        valueX.setDuration(time);
        return valueX;
    }
}

package com.bcgtgjyb.huanwen.customview.mylibrary.tool;


import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by bigwen on 2016/3/1.
 */
public interface BWCallback {
    void onFinish(ValueAnimator... valueAnimator);
    void onFinish();
}

package com.bcgtgjyb.huanwen.customview.mylibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by bigwen on 2016/3/2.
 */
public class Loding58View extends LinearLayout {
    private Context mContext;
    private LinearLayout addView;
    private Loading58Path view;

    public Loding58View(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public Loding58View(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private AnimatorSet riseSet;
    private AnimatorSet dropSet;
    private int run = 0;

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.loding58view, this);
        view = (Loading58Path) findViewById(R.id.loading58);
        start();
    }

    private AnimatorSet rolation;
    private AnimatorSet aList = new AnimatorSet();

    private void start() {
        riseSet = riseVA(0, -100);
        rolation = rotation(90);
        aList.playTogether(riseSet, rolation);
        aList.setDuration(500);
        aList.start();
        view.start();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                dropSet = dropVA(0, 100);
            }
        }, 500);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                riseSet = riseVA(0, -100);
                rolation = rotation(120);
                aList.playTogether(riseSet, rolation);
                aList.setDuration(500);
                aList.start();
            }
        }, 1500);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                dropSet = dropVA(0, 100);
            }
        }, 2000);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                riseSet = riseVA(0, -100);
                rolation = rotation(90);
                aList.playTogether(riseSet, rolation);
                aList.setDuration(500);
                aList.start();
            }
        }, 3000);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                dropSet = dropVA(0, 100);
            }
        }, 3500);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }, 4500);

    }

    private int startX;
    private int startY;

    private ValueAnimator valueRise;

    private AnimatorSet riseVA(float x, float y) {
        AnimatorSet animatorSet = new AnimatorSet();
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("translationX", x);
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("translationY", y);
        animatorSet.play(ObjectAnimator.ofPropertyValuesHolder(view, p1, p2));
        //setDuration在play后面设置
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    private AnimatorSet dropVA(float x, float y) {
        AnimatorSet animatorSet = new AnimatorSet();
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("translationX", x);
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("translationY", y);
        animatorSet.play(ObjectAnimator.ofPropertyValuesHolder(view, p1, p2));
        //setDuration在play后面设置
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.start();
        return animatorSet;
    }

    private AnimatorSet rotation(float param) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(
                ObjectAnimator.ofFloat(view, "rotation", param)
        );
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

}

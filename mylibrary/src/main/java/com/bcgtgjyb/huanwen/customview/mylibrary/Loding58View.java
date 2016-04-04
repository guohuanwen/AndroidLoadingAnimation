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

    private AnimatorSet rolation90,rolation120,rolation180;
    private AnimatorSet aList1,aList2,aList3,aList4;
    private void start() {
        if (aList1 == null) {
            aList1 = new AnimatorSet();
            riseSet = riseVA(0, -100);
            rolation90 = rotation(240);
            aList1.playTogether(riseSet, rolation90);
            aList1.setDuration(500);
            aList1.start();
        }else {
            aList1.start();
        }
        view.start();

        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dropSet == null) {
                    dropSet = dropVA(0, 100);
                }else {
                    dropSet.start();
                }
                view.nextPath();
            }
        }, 500);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (aList2 == null) {
                    aList2 = new AnimatorSet();
                    riseSet = riseVA(0, -100);
                    rolation120 = rotation(90);
                    aList2.playTogether(riseSet, rolation120);
                    aList2.setDuration(500);
                    aList2.start();
                }else {
                    aList2.start();
                }
            }
        }, 1500);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dropSet == null) {
                    dropSet = dropVA(0, 100);
                }else {
                    dropSet.start();
                }
                view.nextPath();
            }
        }, 2000);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (aList3 == null) {
                    aList3 = new AnimatorSet();
                    riseSet = riseVA(0, -100);
                    rolation180 = rotation(180);
                    aList3.playTogether(riseSet, rolation180);
                    aList3.setDuration(500);
                    aList3.start();
                }else {
                    aList3.start();
                }
            }
        }, 3000);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dropSet == null) {
                    dropSet = dropVA(0, 100);
                }else {
                    dropSet.start();
                }
                view.nextPath();
            }
        }, 3500);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }, 4500);

    }

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

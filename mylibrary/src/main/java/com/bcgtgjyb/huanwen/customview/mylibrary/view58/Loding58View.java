package com.bcgtgjyb.huanwen.customview.mylibrary.view58;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.bcgtgjyb.huanwen.customview.mylibrary.R;
import com.bcgtgjyb.huanwen.customview.mylibrary.tool.ScreenUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;

/**
 * Created by bigwen on 2016/3/2.
 */
public class Loding58View extends LinearLayout {

    private Context mContext;
    private Loading58Path view;
    private int dropHeight = 0;
    private String TAG = Loding58View.class.getName();
    private boolean play = false;
    private AnimatorSet riseSet;
    private AnimatorSet dropSet;
    private AnimatorSet rolation90, rolation120, rolation180;
    private AnimatorSet aList1, aList2, aList3, aList4;
    private int duration = 600;
    private long time = 0;
    private AnimatorSet riseSet1, riseSet2, riseSet3, dropSet1, dropSet2, dropSet3, dropSet4;

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

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.loding58view, this);
        dropHeight = ScreenUtil.dip2px(mContext, 50);
        view = (Loading58Path) findViewById(R.id.loading58);

        initAnimList();
    }

    private void animStart() {
        aList1.start();
        view.start();

        aList1.addListener(new AnimEnd() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                dropSet1.start();
                view.nextPath();
            }
        });

        dropSet1.addListener(new AnimEnd() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd: aList2");
                aList2.start();
            }
        });

        aList2.addListener(new AnimEnd() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd: dropSet");
                dropSet2.start();
                view.nextPath();
            }
        });

        dropSet2.addListener(new AnimEnd() {
            @Override
            public void onAnimationEnd(Animator animation) {
                aList3.start();
            }
        });

        aList3.addListener(new AnimEnd() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dropSet3.start();
                view.nextPath();
            }
        });
        dropSet3.addListener(new AnimEnd() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (play) {
                    aList1.start();
                    view.start();
                }
            }
        });
    }

    private void initAnimList() {
        dropSet = dropVA(0, dropHeight);
        dropSet1 = dropVA(0, dropHeight);
        dropSet2 = dropVA(0, dropHeight);
        dropSet3 = dropVA(0, dropHeight);
        aList1 = new AnimatorSet();
        rolation90 = rotation(240);
        riseSet1 = riseVA(0, -dropHeight);
        aList1.playTogether(riseSet1, rolation90);
        aList1.setDuration(duration);

        aList2 = new AnimatorSet();
        rolation120 = rotation(90);
        riseSet2 = riseVA(0, -dropHeight);
        aList2.playTogether(riseSet2, rolation120);
        aList2.setDuration(duration);

        aList3 = new AnimatorSet();
        rolation180 = rotation(180);
        riseSet3 = riseVA(0, -dropHeight);
        aList3.playTogether(riseSet3, rolation180);
        aList3.setDuration(duration);
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
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        return animatorSet;
    }

    private AnimatorSet rotation(float param) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(
                ObjectAnimator.ofFloat(view, "rotation", param)
        );
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    public void start() {
        play = true;
        animStart();
    }

    public void stop() {
        play = false;
    }

    private class AnimEnd implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}

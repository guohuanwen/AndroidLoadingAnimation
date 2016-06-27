package com.bcgtgjyb.huanwen.customview.mylibrary.star;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.bcgtgjyb.huanwen.customview.mylibrary.R;
import com.bcgtgjyb.huanwen.customview.mylibrary.tool.ScreenUtil;

/**
 * Created by bigwen on 2016/4/23.
 */
public class StarCircle extends LinearLayout {

    private Context mContext;
    private StarView starView1;
    private StarView starView2;
    private int viewWidth;
    private int viewHeight;
    private Thread thread;
    private boolean play = false;
    private float degree = 0;
    private String TAG = StarCircle.class.getName();
    private int padding;
    private double circleDegree;
    private int circleX, circleY, circleR;
    private int[] coordinate1, coordinate2;

    public StarCircle(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public StarCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.star_circle, this);
        starView1 = (StarView) findViewById(R.id.star_view_1);
        starView2 = (StarView) findViewById(R.id.star_view_2);
        padding = ScreenUtil.dip2px(mContext, 8);
        initThread();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getWidth() - ScreenUtil.dip2px(mContext, 30);
        viewHeight = getHeight() - ScreenUtil.dip2px(mContext, 30);
    }

    private void initThread() {
        if (thread != null && thread.isAlive()) {
            return;
        }
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (play) {
                    degree += 0.1f;
                    setXy(degree);
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }
        });
        play = true;
        thread.start();
    }

    private void setXy(float d) {
        coordinate1 = caculateDegree(d);
        coordinate2 = caculateDegree(d);
        post(new Runnable() {
            @Override
            public void run() {
                starView1.setTranslationX(coordinate1[0]);
                starView1.setTranslationY(coordinate1[1]);
                starView2.setTranslationX(coordinate2[0]);
                starView2.setTranslationY(coordinate2[1]);
            }
        });
    }

    private int[] caculateDegree(float d) {
        circleDegree = d / Math.PI;
        circleX = viewWidth / 2 - padding;
        circleY = viewHeight / 2 - padding;
        circleR = viewWidth / 2;
        int[] xy = new int[2];
        xy[0] = (int) (circleX + circleR * Math.cos(circleDegree));
        xy[1] = (int) (circleY + circleR * Math.sin(circleDegree));
        return xy;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }


    public void start() {
        play = true;
        initThread();
    }

    public void stop() {
        play = false;
    }
}

package com.bcgtgjyb.huanwen.customview.mylibrary.star;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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
    private int degree = 0;
    private String TAG = StarCircle.class.getName();

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
        initThread();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getWidth()- ScreenUtil.dip2px(mContext,30);
        viewHeight = getHeight()- ScreenUtil.dip2px(mContext,30);
    }

    private void initThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (play) {
                    degree += 1f;
                    setXy(degree);
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }
        });
    }

    private void setXy(int d) {
        final int[] s1 = caculateDegree(d);
        final int[] s2 = caculateDegree(d);
        post(new Runnable() {
            @Override
            public void run() {
                starView1.setTranslationX(s1[0]);
                starView1.setTranslationY(s1[1]);
                starView2.setTranslationX(s2[0]);
                starView2.setTranslationY(s2[1]);
            }
        });
//        starView2.setDegree(d);
//        starView1.setDegree(d);
    }

    private int[] caculateDegree(int d) {
        double dd = d/Math.PI;
        int cX = viewWidth / 2;
        int cY = viewHeight / 2;
        int r = viewWidth / 2;
        int[] xy = new int[2];
        xy[0] = (int) (cX + r * Math.cos(dd));
        xy[1] = (int) (cY + r * Math.sin(dd));
        Log.i(TAG, "caculateDegree: "+xy[0]+"  "+xy[1]);
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
        thread.start();
    }

    public void stop() {
        play = false;
    }
}

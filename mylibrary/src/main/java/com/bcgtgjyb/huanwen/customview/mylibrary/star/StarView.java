package com.bcgtgjyb.huanwen.customview.mylibrary.star;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bigwen on 2016/4/14.
 */
public class StarView extends View {

    private int width = 100;
    private int height = 100;
    private Paint paint;
    private Path pathBig;
    private Context mContext;
    private int alpha = 255;
    private boolean direction = true;
    //颜色变化率
    private int v = 3;
    private boolean init = false;
    private Thread thread;
    private boolean play = false;
    private float degree = 0;


    public StarView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public StarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        play = true;
        initThread();
    }

    private void initThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (play) {
                    degree += 4;
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

    private void initPath() {
        int unit = width / 10;
        pathBig = new Path();
        pathBig.moveTo(unit * 5, 0);
        pathBig.lineTo(6 * unit, 4 * unit);
        pathBig.lineTo(10 * unit, 5 * unit);
        pathBig.lineTo(6 * unit, 6 * unit);
        pathBig.lineTo(5 * unit, 10 * unit);
        pathBig.lineTo(4 * unit, 6 * unit);
        pathBig.lineTo(0, 5 * unit);
        pathBig.lineTo(4 * unit, 4 * unit);
        pathBig.close();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!init) {
            //支持padding，不然padding属性无效
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();
            width = getWidth() - paddingLeft - paddingRight;
            height = getHeight() - paddingTop - paddingBottom;
            initPath();
            init = true;
        }
        if (direction) {
            alpha -= v;
        } else {
            alpha += v;
        }
        if (alpha <= 0) {
            alpha = 0;
            direction = false;
        } else if (alpha >= 255) {
            alpha = 255;
            direction = true;
        }
        paint.setARGB(255, 255, 255, alpha);
        canvas.rotate(degree, width / 2, height / 2);
        canvas.drawPath(pathBig, paint);
    }

    public void setDegree(final float d) {
        post(new Runnable() {
            @Override
            public void run() {
                degree = d;
                invalidate();
            }
        });
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

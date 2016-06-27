package com.bcgtgjyb.huanwen.customview.mylibrary.spring;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bcgtgjyb.huanwen.customview.mylibrary.tool.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigwen on 2016/4/25.
 */
public class SpringView extends View {

    private Context mContext;
    private List<Point> points = new ArrayList<Point>();
    private Paint mPaint;
    private int height, width;
    private int d;
    private boolean play = false;
    private String TAG = SpringView.class.getName();
    public static float springW = 0;
    public static float springH = 0;
    private float scale = 1f;
    private int moveUnit = 0;
    private Point lastPoint;
    private double degree = 0;

    public SpringView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SpringView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        moveUnit = ScreenUtil.dip2px(mContext, 5);
        width = ScreenUtil.getScreenWidth(mContext);
        height = ScreenUtil.getScreenHeight(mContext);
        d = ScreenUtil.dip2px(mContext, 10);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(d);
        springH = d;
        springW = d;
        initPoint();
        initPath();
//        initNowPoint();
        initThread();
//        initRandomPoint();

    }

    private Path path = new Path();

    private void initPath() {
        path.reset();
        if (points.size() == 0) {
            return;
        }
        for (int i = 0; i < points.size(); i++) {
            if (i == 0) {
                path.moveTo(points.get(i).x, points.get(i).y);
            } else {
                path.lineTo(points.get(i).x, points.get(i).y);
            }
        }
    }

    private void initPoint() {
        for (int i = 0; i < 10; i++) {
            Point point = new Point();
            point.x = width / 2 + i * d;
            point.y = height / 2;
            points.add(point);
        }
    }

    public void addPoint() {
        points.add(lastPoint);
    }

    Point addP = new Point();
    public synchronized void pointMove() {
        if (points.size() == 0) {
            return;
        }
        lastPoint = points.get(points.size() - 1);
        int x = (int) (Math.cos(degree) * moveUnit);
        int y = (int) (Math.sin(degree) * moveUnit);
        Log.i(TAG, "pointMove: "+points.size());
        Point firstP = points.get(0);
        addP.x = firstP.x + x;
        addP.y = firstP.y + y;
        points.add(0, addP);
        points.remove(points.size()-1);
//        for (Point p:points) {
//            p.x = p.x - x;
//            p.y = p.y - y;
//        }
    }

    public void setDegree(double d) {
        float pi = (float) Math.PI;
        if (d < 0) {
            d = d + pi * 2;
        }
        float unit = 0.125f * pi;
        if (d > 15 * unit || d < unit) {
            this.degree = 0;
        } else if (d > unit && d < 3 * unit) {
            this.degree = 2 * unit;
        } else if (d > 3 * unit && d < 5 * unit) {
            this.degree = 4 * unit;
        } else if (d > 5 * unit && d < 7 * unit) {
            this.degree = 6 * unit;
        } else if (d > 7 * unit && d < 9 * unit) {
            this.degree = 8 * unit;
        } else if (d > 9 * unit && d < 11 * unit) {
            this.degree = 10 * unit;
        } else if (d > 11 * unit && d < 13 * unit) {
            this.degree = 12 * unit;
        } else if (d > 13 * unit && d < 15 * unit) {
            this.degree = 14 * unit;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.scale(scale,scale,width / 2, height / 2);
//        canvas.drawPoint(width / 2, height / 2, mPaint);
//        canvas.drawPath(path, mPaint);
//        for (Point point:pointList) {
//            canvas.drawPoint(point.x,point.y,mPaint);
//            point.x+=move[0];
//            point.y+=move[1];
//        }

        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            canvas.drawPoint(p.x, p.y, mPaint);
        }
    }

    private Thread mThread;

    private void initThread() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (play) {
                    pointMove();
                    initPath();
                    postInvalidate();
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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

    private void start() {
        play = true;
        mThread.start();
    }

    private void stop() {
        play = false;
    }
}

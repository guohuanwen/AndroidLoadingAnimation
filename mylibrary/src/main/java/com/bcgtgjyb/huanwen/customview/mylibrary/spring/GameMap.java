package com.bcgtgjyb.huanwen.customview.mylibrary.spring;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bcgtgjyb.huanwen.customview.mylibrary.R;
import com.bcgtgjyb.huanwen.customview.mylibrary.tool.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by bigwen on 2016/4/25.
 */
public class GameMap extends View {

    private Context mContext;
    private int mapW, mapH, screenW, screenH;
    private Bitmap bitmap;
    private List<MapInfo> mapInfos = new ArrayList<>();
    private Paint mPaint;
    private int d;
    private String TAG = GameMap.class.getName();
    private Random random = new Random();
    private List<Point> randomPoints = new ArrayList<>();
    private int scale = 0;
    private float nowScale = 1;

    public GameMap(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public GameMap(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        scale = ScreenUtil.getScreenWidth(mContext) / 5;
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 5));
        d = ScreenUtil.dip2px(mContext, 1);
        screenW = ScreenUtil.getScreenWidth(mContext);
        screenH = ScreenUtil.getScreenHeight(mContext);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        mapW = bitmap.getWidth();
        mapH = bitmap.getHeight();

        int n = screenW / mapW;
        int m = screenH / mapH;

        int startX = -20 * mapW, startY = -20 * mapH;
        for (int i = 0; i < (n + 40); i++) {
            for (int j = 0; j < (m + 40); j++) {
                MapInfo mapInfo = new MapInfo();
                mapInfo.bitmap = bitmap;
                mapInfo.x = startX;
                mapInfo.y = startY;
                startY += mapH;
                mapInfos.add(mapInfo);
            }
            startX += mapW;
            startY = -20 * mapH;
        }
        initThread();
        addRandomPoint(100);
    }


    private class MapInfo {
        Bitmap bitmap;
        int x;
        int y;
    }

    private List<Point> removePoint = new ArrayList<>();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(tx, ty);

        if (SpringView.springW > scale) {
            int scalePamra = (int)(SpringView.springW / scale);
            canvas.scale(1f / scalePamra, 1f / scalePamra, screenW / 2, screenH / 2);
            if (springCallback != null) {
                springCallback.canvasScale(1f / scalePamra);
            }
            nowScale = 1f / scalePamra;
        }
//        for (MapInfo m : mapInfos) {
//            canvas.drawBitmap(m.bitmap, m.x, m.y, null);
//        }
        float sw = SpringView.springW / 2 * nowScale;
        float sh = SpringView.springW / 2 * nowScale;
        for (Point point : randomPoints) {
            float x = point.x + tx;
            float y = point.y + ty;
            if (!(x > 0 && x < screenW && y > 0 && y < screenH)) {
//                continue;
            }
//            Log.i(TAG, "onDraw: " + x + "   " + y + screenW + "  " + screenH);

            if ((x >= (screenW / 2 - sw) && x <= (screenW / 2 + sw)) &&
                    (y >= (screenH / 2 - sh) && y <= (screenH / 2 + sh))) {
                removePoint.add(point);
                if (springCallback != null) {
                    springCallback.changePoint();
                }
            } else {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
        }
        if (removePoint.size() != 0) {
            Log.i(TAG, "onDraw: remove");
            int numb = removePoint.size();
            randomPoints.removeAll(removePoint);
            removePoint.clear();
            addRandomPoint(numb);
        }

    }

    private void addRandomPoint(int param) {
        for (int i = 0; i < param; i++) {
            Point point = new Point();
            int kk = random.nextBoolean() ? 1 : -1;
            int k = random.nextBoolean() ? 1 : -1;
            point.x = kk * random.nextInt(5 * mapW);
            point.y = k * random.nextInt(5 * mapH);
            randomPoints.add(point);
        }
    }

    private float latsX, lastY, tx = 0, ty = 0;

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            latsX = x;
//            lastY = y;
//            return false;
//        }
//        if (event.getAction() == MotionEvent.ACTION_MOVE) {
////            translate(x-latsX,y-lastY);
//            translateCanvas(x - lastY, y - lastY);
//            invalidate();
//            latsX = x;
//            lastY = y;
//        }
//        return super.onTouchEvent(event);
//    }

    public void translateCanvas(float x, float y) {
        tx = tx + x;
        ty = ty + y;
        postInvalidate();
    }

    private float degree = 0;

    public void move(double d) {
//        int piVal = (int) (d / (2 * Math.PI));
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
//        Log.i(TAG, "move: " + d + "  " + unit);
    }

    public float getDegree() {
        return degree;
    }

    private Thread mThread;
    private boolean play = false;

    private void initThread() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (play) {
                    float x = -(float) (Math.cos(getDegree()) * d);
                    float y = -(float) (Math.sin(getDegree()) * d);
                    translateCanvas(x, y);
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void start() {
        play = true;
        mThread.start();
    }

    public void stop() {
        play = false;
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

    public void setSpringCallback(SpringCallback springCallback) {
        this.springCallback = springCallback;
    }

    private SpringCallback springCallback = null;

    public interface SpringCallback {
        void changePoint();

        void canvasScale(float parma);
    }
}

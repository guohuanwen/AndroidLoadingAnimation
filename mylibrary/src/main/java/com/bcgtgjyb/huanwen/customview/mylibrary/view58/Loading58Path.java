package com.bcgtgjyb.huanwen.customview.mylibrary.view58;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.bcgtgjyb.huanwen.customview.mylibrary.tool.BWValueAnimator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by bigwen on 2016/3/2.
 */
public class Loading58Path extends View {
    private int w = 0;
    private int h = 0;
    private Paint paint;
    private String TAG = Loading58Path.class.getName();
    private Path pathTri = new Path();
    private static final float SQRT3 = 1.7f;
    private static final float SQRT2 = 1.414f;
    private int runParam = -1;
    private int ST = 0;
    private int TC = 0;
    private int CS = 0;
    private Path pathSqure = new Path();
    private ValueAnimator colorVA;
    private Path cirToSque = new Path();
    private ValueAnimator cirToSqueVA;
    private Path triToCir = new Path();
    private ValueAnimator triToCirVA;
    private Path squeToTri = new Path();
    private ValueAnimator squeToTriVA = null;
    private Path pathCir = new Path();

    public Loading58Path(Context context) {
        super(context);
        init();
    }

    public Loading58Path(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        initColorChange();
    }

    //  正->三角->保持三角->圆->保持圆->正->保持正->循环
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initWH(getWidth(), getHeight());
        if (runParam == 0) {
            if (colorVA != null && colorVA.isRunning()) {
                int d = (Integer) colorVA.getAnimatedValue();
                paint.setARGB(255, 255 - d, d, 0);
            } else {
                colorVA.start();
            }
            drawSquToTri();
            canvas.drawPath(squeToTri, paint);
            if (squeToTriVA != null && squeToTriVA.isRunning()) {
                invalidate();
            } else {
                runParam = 1;
                invalidate();
            }
        }

        if (runParam == 1) {
            drawPathTriangle();
            canvas.drawPath(pathTri, paint);
        }

        if (runParam == 2) {
            if (colorVA != null && colorVA.isRunning()) {
                int d = (Integer) colorVA.getAnimatedValue();
                paint.setARGB(255, 0, 255 - d, d);
            } else {
                colorVA.start();
            }
            drawTriToCir();
            canvas.drawPath(triToCir, paint);
            if (triToCirVA != null && triToCirVA.isRunning()) {
                invalidate();
            } else {
                runParam = 3;
                invalidate();
            }
        }

        if (runParam == 3) {
            drawPathCir();
            canvas.drawPath(pathCir, paint);
        }

        if (runParam == 4) {
            if (colorVA != null && colorVA.isRunning()) {
                int d = (Integer) colorVA.getAnimatedValue();
                paint.setARGB(255, d, 0, 255 - d);
            } else {
                colorVA.start();
            }
            drawCirToSque();
            canvas.drawPath(cirToSque, paint);
            if (cirToSqueVA.isRunning()) {
                invalidate();
            } else {
                runParam = 5;
                invalidate();
            }
        }

        if (runParam == 5) {
            drawPathSqure();
            canvas.drawPath(pathSqure, paint);
        }
    }

    public void start() {
        if (runParam == -1) {
            runParam = 0;
            invalidate();
        }
    }

    public void stop() {
        runParam = -1;
        clear();
    }

    public void nextPath() {
        Log.i(TAG, "nextPath: " + runParam);
        runParam++;
        if (runParam == 6) {
            runParam = 0;
            clear();
        }
        invalidate();
    }

    private void initWH(int width, int height) {
        this.w = width;
        this.h = height;
    }

    private void drawPathTriangle() {
        Log.i(TAG, "drawPathTriangle: ");
        pathTri.reset();
        pathTri.moveTo(w / 2, 0);
        pathTri.lineTo(w / 2 + SQRT3 / 4 * w, 0.75f * h);
        pathTri.lineTo(w / 2 - SQRT3 / 4 * w, 0.75f * h);
        pathTri.lineTo(w / 2, 0);
        pathTri.close();
    }

    private void drawPathSqure() {
        Log.i(TAG, "drawPathSqure: ");
        pathSqure.reset();
        pathSqure.moveTo(0, 0);
        pathSqure.lineTo(w, 0);
        pathSqure.lineTo(w, h);
        pathSqure.lineTo(0, h);
        pathSqure.close();
    }


    private void drawPathCir() {
        Log.i(TAG, "drawPathCir: ");
        pathCir.reset();
        pathCir.addCircle(w / 2, h / 2, h / 2, Path.Direction.CCW);
    }

    private void drawSquToTri() {
        squeToTri.reset();
        if (squeToTriVA == null) {
            ST = 2;
            squeToTriVA = BWValueAnimator.getValueAnimator(0, w / 2, 500);
            squeToTriVA.start();
        } else if (ST == 1) {
            ST = 2;
            squeToTriVA.start();
        } else {

        }
        if (squeToTriVA != null && squeToTriVA.isRunning()) {
            float value = (float) squeToTriVA.getAnimatedValue();
            float value1 = (1 - SQRT3 / 2) * value;
            float value2 = value / 2;
            //左下点
            float x1 = value1;
            float y1 = h - value2;
            //右下点
            float x2 = w - value1;
            float y2 = h - value2;
            //顶点左
            float x3 = value;
            float y3 = 0;
            //定点右
            float x4 = w - value;
            float y4 = 0;
            squeToTri.moveTo(x1, y1);
            squeToTri.lineTo(x2, y2);
            squeToTri.lineTo(x4, y4);
            squeToTri.lineTo(x3, y3);
            squeToTri.close();
            return;
        } else {
            return;
        }
    }

    private void drawTriToCir() {
        triToCir.reset();
        //变化值 0-一个shu
        float num = 0.4f * w;
        if (triToCirVA == null) {
            triToCirVA = BWValueAnimator.getValueAnimator(0, num, 500);
            triToCirVA.start();
            TC = 2;
        } else if (TC == 1) {
            triToCirVA.start();
            TC = 2;
        } else {

        }
        if (triToCirVA.isRunning()) {
            float param = (float) triToCirVA.getAnimatedValue();
            //三角形 左下定点
            float x1 = w / 2 - SQRT3 / 4 * w;
            float y1 = 0.75f * h;
            //三角形 右下顶点
            float x2 = w / 2 + SQRT3 / 4 * w;
            float y2 = 0.75f * h;
            //三角形 顶部顶点
            float x3 = w / 2;
            float y3 = 0;
            //左上 调节点
            float[] point1 = fx1((x3 + x1) / 2 - param);
            //右上 调节点
            float[] point2 = fx2((x3 + x2) / 2 + param);
            //下部 调节点
            float[] point3 = new float[2];
            point3[0] = w / 2;
            point3[1] = h + param / SQRT3;

            triToCir.moveTo(x3, y3);
            triToCir.quadTo(point1[0], point1[1], x1, y1);
            triToCir.quadTo(point3[0], point3[1], x2, y2);
            triToCir.quadTo(point2[0], point2[1], x3, y3);
            Log.i(TAG, "drawTriToCir: " + y1 + "  " + y2 + "  " + y3);
            triToCir.close();
        }
    }

    //三角形一边的垂直平分线函数
    private float[] fx1(float x) {
        float[] point = new float[2];
        point[0] = x;
        point[1] = SQRT3 / 3 * x + (0.5f - SQRT3 / 6) * h;
        return point;
    }

    //三角形一边的垂直平分线函数
    private float[] fx2(float x) {
        float[] point = new float[2];
        point[0] = x;
        point[1] = -SQRT3 / 3 * x + (0.5f + SQRT3 / 6) * h;
        return point;
    }

    private void drawCirToSque() {
        cirToSque.reset();
        if (cirToSqueVA == null) {
            float num = 0.45f * w;
            cirToSqueVA = BWValueAnimator.getValueAnimator(0.27f * w, num, 500);
            cirToSqueVA.start();
            CS = 2;
        } else if (CS == 1) {
            cirToSqueVA.start();
            CS = 2;
        } else {

        }
        if (cirToSqueVA.isRunning()) {
            float param = (float) cirToSqueVA.getAnimatedValue();
            //顶部点
            float x1 = w / 2;
            float y1 = 0;
            //右顶点
            float x2 = w;
            float y2 = h / 2;
            //下顶点
            float x3 = w / 2;
            float y3 = h;
            //左顶点
            float x4 = 0;
            float y4 = h / 2;

            //调节参数
            float par = 0.8f;
            //右上调节点
            float xx1 = par * w + param;
            float[] point1 = fx4(xx1);
            //右下调节点
            float xx2 = par * w + param;
            float[] point2 = fx3(xx2);
            //左下调节点
            float xx3 = (1 - par) * w - param;
            float[] point3 = fx4(xx3);
            //左上调节点
            float xx4 = (1 - par) * w - param;
            float[] point4 = fx3(xx4);

            cirToSque.moveTo(x1, y1);
            cirToSque.quadTo(point1[0], point1[1], x2, y2);
            cirToSque.quadTo(point2[0], point2[1], x3, y3);
            cirToSque.quadTo(point3[0], point3[1], x4, y4);
            cirToSque.quadTo(point4[0], point4[1], x1, y1);
            cirToSque.close();
        }
    }

    //调节点 函数
    private float[] fx3(float x) {
        float[] param = new float[2];
        param[0] = x;
        param[1] = x;
        return param;
    }

    //调节点 函数
    private float[] fx4(float x) {
        float[] param = new float[2];
        param[0] = x;
        param[1] = w - x;
        return param;
    }

    public void clear() {
        ST = 1;
        TC = 1;
        CS = 1;
    }

    private void initColorChange() {
        colorVA = ValueAnimator.ofInt(0, 255);
        colorVA.setDuration(500);
        colorVA.setInterpolator(new LinearInterpolator());
    }

}

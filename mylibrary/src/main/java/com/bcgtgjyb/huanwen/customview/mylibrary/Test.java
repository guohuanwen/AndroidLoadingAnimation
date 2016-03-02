package com.bcgtgjyb.huanwen.customview.mylibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.bcgtgjyb.huanwen.customview.mylibrary.tool.BWCallback;
import com.bcgtgjyb.huanwen.customview.mylibrary.tool.Move;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by bigwen on 2016/3/1.
 */
public class Test extends View {
    private String TAG = Test.class.getName();

    public Test(Context context) {
        super(context);
        init();
    }

    public Test(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private Path path;
    private void init(){
        move = new Move();
        returenMove = new Move();
        paint = new Paint();
        paint.setColor(Color.RED);
        path = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private Move move;
    private Move returenMove;
    private Paint paint;
    private int run = -1;
    private float[] f = new float[2];
    private float [] ff = new float[2];
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        if (run == -1){
            run = 0;
            f = move.xy(w/2, h/2, 100, 100, 5000);
            move.setCallback(new BWCallback() {
                @Override
                public void onFinish(ValueAnimator... valueAnimator) {

                }

                @Override
                public void onFinish() {
                    run = 1;
                    invalidate();
                }
            });
        }
        if (run == 0) {
            f = move.xy(w/2, h/2, 100, 100, 5000);
            canvas.drawCircle(f[0], f[1], 20, paint);
        }
        if (run == 1){
            ff = returenMove.xy(w/2, h/2, -100, -100, 5000);
            canvas.drawCircle(ff[0], ff[1], 20, paint);
        }


//        path.moveTo(w/2,h/2);
//        path.quadTo(0, 0, w/2, 0);
//        path.quadTo(w/2, 0, w / 2, h);
//        path.quadTo(w / 2, h, 0, 0);
        Paint paint1 = new Paint();
        paint1.setColor(Color.BLUE);
        paint1.setAntiAlias(false);
        paint1.setStyle(Paint.Style.FILL);
        path.reset();
        path.moveTo(0, 0);
        path.quadTo(xxx / 2, h / 2, xxx, 0);
        path.lineTo(xxx, h);
        path.quadTo(xxx/2, h/2, 0, h);
        path.lineTo(0,0);
        path.close();
        canvas.drawPath(path,paint1);
        canvas.translate(0, 60);
        if(move != null && move.isRunning()){
            invalidate();
        }
        if (returenMove != null && returenMove.isRunning()){
            invalidate();
        }
    }


    private float xxx = 100;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ");
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float  y = event.getX();
                float rawy = event.getRawY();
                Log.i(TAG, "onTouchEvent: "+y +"   "+rawy);
                xxx = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}

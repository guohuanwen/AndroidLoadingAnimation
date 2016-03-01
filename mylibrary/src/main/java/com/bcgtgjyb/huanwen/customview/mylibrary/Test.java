package com.bcgtgjyb.huanwen.customview.mylibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.bcgtgjyb.huanwen.customview.mylibrary.tool.BWCallback;
import com.bcgtgjyb.huanwen.customview.mylibrary.tool.Move;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by bigwen on 2016/3/1.
 */
public class Test extends View {

    public Test(Context context) {
        super(context);
        init();
    }

    public Test(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        move = new Move();
        returenMove = new Move();
        paint = new Paint();
        paint.setColor(Color.RED);
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

        if(move != null && move.isRunning()){
            invalidate();
        }
        if (returenMove != null && returenMove.isRunning()){
            invalidate();
        }
    }
}

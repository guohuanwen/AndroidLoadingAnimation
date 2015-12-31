package com.bcgtgjyb.huanwen.customview.mylibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bigwen on 2015/12/31.
 */
public class People extends View {
    private Paint paint;
    public People(Context context) {
        super(context);
        init();
    }

    public People(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#ff0099cc"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        float[] floats = new float[]{width/2,2/3*height};
        float[] end = getCircleXY(floats,width/6,30);
        canvas.drawLines(new float[]{floats[0],floats[1],end[0],end[1]},paint);


    }

    private float[] getCircleXY(float[] floats,float R,int audio){
        float x = (float)Math.sin(audio)*R+floats[0];
        float y = (float)Math.cos(audio)*R+floats[1];
        return new float[]{x,y};
    }
}

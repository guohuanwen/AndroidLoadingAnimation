package com.bcgtgjyb.huanwen.customview.mylibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by huanwen on 2015/9/1.
 */
public class TaiJiButton extends View {
    private final String TAG="TaiJiButton";
    private float rolate=360;
    private Paint mWhitePaint;
    private Paint mBlackPaint;
    private Paint backgroundPaint;
    private Paint arcPaint;
    private Rect mRect;
    private RectF backRectF;
    private ValueAnimator animator;
    private ValueAnimator smallAnimator;
    private ValueAnimator lineToArcAnimator;
    private ValueAnimator arcToLineAnimator;
    private boolean init=false;
    private float ratio=0;
    private boolean stopAnimator = false;
    private float raduis=0;
    private int velocity=1000;
    private int color1=Color.BLACK;
    private int color2=Color.GRAY;
    RectF rectF = new RectF();
    RectF rectF2 = new RectF();


    public TaiJiButton(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        mWhitePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mBlackPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect=new Rect();
        backRectF=new RectF();
    }

    /**
     * set the animation start
     */
    public void startLoad(){
        stopAnimator=false;
        setLineToArcAnimator();
        setAppearAnimator();
        loading();
    }

    /**
     * set the animation stop
     */
    public void stopLoad(){
        this.stopAnimator=true;
        setArcToLineAnimator();
        setDisAppearAnimator();
    }

    /**
     * set the velocity of animation
     * @param velocity
     */
    public void setVelocity(int velocity){
        this.velocity=velocity;
    }

    /**
     * set the view's color
     * @param color1
     * @param color2
     */
    public void setColor(int color1,int color2){
        this.color1=color1;
        this.color2=color2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(animator!=null&&animator.isRunning()){
            float ratio = (Float)animator.getAnimatedValue();
            canvas.rotate(rolate*ratio,getWidth()/2,getHeight()/2);
            invalidate();
        }
        float R=getWidth()/2-5;
        mWhitePaint.setColor(color2);
        mBlackPaint.setColor(color1);
        backRectF.set(getWidth() / 2 - R, getHeight() / 2 - R, getWidth() / 2 + R, getHeight() / 2 + R);
        mRect.set(getWidth(), getHeight(), 0, 0);
        canvas.drawArc(backRectF, 0, 180, true, mWhitePaint);
        canvas.drawArc(backRectF, 0, -180, true, mBlackPaint);
        //消除锯齿
//        backgroundPaint.setAntiAlias(true);

        // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线


//        canvas.drawOval(backRectF,arcPaint);
        //设置图形为空心
//        backgroundPaint.setStyle(Paint.Style.STROKE);

//        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, R, backgroundPaint);


//            init=true;

//        if(ratio==0.9998989){
//            RectF rectF = new RectF();
//            RectF rectF2 = new RectF();
//            rectF.set(getWidth() / 2 - R, getHeight() / 2 + R / 2, getWidth() / 2, getHeight() / 2 - R / 2);
//            canvas.drawArc(rectF, 0, 360, true, mWhitePaint);
//            rectF2.set(getWidth() / 2, getHeight() / 2 + R / 2, getWidth() / 2 + R, getHeight() / 2 - R / 2);
//            canvas.drawArc(rectF2, 0, 360, true, mBlackPaint);
//        }


        rectF.set(getWidth() / 2 - R,getHeight() / 2+ratio*R/2, getWidth() / 2, getHeight() / 2 -  ratio * R / 2);
        canvas.drawArc(rectF, 0, 180, true, mWhitePaint);

        rectF2.set(getWidth() / 2, getHeight() / 2 + ratio * R / 2, getWidth() / 2 + R, getHeight() / 2 - ratio * R / 2);
        canvas.drawArc(rectF2, 0, -180, true, mBlackPaint);
        if((lineToArcAnimator!=null&&lineToArcAnimator.isRunning())) {
            ratio = (Float) lineToArcAnimator.getAnimatedValue();
            invalidate();
        }

        if(arcToLineAnimator!=null&&arcToLineAnimator.isRunning()){
            ratio = (Float) arcToLineAnimator.getAnimatedValue();
            invalidate();
        }


//        if(ratio<=0.5){
//            canvas.drawArc(backRectF, 0, 180, true, mWhitePaint);
//            canvas.drawArc(backRectF, 0, -180, true, mBlackPaint);
//        }else{
//            canvas.drawArc(backRectF, 0, -180, true, mWhitePaint);
//            canvas.drawArc(backRectF, 0, 180, true, mBlackPaint);
//        }


//            canvas.rotate(360 * rotate, getWidth() / 2, getHeight() / 2);

//        if(smallAnimator!=null&&smallAnimator.isRunning()){
//            rotate = (Float)smallAnimator.getAnimatedValue();
//            invalidate();
//        }

        float raduisX1=getWidth()/2+R/2;
        float raduisY1=getHeight()/2;
        float raduisX2=getWidth()/2-R/2;
        float raduisY2=getHeight()/2;

//        float raduisX1=getWidth()/2;
//        float raduisY1=getHeight()/2+R/2;
//        float raduisX2=getWidth()/2;
//        float raduisY2=getHeight()/2-R/2;

//        canvas.drawCircle(raduisX1,raduisY1,R/2,mWhitePaint);
//        canvas.drawCircle(raduisX2,raduisY2,R/2,mBlackPaint);

        canvas.drawCircle(raduisX1,raduisY1,raduis*R/5,mWhitePaint);
        canvas.drawCircle(raduisX2,raduisY2,raduis*R/5,mBlackPaint);
        if(smallAnimator!=null&&smallAnimator.isRunning()){
            raduis=(float)smallAnimator.getAnimatedValue();
            invalidate();
        }




//
    }







    private void loading(){
        loadAnimator();
        if(!stopAnimator){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loading();
                }
            },animator.getDuration());
        }
    }

    private void loadAnimator() {
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(velocity);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        invalidate();
    }

    private void setLineToArcAnimator(){
        Log.i(TAG, "setLineToArcAnimator ");
        lineToArcAnimator=ValueAnimator.ofFloat(0f,1f);
        lineToArcAnimator.setDuration(velocity);
        lineToArcAnimator.setInterpolator(new LinearInterpolator());
        lineToArcAnimator.start();
        invalidate();
    }
    private void setArcToLineAnimator(){
        Log.i(TAG, "setArcToLineAnimator ");
        arcToLineAnimator=ValueAnimator.ofFloat(1f,0f);
        arcToLineAnimator.setDuration(velocity);
        arcToLineAnimator.setInterpolator(new LinearInterpolator());
        arcToLineAnimator.start();
        invalidate();
    }

    private void setAppearAnimator(){
        smallAnimator=ValueAnimator.ofFloat(0f,1f);
        smallAnimator.setDuration(velocity);
        smallAnimator.start();
        invalidate();
    }

    private void setDisAppearAnimator(){
        smallAnimator=ValueAnimator.ofFloat(1f,0f);
        smallAnimator.setDuration(velocity);
        smallAnimator.start();
        invalidate();
    }
}

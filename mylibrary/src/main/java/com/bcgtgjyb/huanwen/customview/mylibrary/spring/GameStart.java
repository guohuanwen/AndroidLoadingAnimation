package com.bcgtgjyb.huanwen.customview.mylibrary.spring;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.bcgtgjyb.huanwen.customview.mylibrary.R;

/**
 * Created by bigwen on 2016/4/25.
 */
public class GameStart extends LinearLayout {

    private Context mContext;
    private GameMap mGameMap;
    private String TAG = GameStart.class.getName();
    private SpringView mSpringView;


    public GameStart(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public GameStart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.game_start, this);
        mGameMap = (GameMap) findViewById(R.id.game_map);
        mSpringView = (SpringView) findViewById(R.id.springView);
        mGameMap.setSpringCallback(new GameMap.SpringCallback() {
            @Override
            public void changePoint() {
//                mSpringView.changeSpring();
                mSpringView.addPoint();
            }

            @Override
            public void canvasScale(float parma) {
            }
        });
    }

    private float downX, downY;
    private boolean isMove = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    private double lastD = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
//        Log.i(TAG, "onTouchEvent: "+x);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = x;
            downY = y;
            isMove = true;
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (isMove) {
                double d = RectToPolar(x - downX, y - downY);
                Log.i(TAG, "onTouchEvent: " + d);
                mGameMap.move(d);
                mSpringView.setDegree(d);
                lastD = d;
            }
        }
        return super.onTouchEvent(event);
    }

    // 实现直角坐标系转换为极坐标系的方法
    public double RectToPolar(double x, double y) {
        return Math.atan2(y, x);
    }
}

/*
 * Copyright 2015 guohuanwen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bcgtgjyb.huanwen.customview.mylibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2015/11/9.
 */
public class PPTVLoading extends View {
    private Paint paint1;
    private Paint paint2;
    //default color
    private int color1 = Color.parseColor("#ff0099cc");
    private int color2 = Color.parseColor("#ff669900");

    private boolean init = false;
    private ValueAnimator valueAnimator;
    private float numb = 0;

    private boolean stop = false;

    private int R = 0;

    public PPTVLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(color1);
        paint2.setColor(color2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!init) {
            init = true;
            R = getWidth() / 8;
            start();
        }
        numb = (float) valueAnimator.getAnimatedValue();
        if (numb < 0) {
            canvas.drawCircle((getWidth() - 2 * R) * (1 - Math.abs(numb)) + R, getHeight() / 2, R - 5, paint2);
            canvas.drawCircle((getWidth() - 2 * R) * Math.abs(numb) + R, getHeight() / 2, R - 5 * (float) Math.abs(Math.abs(numb) - 0.8), paint1);
        } else {
            canvas.drawCircle((getWidth() - 2 * R) * (1 - Math.abs(numb - 1)) + R, getHeight() / 2, R - 5, paint1);
            canvas.drawCircle((getWidth() - 2 * R) * Math.abs(numb - 1) + R, getHeight() / 2, R - 5 * (float) Math.abs(Math.abs(numb) - 0.8), paint2);
        }
        if (valueAnimator.isRunning()) {
            invalidate();
        }
    }

    public void start() {
        if (valueAnimator == null) {
            valueAnimator = getValueAnimator();
        } else {
            valueAnimator.start();
        }
        if (stop == false) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    start();
                    invalidate();
                }
            }, valueAnimator.getDuration());
        }
    }

    public void stop() {
        this.stop = true;
    }

    private ValueAnimator getValueAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(-1f, 1f);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
        return valueAnimator;
    }

}

package com.bcgtgjyb.huanwen.customview.mylibrary.tool;

/**
 * Created by bigwen on 2016/2/29.
 */
public class Rotate {

    private static final int CIRCLEDEGREE = 360;

    public float[] rotatePoint(float[] point, float[] coodinate, int degree) {
        float radioX = coodinate[0];
        float radioY = coodinate[1];
        float x = point[0];
        float y = point[1];
        float r = (float) Math.sqrt((radioX - x) * (radioX - x) + (radioY - y) * (radioY - y));
        float nowDegree = (float) Math.atan((y - radioY) / (x - radioX)) * 360;
        float[] end = new float[2];
        float endDegree = nowDegree + degree;
        end[0] = (float) (r * Math.sin(endDegree / CIRCLEDEGREE));
        end[1] = (float) (r * Math.cos(endDegree / CIRCLEDEGREE));
        return end;
    }

}

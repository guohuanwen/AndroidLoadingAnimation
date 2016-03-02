package com.bcgtgjyb.huanwen.customview.mylibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by bigwen on 2016/3/2.
 */
public class Loding58View extends LinearLayout {
    private Context mContext;
    private View view;

    public Loding58View(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public Loding58View(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.loding58view, null);
        view = findViewById(R.id.loading58);

    }


}

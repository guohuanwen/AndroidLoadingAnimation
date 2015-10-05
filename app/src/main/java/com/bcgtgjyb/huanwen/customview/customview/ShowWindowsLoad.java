package com.bcgtgjyb.huanwen.customview.customview;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.bcgtgjyb.huanwen.customview.mylibrary.WindowsLoad;

/**
 * Created by Administrator on 2015/10/5.
 */
public class ShowWindowsLoad extends Activity {
    private WindowsLoad windowsLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_windows);
        windowsLoad=(WindowsLoad)findViewById(R.id.windows_load);
    }
}

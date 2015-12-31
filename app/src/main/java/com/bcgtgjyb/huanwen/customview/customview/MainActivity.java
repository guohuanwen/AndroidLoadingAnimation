package com.bcgtgjyb.huanwen.customview.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bcgtgjyb.huanwen.customview.mylibrary.FiveLine;
import com.bcgtgjyb.huanwen.customview.mylibrary.TaiJiButton;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TaiJiButton taiJiButton;
    private FiveLine fiveLine1;
    private FiveLine fiveLine2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        taiJiButton = (TaiJiButton) findViewById(R.id.taiJiButton);
        fiveLine1 = (FiveLine) findViewById(R.id.fiveLine1);
        fiveLine2 = (FiveLine) findViewById(R.id.fiveLine2);
        fiveLine2.initLine(new float[]{0,0.2f,0.4f,0.6f,0.8f});

        taiJiButton.setVelocity(5000);
        taiJiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taiJiButton.startLoad();
            }
        });


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taiJiButton != null) {
                    taiJiButton.stopLoad();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

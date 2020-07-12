package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button startStopBtn;
    Chronometer watch;
    Handler handler;
    Long tMilliSec, tBuff, tUpdate,tStart =0L;
    int hour, min, sec, milliSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        watch = findViewById(R.id.watch_chrm);
        handler = new Handler();
        startStopBtn = findViewById(R.id.start_stop_btn);
        startStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stat()){
                    start();
                }else{
                    stop();
                }
            }
        });

        findViewById(R.id.reset_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    public Runnable startRun = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis()-tStart;
            tUpdate = tBuff = tMilliSec;
            sec = (int) (tUpdate/1000);
            min = sec/60;
            hour = min/60;
            min = min%60;
            sec = sec%60;
            milliSec = (int) (tUpdate%100);
            String display = String.format("%02d",hour)+":"+String.format("%02d",min)+":"+
                    String.format("%02d",sec)+":"+ String.format("%02d",milliSec);
            watch.setText(display);
            handler.postDelayed(this,60);
        }
    };

    private void start() {
        startStopBtn.setText(R.string.stop);
        startStopBtn.setTextColor(Color.parseColor("#EF0808"));
        startRunning();

    }

    private void startRunning() {
        tStart = SystemClock.uptimeMillis();
        handler.postDelayed(startRun,0);
        watch.start();



    }

    private void stop() {
        startStopBtn.setText(R.string.start);
        startStopBtn.setTextColor(Color.parseColor("#05E10E"));
        tMilliSec = 0L;
        tBuff = 0L;
        tUpdate = 0L;
        tStart =0L;
        hour = 0;
        min =0;
        sec = 0;
        milliSec = 0;
        String stopText = "00:00:00:00";
        watch.setText(stopText);
    }

    private boolean stat() {
        String statusText = startStopBtn.getText().toString();
        return statusText.equalsIgnoreCase("start");

    }

    private void reset() {
        TextView hoursTv, minuteTv, secondsTv, milliSecondsTv;
        hoursTv = findViewById(R.id.hours_tv);
        minuteTv = findViewById(R.id.minutes_tv);
        secondsTv = findViewById(R.id.seconds_tv);
        milliSecondsTv = findViewById(R.id.mili_seconds_tv);

        hoursTv.setText(R.string.zero);
        minuteTv.setText(R.string.zero);
        secondsTv.setText(R.string.zero);
        milliSecondsTv.setText(R.string.zero);
    }
}
package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    Button startStopBtn, pauseBtn;
    Chronometer watch;
    Handler handler;
    Long tMilliSec, tBuff, tUpdate,tStart, pauseOffSet =0L;
    int hour, min, sec, milliSec;
    Boolean running, isPaused, isResumed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        watch = findViewById(R.id.watch_chrm);
        startStopBtn = findViewById(R.id.start_stop_btn);
        pauseBtn = findViewById(R.id.pause_btn);
        running = false;

        handler = new Handler();
    }


    public Runnable startRun = new Runnable() {
        @Override
        public void run() {
            if (running ) {
                tMilliSec = SystemClock.uptimeMillis() - tStart;
                tUpdate = tBuff = tMilliSec;
                sec = (int) (tUpdate / 1000);
                min = sec / 60;
                hour = min / 60;
                min = min % 60;
                sec = sec % 60;
                milliSec = (int) (tUpdate % 100);
                String display = String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" +
                        String.format("%02d", sec) + ":" + String.format("%02d", milliSec);
                watch.setText(display);
                handler.postDelayed(this, 60);
                }
            }
        };



    public void startStopBtnClicked(View v) {
        if (stat()){
            if(!running){
                watch.setBase(SystemClock.elapsedRealtime());
                watch.start();
                pauseBtn.setVisibility(View.VISIBLE);
                startStopBtn.setText(R.string.stop);
                startStopBtn.setTextColor(Color.parseColor("#EF0808"));
                running =true;
                isPaused = false;
                isResumed = false;
                tStart = SystemClock.uptimeMillis();
                handler.postDelayed(startRun,0);

            }


        }
        else {
            startStopBtn.setText(R.string.start);
            pauseBtn.setText(R.string.pause);
            pauseBtn.setVisibility(View.GONE);
            startStopBtn.setTextColor(Color.parseColor("#05E10E"));
            running = false;
        }


    }

    public void pauseBtnClicked(View v) {
            if (pauseStat()){
                pauseBtn.setText(R.string.resume);
                watch.stop();
                pauseOffSet = SystemClock.uptimeMillis() - tStart;
                running = false;
                handler.removeCallbacks(startRun);
            }else{
                pauseBtn.setText(R.string.pause);
                tStart = (SystemClock.uptimeMillis()-pauseOffSet);
                running = true;
                handler.postDelayed(startRun,0);
            }

    }
    public void resetBtnClicked(View v) {
        watch.stop();
        String stopText = "00:00:00:00";
        watch.setText(stopText);
    }

    private boolean stat() {
        String statusText = startStopBtn.getText().toString();
        return statusText.equalsIgnoreCase("start");

    }
    private boolean pauseStat() {
        String statusText = pauseBtn.getText().toString();
        return statusText.equalsIgnoreCase("pause");

    }
}
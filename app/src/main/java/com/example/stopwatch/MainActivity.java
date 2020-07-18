package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
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
        handler = new Handler();

        if (savedInstanceState != null){
            running = savedInstanceState.getBoolean("running");
            watch.setText(savedInstanceState.getString("display"));
            startStopBtn.setText(savedInstanceState.getString("startStop"));
            pauseBtn.setText(savedInstanceState.getString("pauseResume"));
            isPaused = savedInstanceState.getBoolean("pauseStatus");
            if (!isPaused){
                pauseBtn.setVisibility(View.VISIBLE);
                startStopBtn.setTextColor(Color.parseColor("#EF0808"));
            }
            pauseOffSet = savedInstanceState.getLong("pauseOffSetCurrent");
            tStart = (SystemClock.uptimeMillis()-pauseOffSet);
        }else  running = false;


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (running)  pause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("startStop",startStopBtn.getText().toString());
        outState.putString("pauseResume",pauseBtn.getText().toString());
        outState.putBoolean("pauseStatus",pauseStat());
        outState.putBoolean("running",running);
        outState.putLong("pauseOffSetCurrent",pauseOffSet);
        outState.putString("display",watch.getText().toString());
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

                watch.setBase(SystemClock.elapsedRealtime());
                watch.start();
                pauseBtn.setVisibility(View.VISIBLE);
                startStopBtn.setText(R.string.stop);
                startStopBtn.setTextColor(Color.parseColor("#EF0808"));
                running =true;
                tStart = SystemClock.uptimeMillis();
                handler.postDelayed(startRun,0);

        }
        else {
            watch.stop();
            running = false;
            startStopBtn.setText(R.string.start);
            pauseBtn.setText(R.string.pause);
            pauseBtn.setVisibility(View.GONE);
            startStopBtn.setTextColor(Color.parseColor("#05E10E"));

        }


    }

    public void pauseBtnClicked(View v) {
            if (pauseStat()){
                pause();
            }else{
                pauseBtn.setText(R.string.pause);
                tStart = (SystemClock.uptimeMillis()-pauseOffSet);
                running = true;
                handler.postDelayed(startRun,0);
            }

    }

    private void pause() {
        pauseBtn.setText(R.string.resume);
        watch.stop();
        pauseOffSet = SystemClock.uptimeMillis() - tStart;
        running = false;
        handler.removeCallbacks(startRun);
    }

    public void resetBtnClicked(View v) {
        watch.stop();
        running=false;
        if (!stat()) {
            handler.removeCallbacks(startRun);
            String stopText = "00:00:00:00";
            watch.setText(stopText);
            tStart= SystemClock.uptimeMillis();
            running = true;
            handler.postDelayed(startRun,0);
        } else {
            handler.removeCallbacks(startRun);
            running = false;
            String stopText = "00:00:00:00";
            watch.setText(stopText);
        }
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
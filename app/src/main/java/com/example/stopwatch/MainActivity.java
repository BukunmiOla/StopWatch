package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button startStopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    private void start() {
        startStopBtn.setText(R.string.stop);
        startStopBtn.setTextColor(Color.parseColor("#EF0808"));

    }

    private void stop() {
        startStopBtn.setText(R.string.start);
        startStopBtn.setTextColor(Color.parseColor("#05E10E"));
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
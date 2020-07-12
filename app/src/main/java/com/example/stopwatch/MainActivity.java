package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
}
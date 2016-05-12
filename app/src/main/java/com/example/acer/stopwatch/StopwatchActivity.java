package com.example.acer.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends Activity {

    private int miliseconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            miliseconds = savedInstanceState.getInt("miliseconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("miliseconds", miliseconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    //Start the stop watch running when start button is clicked
    public void onClickStart(View view) {
        running = true;
    }

    //Stop the stopwatch running when the stop button is clicked
    public void onClickStop(View view) {
        running = false;
    }

    //Reset the stopwatch when the reset button is clicked
    public void onClickReset(View view) {
        running = false;
        miliseconds = 0;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (miliseconds  / 600) % 60;
                int secs = ((miliseconds % 600) / 10) % 60;
                int milisecs = miliseconds % 10;
                String time = String.format("%02d:%02d:%d", minutes, secs, milisecs);
                timeView.setText(time);
                if (running) {
                    miliseconds += 1;
                }
                handler.postDelayed(this, 100);
            }
        });
    }
}

package com.example.visualplanner.ui;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Timer extends CountDownTimer {

    private final String TAG = "Timer";
    TextView view;

    public Timer(View view, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.view = (TextView) view;
        Log.d(TAG, "New instance created: " + this.toString());
    }

    @Override
    public void onTick(long l) {
        TimeUnit tUM = TimeUnit.MILLISECONDS;
        String hms;
        if (tUM.toDays(l) > 1) {
            hms = (tUM.toDays(l)) + " days";
        } else if (tUM.toHours(l) > 1) {
            hms = tUM.toHours(l) + ":" +
                    (tUM.toMinutes(l) - tUM.toMinutes(tUM.toHours(l))) + ":" +
                    (tUM.toSeconds(l) - tUM.toSeconds(tUM.toMinutes(l)));
        } else if (tUM.toMinutes(l) > 1) {
            hms = tUM.toMinutes(l) + ":" +
                    (tUM.toSeconds(l) - tUM.toSeconds(tUM.toMinutes(l)));
        } else hms = String.valueOf(tUM.toSeconds(l));

        view.setText(hms);
    }

    @Override
    public void onFinish() {
        view.setText("hendelse ferdig.");
    }
}
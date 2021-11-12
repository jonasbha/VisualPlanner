package com.example.visualplanner.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.visualplanner.R;
import com.example.visualplanner.adapter.EventRecycleAdapter;
import com.example.visualplanner.model.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AlarmManager {

    private final Context context;
    private final EventRecycleAdapter.EventViewHolder viewHolder;
    private final Event event;

    private final SwitchCompat dateSwitch, timeSwitch;
    private final TextView timerView;

    private final Calendar alarmDate, now;
    private int year, month, day, hour, minute;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Timer timer;

    public AlarmManager(EventRecycleAdapter.EventViewHolder viewHolder, Event event) {
        this.viewHolder = viewHolder;
        this.event = event;

        View view = viewHolder.itemView;
        this.dateSwitch = view.findViewById(R.id.setDateSwitch);
        this.timeSwitch = view.findViewById(R.id.setTimeSwitch);
        this.timerView = view.findViewById(R.id.test);
        this.context = view.getContext();

        alarmDate = Calendar.getInstance(new Locale("nb"));
        now = Calendar.getInstance(new Locale("nb"));

        // personlig preferanse om init date er fra i dag eller sist valgte dato
        // (kunne veart med i en evt. konfig)
        if (event.getAlarm() != null)
            alarmDate.setTime(event.getAlarm());

        year = alarmDate.get(Calendar.YEAR);
        month = alarmDate.get(Calendar.MONTH);
        day = alarmDate.get(Calendar.DAY_OF_MONTH);
        hour = alarmDate.get(Calendar.HOUR_OF_DAY);
        minute = alarmDate.get(Calendar.MINUTE);


        if (timer == null) {
            long diff = event.getAlarm().getTime() - now.getTime().getTime();
            timer = new Timer(diff, 1000);
        }
        startTimer();

        initDatePickerDialog();
        initTimePickerDialog();
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            Log.d("AlarmManager", "new Instance canceled: " + this.toString());
        }
    }

    public void startTimer() {
        if (timer != null) {
            timer.start();
            Log.d("AlarmManager", "new Instance started: " + this.toString());
        }
    }

    private void initDatePickerDialog() {
        DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, year, month, day) -> {
            this.year = year;
            this.month = month;
            this.day = day;

            alarmDate.set(year, month, day, hour, minute);
            event.setAlarm(alarmDate.getTime());
            event.setDateSet(true);
            stopTimer();
            dateSwitch.setChecked(true);
            viewHolder.notifyChange();
        };
        datePickerDialog = new DatePickerDialog(context, onDateSetListener, year, month, day);
    }

    private void initTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, hour, minute) -> {
            this.hour = hour;
            this.minute = minute;

            alarmDate.set(year, month, day, hour, minute);
            event.setAlarm(alarmDate.getTime());
            event.setTimeSet(true);

            timeSwitch.setChecked(true);
            stopTimer();
            viewHolder.notifyChange();
        };
        timePickerDialog = new TimePickerDialog(context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                onTimeSetListener, hour, minute, true
        );
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void onDateCheckChanged(boolean checked) {
        if (checked) {
            if (!event.isDateSet()) {
                dateSwitch.setChecked(false);
                showDatePicker();
            } else event.setDateOn(true);

            if (!datePickerDialog.isShowing())
                viewHolder.notifyChange();
        } else {
            event.setDateOn(false);
            viewHolder.notifyChange();
        }
    }

    public void onTimeCheckChanged(boolean checked) {
        if (checked) {
            if (!event.isTimeSet()) {
                timeSwitch.setChecked(false);
                showTimePicker();
            } else event.setTimeOn(true);

            if (!timePickerDialog.isShowing())
                viewHolder.notifyChange();
        } else {
            event.setTimeOn(false);
            viewHolder.notifyChange();
        }
    }

    public void showDatePicker() {
        datePickerDialog.show();
    }

    public void showTimePicker() {
        timePickerDialog.show();
    }

    private class Timer extends CountDownTimer {

        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
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

            timerView.setText(hms);
            viewHolder.notifyChange();
        }

        @Override
        public void onFinish() {
            timerView.setText("hendelse ferdig.");
        }
    }
}

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

    private Context context;
    private EventRecycleAdapter.EventViewHolder viewHolder;
    private Event event;

    private SwitchCompat dateSwitch, timeSwitch;

    private Calendar alarmDate;
    private int year, month, day, hour, minute;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    public void init(EventRecycleAdapter.EventViewHolder viewHolder, Event event) {
        this.viewHolder = viewHolder;
        this.event = event;

        View view = viewHolder.itemView;
        this.dateSwitch = view.findViewById(R.id.setDateSwitch);
        this.timeSwitch = view.findViewById(R.id.setTimeSwitch);
        this.context = view.getContext();

        alarmDate = Calendar.getInstance(new Locale("nb"));

        // personlig preferanse om init date er fra i dag eller sist valgte dato
        // (kunne veart med i en evt. konfig)
        if (event.getAlarm() != null)
            alarmDate.setTime(event.getAlarm());

        year = alarmDate.get(Calendar.YEAR);
        month = alarmDate.get(Calendar.MONTH);
        day = alarmDate.get(Calendar.DAY_OF_MONTH);
        hour = alarmDate.get(Calendar.HOUR_OF_DAY);
        minute = alarmDate.get(Calendar.MINUTE);

        initDatePickerDialog();
        initTimePickerDialog();
    }


    private void initDatePickerDialog() {
        DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, year, month, day) -> {
            this.year = year;
            this.month = month;
            this.day = day;

            alarmDate.set(year, month, day, hour, minute);
            event.setAlarm(alarmDate.getTime());
            event.setDateHolder(alarmDate.getTime());
            event.setDateSet(true);
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
            event.setDateHolder(alarmDate.getTime());
            event.setTimeSet(true);

            timeSwitch.setChecked(true);
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
            event.setAlarm(event.getDateHolder());

            if (!event.isDateSet()) {
                dateSwitch.setChecked(false);
                showDatePicker();
            } else event.setDateOn(true);

            if (!datePickerDialog.isShowing())
                viewHolder.notifyChange();
        } else {
            event.setDateOn(false);
            if (!event.isTimeOn()) event.setAlarm(null);
            viewHolder.notifyChange();
        }
    }

    public void onTimeCheckChanged(boolean checked) {
        if (checked) {
            event.setAlarm(event.getDateHolder());

            if (!event.isTimeSet()) {
                timeSwitch.setChecked(false);
                showTimePicker();
            } else event.setTimeOn(true);

            if (!timePickerDialog.isShowing())
                viewHolder.notifyChange();
        } else {
            event.setTimeOn(false);
            if (!event.isDateOn()) event.setAlarm(null);
            viewHolder.notifyChange();
        }
    }

    public void showDatePicker() {
        datePickerDialog.show();
    }

    public void showTimePicker() {
        timePickerDialog.show();
    }


    public void setViewHolder(EventRecycleAdapter.EventViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}

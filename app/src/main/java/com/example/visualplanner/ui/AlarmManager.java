package com.example.visualplanner.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.SwitchCompat;

import com.example.visualplanner.R;
import com.example.visualplanner.adapter.EventRecycleAdapter;
import com.example.visualplanner.model.Event;

import java.util.Calendar;
import java.util.Locale;

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

        // Lokal tidssone ikke satt, derfor UTC+1. (kunne vært med i en evt. config)
        alarmDate = Calendar.getInstance();

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

            Calendar today = Calendar.getInstance();
            today.set(year, month, day, 0, 0, 0);
            event.setDateHolder(today.getTime());

            event.setAlarm(alarmDate.getTime());
            event.setAlarmHolder(alarmDate.getTime());

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

            Calendar today = Calendar.getInstance();
            int year = today.get(Calendar.YEAR);
            int month = today.get(Calendar.MONTH);
            int day = today.get(Calendar.DAY_OF_MONTH);
            today.set(year, month, day, hour, minute);
            event.setTimeHolder(today.getTime());

            event.setAlarm(alarmDate.getTime());
            event.setAlarmHolder(alarmDate.getTime());

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
            if (!event.isTimeOn()) event.setAlarm(event.getDateHolder());
            else event.setAlarm(event.getAlarmHolder());

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
            if (!event.isTimeSet()) {
                timeSwitch.setChecked(false);
                showTimePicker();
            } else event.setTimeOn(true);

            if (!event.isDateOn()) event.setAlarm(event.getTimeHolder());
            else event.setAlarm(event.getAlarmHolder());

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

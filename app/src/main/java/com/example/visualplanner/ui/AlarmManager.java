package com.example.visualplanner.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.appcompat.widget.SwitchCompat;

import com.example.visualplanner.R;
import com.example.visualplanner.adapter.EventRecycleAdapter;
import com.example.visualplanner.model.Alarm;
import com.example.visualplanner.model.Event;

import java.util.Calendar;

public class AlarmManager {

    private Context context;
    private EventRecycleAdapter.EventViewHolder viewHolder;
    private Alarm alarm;
    private Event event;

    private SwitchCompat dateSwitch, timeSwitch;

    private Calendar alarmDate;
    private int year, month, day, hour, minute;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    public void init(EventRecycleAdapter.EventViewHolder viewHolder, Event event) {
        this.viewHolder = viewHolder;
        this.alarm = event.getAlarm();

        View view = viewHolder.itemView;
        this.dateSwitch = view.findViewById(R.id.setDateSwitch);
        this.timeSwitch = view.findViewById(R.id.setTimeSwitch);
        this.context = view.getContext();

        // Lokal tidssone ikke satt, derfor UTC+1. (kunne vært med i en evt. config)
        alarmDate = Calendar.getInstance();

        // personlig preferanse om init date er fra i dag eller sist valgte dato
        // (kunne veart med i en evt. konfig)
        if (alarm.getDateTime() != null)
            alarmDate.setTime(alarm.getDateTime());

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
            alarm.setDateHolder(today.getTime());

            alarm.setDateTime(alarmDate.getTime());
            alarm.setAlarmHolder(alarmDate.getTime());

            alarm.setDateSet(true);
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

            if (today.getTime().before(Calendar.getInstance().getTime()))
                today.set(year, month, day + 1, hour, minute);

            alarm.setTimeHolder(today.getTime());

            alarm.setDateTime(alarmDate.getTime());
            alarm.setAlarmHolder(alarmDate.getTime());

            alarm.setTimeSet(true);
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
            if (alarm.isDateSet())
                alarm.setDateOn(true);
            else {
                dateSwitch.setChecked(false);
                showDatePicker();
            }
            if (alarm.isTimeOn()) alarm.setDateTime(alarm.getAlarmHolder());
            else alarm.setDateTime(alarm.getDateHolder());

            if (!datePickerDialog.isShowing())
                viewHolder.notifyChange();
        } else {
            alarm.setDateOn(false);
            if (alarm.isTimeOn()) alarm.setDateTime(alarm.getTimeHolder());
            else alarm.setDateTime(null);

            viewHolder.notifyChange();
        }
    }

    public void onTimeCheckChanged(boolean checked) {
        if (checked) {
            if (alarm.isTimeSet())
                alarm.setTimeOn(true);
             else {
                timeSwitch.setChecked(false);
                showTimePicker();
            }
            if (alarm.isDateOn()) alarm.setDateTime(alarm.getAlarmHolder());
            else alarm.setDateTime(alarm.getTimeHolder());

            if (!timePickerDialog.isShowing())
                viewHolder.notifyChange();
        } else {
            alarm.setTimeOn(false);
            if (alarm.isDateOn()) alarm.setDateTime(alarm.getDateHolder());
            else alarm.setDateTime(null);

            viewHolder.notifyChange();
        }
    }

    public void showDatePicker() {
        datePickerDialog.show();
    }

    public void showTimePicker() {
        timePickerDialog.show();
    }
}

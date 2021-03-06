package com.example.visualplanner.ui.event.alarm;

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

public class AlarmUI {

    private static final String TAG = "AlarmUI";

    private Context context;
    private EventRecycleAdapter.EventViewHolder viewHolder;
    private Alarm alarm;
    private Event event;
    private AlarmScheduler alarmScheduler;

    private SwitchCompat dateSwitch, timeSwitch;

    private Calendar alarmDate;
    private int year, month, day, hour, minute;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    public void init(EventRecycleAdapter.EventViewHolder viewHolder, Event event) {
        this.viewHolder = viewHolder;
        this.alarm = event.getAlarm();
        this.event = event;

        View view = viewHolder.itemView;
        this.context = view.getContext();
        this.dateSwitch = view.findViewById(R.id.setDateSwitch);
        this.timeSwitch = view.findViewById(R.id.setTimeSwitch);

        if (this.alarmScheduler == null)
            this.alarmScheduler = new AlarmScheduler(context);

        // Lokal tidssone ikke satt, derfor UTC+1. (kunne vært med i en evt. config)
        alarmDate = Calendar.getInstance();

        // personlig preferanse om init date er fra i dag eller sist valgte dato
        // (kunne veart med i en evt. konfig)
        if (alarm.getDateTime() != null)
            alarmDate.setTime(alarm.getDateTime());
        alarmScheduler.setAlarmTitle(event.getTitle());

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
            alarm.setDateTimeHolder(alarmDate.getTime());
            alarm.setDateTime(alarmDate.getTime());
            setDateHolder(year, month, day);

            alarm.setDateSet(true);
            dateSwitch.setChecked(true);
            alarm.update();
            alarmScheduler.start(alarm);
            viewHolder.notifyChange();
        };
        datePickerDialog = new DatePickerDialog(context, onDateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
    }

    private void initTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, hour, minute) -> {
            this.hour = hour;
            this.minute = minute;

            alarmDate.set(year, month, day, hour, minute);
            alarm.setDateTime(alarmDate.getTime());
            alarm.setDateTimeHolder(alarmDate.getTime());
            setTimeHolder(hour, minute);

            alarm.setTimeSet(true);
            timeSwitch.setChecked(true);
            alarm.update();
            alarmScheduler.start(alarm);
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
                if (!alarm.isDateOn()) {
                    if (alarm.isDateSet())
                        alarm.setDateOn(true);
                    else {
                        dateSwitch.setChecked(false);
                        showDatePicker();
                    }
                    alarm.update();
                    alarmScheduler.start(alarm);
                }
            } else {
                if (alarm.isDateOn()) {
                    alarm.setDateOn(false);
                    alarm.update();
                    alarmScheduler.cancel(alarm);
                }
            }
            viewHolder.notifyChange();
    }

    public void onTimeCheckChanged(boolean checked) {
        if (checked) {
            if (!alarm.isTimeOn()) {
                if (alarm.isTimeSet()) {
                    alarm.setTimeOn(true);
                } else {
                    timeSwitch.setChecked(false);
                    showTimePicker();
                }
                alarm.update();
                alarmScheduler.start(alarm);
            }
        } else {
            if (alarm.isTimeOn()) {
                alarm.setTimeOn(false);
                alarm.update();
                alarmScheduler.cancel(alarm);
            }
        }
        viewHolder.notifyChange();
    }

    public void showDatePicker() {
        datePickerDialog.show();
    }

    public void showTimePicker() {
        timePickerDialog.show();
    }

    private void setDateHolder(int year, int month, int day) {
        Calendar today = Calendar.getInstance();
        today.set(year, month, day, 0, 0, 0);
        alarm.setDateHolder(today.getTime());
    }

    private void setTimeHolder(int hour, int minute) {
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DAY_OF_MONTH);
        today.set(year, month, day, hour, minute);
        alarm.setTimeHolder(today.getTime());
    }
}
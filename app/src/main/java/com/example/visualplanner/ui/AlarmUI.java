package com.example.visualplanner.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.visualplanner.R;
import com.example.visualplanner.adapter.EventRecycleAdapter;
import com.example.visualplanner.model.Event;

import java.util.Calendar;
import java.util.Locale;

public class AlarmUI {

    private final Context context;
    private final EventRecycleAdapter.EventViewHolder viewHolder;
    private final Event event;

    private final SwitchCompat dateSwitch, timeSwitch;
    private final Calendar calendar;
    private int year, month, day, hour, minute;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    public AlarmUI(EventRecycleAdapter.EventViewHolder viewHolder, Event event) {
        this.viewHolder = viewHolder;
        this.event = event;

        View view = viewHolder.itemView;
        this.dateSwitch = view.findViewById(R.id.setDateSwitch);
        this.timeSwitch = view.findViewById(R.id.setTimeSwitch);
        this.context = view.getContext();

        calendar = Calendar.getInstance(new Locale("nb"));

        // personlig preferanse om init date er fra i dag eller sist valgte dato
        // (kunne veart med i en evt. konfig)
        if (event.getAlarm() != null)
            calendar.setTime(event.getAlarm());

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        initDatePickerDialog();
        initTimePickerDialog();
    }

    private void initDatePickerDialog() {
        DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, year, month, day) -> {
            this.year = year;
            this.month = month;
            this.day = day;

            calendar.set(year, month, day, hour, minute);
            event.setAlarm(calendar.getTime());
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

            calendar.set(year, month, day, hour, minute);
            event.setAlarm(calendar.getTime());
            event.setTimeSet(true);

            timeSwitch.setChecked(true);
            viewHolder.notifyChange();
        };
        timePickerDialog = new TimePickerDialog(
                context,
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
}

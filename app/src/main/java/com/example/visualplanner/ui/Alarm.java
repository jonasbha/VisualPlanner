package com.example.visualplanner.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.widget.SwitchCompat;

import com.example.visualplanner.R;
import com.example.visualplanner.adapter.EventRecycleAdapter;
import com.example.visualplanner.model.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Alarm {

    private final Context context;
    private final EventRecycleAdapter.EventViewHolder viewHolder;
    private final Event event;

    private final TextView dateView, timeView;
    private final SwitchCompat dateSwitch, timeSwitch;
    private final Calendar calendar;
    private int year, month, day, hour, minute;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    public Alarm(EventRecycleAdapter.EventViewHolder viewHolder, Event event) {
        this.viewHolder = viewHolder;
        this.event = event;

        View view = viewHolder.itemView;
        this.dateView = view.findViewById(R.id.dateText);
        this.timeView = view.findViewById(R.id.timeText);
        this.dateSwitch = view.findViewById(R.id.setDateSwitch);
        this.timeSwitch = view.findViewById(R.id.setTimeSwitch);
        this.context = view.getContext();

        calendar = Calendar.getInstance(new Locale("nb"));

        // personlig preferanse om init date er fra i dag eller sist valgte dato
        // (kunne veart med i en evt. konfig)
        if (event.getAlarmDate() != null)
            calendar.setTime(event.getAlarmDate());

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
            event.setAlarmDate(calendar.getTime());
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
            event.setAlarmDate(calendar.getTime());
            viewHolder.notifyChange();
        };
        timePickerDialog = new TimePickerDialog(
                context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                onTimeSetListener, hour, minute, true
        );
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void onDateCheckedChanged(boolean checked) {
        if (checked) {
            dateView.setVisibility(View.VISIBLE);
            if (event.getAlarmDate() == null) {
                dateSwitch.setChecked(false);
                showDatePicker();
            }
            if (event.getAlarmDate() != null)
                event.setAlarmSet(true);

            if (datePickerDialog != null) {
                if (!datePickerDialog.isShowing())
                    viewHolder.notifyChange();
            }
        } else {
            event.setAlarmSet(false);
            viewHolder.notifyChange();
        }
    }

    public void onTimeCheckedChanged(boolean checked) {

    }

    public void showDatePicker() {
        datePickerDialog.show();
    }

    public void showTimePicker() {
        timePickerDialog.show();
    }
}

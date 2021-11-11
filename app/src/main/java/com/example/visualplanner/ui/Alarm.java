package com.example.visualplanner.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.visualplanner.R;
import com.example.visualplanner.adapter.EventRecycleAdapter;
import com.example.visualplanner.model.Event;

import java.util.Calendar;

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

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        initDatePickerDialog();
    }

    private void initDatePickerDialog() {
        datePickerDialog = new DatePickerDialog(
                context,
                (DatePickerDialog.OnDateSetListener) (datePicker, year, month, day) -> {
                    calendar.set(year, month, day);
                    event.setAlarmDate(calendar.getTime());
                    dateSwitch.setChecked(true);
                    viewHolder.notifyChange();
                }, year, month, day
        );
    }

    private void initTimePickerDialog() {

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

            if (!datePickerDialog.isShowing())
                viewHolder.notifyChange();
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

    public void showTimePicker() {}
}

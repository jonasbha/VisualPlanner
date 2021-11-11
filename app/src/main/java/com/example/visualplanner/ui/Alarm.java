package com.example.visualplanner.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.visualplanner.adapter.EventRecycleAdapter;
import com.example.visualplanner.model.Event;

import java.util.Calendar;

public class Alarm {

    private final Event event;
    private final TextView date;
    private final SwitchCompat switchCompat;
    private final Calendar calendar;
    private final int year;
    private final int month;
    private final int day;
    private DatePickerDialog datePickerDialog;
    private final Context context;
    private final EventRecycleAdapter.EventViewHolder viewHolder;

    public Alarm(EventRecycleAdapter.EventViewHolder viewHolder, Context context) {
        this.date = viewHolder.getDateView();
        this.switchCompat = viewHolder.getSwitchC();
        this.context = context;
        this.viewHolder = viewHolder;
        this.event = viewHolder.getEvent();

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
                    switchCompat.setChecked(true);
                    viewHolder.notifyChange();
                }, year, month, day
        );
    }

    public void showDatePicker() {
        datePickerDialog.show();
    }

    public void onDateCheckedChanged(boolean checked) {
        if (checked) {
            date.setVisibility(View.VISIBLE);
            if (event.getAlarmDate() == null) {
                switchCompat.setChecked(false);
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
}

package com.example.visualplanner.ui.event.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.visualplanner.model.Alarm;
import com.example.visualplanner.receiver.AlarmReceiver;

public class AlarmScheduler {

    private static final String TAG = "AlarmScheduler";
    private final AlarmManager alarmManager;
    private Context context;
    private Alarm alarm;
    private String alarmTitle;

    public AlarmScheduler(Context context) {
        this.context = context;
        this.alarmTitle = "Alarm";
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void cancel(Alarm alarm) {
        this.alarm = alarm;
        PendingIntent pendingIntent = getPendingIntent();

        if (!alarm.isDateOn() && !alarm.isTimeOn()) {
            if (pendingIntent != null && alarmManager != null) {
                alarmManager.cancel(pendingIntent);
                Log.i(TAG, "alarm " + alarmTitle + " cancelled.");
            }
        } else if (alarm.isFinished()) {
            if (pendingIntent != null && alarmManager != null) {
                alarmManager.cancel(pendingIntent);
                Log.i(TAG, "alarm " + alarmTitle + " cancelled.");
            }
        } else if (alarm.isDateOn() || alarm.isTimeOn()) {
            start(alarm);
        }
    }

    public void cancelByForce(Alarm alarm) {
        this.alarm = alarm;
        PendingIntent pendingIntent = getPendingIntent();

        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Log.i(TAG, "alarm " + alarmTitle + " cancelled.");
        }
    }

    protected void start(Alarm alarm) {
        this.alarm = alarm;
        if (!alarm.isFinished() && alarm.getDateTime() != null) {
            Log.i(TAG, "alarm " + alarmTitle + " started.");
            if (alarm.isDateOn() && alarm.isTimeOn()) {
                startExactAlarm();
            } else if (alarm.isTimeOn()) {
                startExactAlarm();
            } else if (alarm.isDateOn()) {
                startInexactAlarm();
            }
        }
    }

    private void startExactAlarm() {
        PendingIntent pendingIntent = getPendingIntent();

        if (alarmManager != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms())
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getDateTime().getTime(), pendingIntent);
                else alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getDateTime().getTime(), pendingIntent);
            }
            else alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getDateTime().getTime(), pendingIntent);
    }

    private void startInexactAlarm() {
        PendingIntent pendingIntent = getPendingIntent();

        if (alarmManager != null)
            alarmManager.set(AlarmManager.RTC, alarm.getDateTime().getTime(), pendingIntent);
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this.context, AlarmReceiver.class);
        intent.putExtra("title", alarmTitle);
        intent.putExtra("rq", alarm.getRequestCode());
        return PendingIntent.getBroadcast(this.context, alarm.getRequestCode(), intent,
                PendingIntent.FLAG_IMMUTABLE);
    }

    public void setAlarmTitle(String alarmTitle) {
        if (!alarmTitle.equals(""))
            this.alarmTitle = alarmTitle;
    }
}

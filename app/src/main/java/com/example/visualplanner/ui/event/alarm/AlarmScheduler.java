package com.example.visualplanner.ui.event.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.visualplanner.model.Alarm;

public class AlarmScheduler {

    private static final String TAG = "AlertHandler";
    private final AlarmManager alarmManager;
    private Context context;
    private Alarm alarm;

    public AlarmScheduler() {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }
    public AlarmScheduler(Context context, Alarm alarm) {
        this.context = context;
        this.alarm = alarm;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this.context, AlarmReceiver.class);

        return PendingIntent.getBroadcast(this.context, alarm.getRequestCode(), intent,
                PendingIntent.FLAG_IMMUTABLE);
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

    protected void cancel() {
        PendingIntent pendingIntent = getPendingIntent();

        if (pendingIntent != null && alarmManager != null)
            alarmManager.cancel(pendingIntent);
    }

    protected void start() {
        if (alarm.isDateOn() && alarm.isTimeOn()) {
            startExactAlarm();
        } else if (alarm.isTimeOn()) {
            startExactAlarm();
        } else if (alarm.isDateOn()) {
            startInexactAlarm();
        }
    }
}
